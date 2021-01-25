package com.example.bliss.data.source.remote

import com.example.bliss.data.Emoji
import com.example.bliss.data.User
import com.example.bliss.data.source.GithubDataSource
import javax.inject.Inject

class RemoteGithubDataSource @Inject constructor(
    private val github: GithubService
) : GithubDataSource {

    override suspend fun getEmojiList(): List<Emoji> {
        return github.getEmojis()
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
}