package com.example.bliss.data.source.remote

import androidx.paging.PagingSource
import com.example.bliss.data.Emoji
import com.example.bliss.data.User
import com.example.bliss.data.source.GithubDataSource
import com.example.bliss.data.source.Repository
import javax.inject.Inject

class RemoteGithubDataSource @Inject constructor(
    private val github: GithubService
) : GithubDataSource {

    override suspend fun getEmojiList(): Result<List<Emoji>> {
        return kotlin.runCatching { github.getEmojis().orEmpty() }
    }

    override suspend fun saveAll(emojis: List<Emoji>) {
        // No-op
    }

    override suspend fun getUser(username: String): User? {
        return github.getUser(username)
    }

    override suspend fun saveUser(user: User) {
        // No-op
    }

    override suspend fun getUsers(): List<User> {
        // No-op
        throw RuntimeException("This method is not supported for remote data sources")
    }

    override suspend fun removeUser(user: User) {
        // No-op
    }

    override suspend fun getUserRepos(username: String, page: Int, perPage: Int): List<Repository> {
        return github.getUserRepos(username, page, perPage)
    }

    override suspend fun saveRepos(repositories: List<Repository>) {
        // No-op
    }

    override suspend fun clearAllRepos() {
        // No-op
    }

    override fun getReposByUsername(username: String): PagingSource<Int, Repository> {
        throw RuntimeException("This method is not supported for remote data sources")
    }
}