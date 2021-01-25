package com.example.bliss.data.source.local

import androidx.paging.DataSource
import androidx.paging.PagedList
import com.example.bliss.data.Emoji
import com.example.bliss.data.User
import com.example.bliss.data.source.GithubDataSource
import com.example.bliss.data.source.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalGithubDataSource @Inject constructor(private val db: AppDatabase) : GithubDataSource {
    override suspend fun getEmojiList(): List<Emoji> = withContext(Dispatchers.IO) {
        return@withContext db.emojiDao().getAll()
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

    override suspend fun getUserRepos(username: String, page: Int, perPage: Int): DataSource<Int, Repository> {
        TODO()
    }

    override suspend fun saveRepos(repositories: List<Repository>, username: String) {
        TODO()
    }
}