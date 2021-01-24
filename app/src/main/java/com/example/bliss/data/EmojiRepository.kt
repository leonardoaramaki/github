package com.example.bliss.data

import com.example.bliss.data.source.EmojiDataSource
import com.example.bliss.data.source.Preferences
import kotlinx.coroutines.flow.collect
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import java.util.concurrent.TimeUnit

interface EmojiRepository {
    /**
     * Return a list of [Emoji]s.
     */
    suspend fun getEmojiList(): List<Emoji>
}

/**
 * Default implementation for [EmojiRepository] that uses Github as a data source.
 *
 * The default [timeToLive] is defined as 1 day.
 *
 * @property remoteDataSource the remote data source where to fetch fresh data
 * @property localDataSource the local data source from where to fetch cached data
 * @property timeToLive the total time cached data is considered valid (not staled) in milliseconds
 */
class DefaultEmojiRepository(
    private val preferences: Preferences,
    private val remoteDataSource: EmojiDataSource,
    private val localDataSource: EmojiDataSource,
    private val timeToLive: Long = TimeUnit.DAYS.toMillis(1) // 24h or 86400000ms
) : EmojiRepository {

    override suspend fun getEmojiList(): List<Emoji> {
        var lastUpdated: Long = 0
        preferences.getLastUpdatedEmojisEpoch().collect {
            lastUpdated = it
        }

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
            preferences.putLastUpdatedEmojisEpoch(currentTime)
            localDataSource.saveAll(it)
        }
}
