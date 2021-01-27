package com.example.bliss.data.source.local

import androidx.paging.PagingSource
import com.example.bliss.data.Emoji
import com.example.bliss.data.User
import com.example.bliss.data.source.GithubDataSource
import com.example.bliss.data.source.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.RuntimeException
import javax.inject.Inject

class LocalGithubDataSource @Inject constructor(private val db: AppDatabase) : GithubDataSource {
    override suspend fun getEmojiList(): Result<List<Emoji>> = withContext(Dispatchers.IO) {
        return@withContext kotlin.runCatching { db.emojiDao().getAll() }
    }

    override suspend fun saveAll(emojis: List<Emoji>) = withContext(Dispatchers.IO) {
        db.emojiDao().insertAll(*emojis.toTypedArray())
    }

    override suspend fun getUser(username: String): User? = withContext(Dispatchers.IO) {
        return@withContext db.userDao().findByUsername(username)
    }

    override suspend fun saveUser(user: User) = withContext(Dispatchers.IO) {
        db.userDao().insertAll(user)
    }

    override suspend fun getUsers(): List<User> = withContext(Dispatchers.IO) {
        return@withContext db.userDao().getAll()
    }

    override suspend fun removeUser(user: User) = withContext(Dispatchers.IO) {
        db.userDao().deleteAll(user)
    }

    override suspend fun getUserRepos(username: String, page: Int, perPage: Int): List<Repository> {
        // No-op
        throw RuntimeException("Tried to query local database to get repositories as a List.\n" +
            "Use getRepositoryPagingSource instead to get the paged version.")
    }

    override suspend fun saveRepos(repositories: List<Repository>) {
        db.repositoryDao().insertAll(repositories)
    }

    override suspend fun clearAllRepos() {
        db.repositoryDao().clearAll()
    }

    override fun getReposByUsername(username: String): PagingSource<Int, Repository> {
        return db.repositoryDao().pagingSource(username)
    }
}