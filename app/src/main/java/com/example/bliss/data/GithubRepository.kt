package com.example.bliss.data

import com.example.bliss.data.source.GithubDataSource
import com.example.bliss.data.source.Preferences
import com.example.bliss.data.source.Repository
import kotlinx.coroutines.flow.firstOrNull
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface GithubRepository {
    /**
     * Return a list of [Emoji]s.
     */
    suspend fun getEmojiList(): List<Emoji>

    /**
     * Return a [User] data for a given [username].
     *
     * @param username the github username handle to search for
     *
     * @return a [User] object when found or null otherwise
     */
    suspend fun getUser(username: String): User?

    /**
     * Return a list containing all [User]s that were fetched so far.
     *
     * @return a list of [User]s
     */
    suspend fun getUsers(): List<User>

    /**
     * Remove a [user] from data source, if possible.
     */
    suspend fun removeUser(user: User)

    /**
     * Return a list of [Repository] objects from Google.
     */
    suspend fun getGoogleRepos(): List<Repository>
}

/**
 * Default implementation for [GithubRepository] that uses Github as a data source.
 *
 * The default [timeToLive] is defined as 1 day.
 *
 * @property remoteDataSource the remote data source where to fetch fresh data
 * @property localDataSource the local data source from where to fetch cached data
 * @property timeToLive the total time cached data is considered valid (not staled) in milliseconds
 */
class DefaultGithubRepository @Inject constructor(
    private val preferences: Preferences,
    private val remoteDataSource: GithubDataSource,
    private val localDataSource: GithubDataSource,
    private val timeToLive: Long = TimeUnit.DAYS.toMillis(1) // 24h or 86400000ms
) : GithubRepository {

    override suspend fun getEmojiList(): List<Emoji> {
        val lastUpdated: Long = preferences.getLastUpdateForEmoji().firstOrNull() ?: 0

        val currentTime = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000
        if (currentTime - lastUpdated >= timeToLive) {
            return getEmojisFromRemote(currentTime)
        }

        val emojisLocal = localDataSource.getEmojiList()
        if (emojisLocal.isNotEmpty()) {
            return emojisLocal
        }

        return getEmojisFromRemote(currentTime)
    }

    // Fetch emojis from remote, persist on SQLite and save update time.
    private suspend fun getEmojisFromRemote(currentTime: Long): List<Emoji> =
        remoteDataSource.getEmojiList().also {
            preferences.setLastUpdateForEmoji(currentTime)
            localDataSource.saveAll(it)
        }

    override suspend fun getUser(username: String): User? {
        // Always keep Users cached since this route is more prone for abusing
        return localDataSource.getUser(username) ?: return remoteDataSource.getUser(username).also {
            it ?: return@also
            localDataSource.saveUser(it)
        }
    }

    override suspend fun getUsers(): List<User> {
        return localDataSource.getUsers()
    }

    override suspend fun removeUser(user: User) {
        localDataSource.removeUser(user)
    }

    override suspend fun getGoogleRepos(): List<Repository> {
        TODO()
    }
}
