package com.example.bliss.data.source

import androidx.paging.DataSource
import androidx.paging.PagingSource
import com.example.bliss.data.Emoji
import com.example.bliss.data.User

interface GithubDataSource {
    suspend fun getEmojiList(): List<Emoji>

    suspend fun saveAll(emojis: List<Emoji>)

    suspend fun getUser(username: String): User?

    suspend fun saveUser(user: User)

    suspend fun getUsers(): List<User>

    suspend fun removeUser(user: User)

    suspend fun getUserRepos(username: String, page: Int, perPage: Int): List<Repository>

    suspend fun saveRepos(repositories: List<Repository>)

    suspend fun clearAllRepos()

    fun getReposByUsername(username: String): PagingSource<Int, Repository>
}
