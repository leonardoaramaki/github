package com.example.bliss.data.source

import com.example.bliss.data.Emoji
import com.example.bliss.data.User

interface GithubDataSource {
    suspend fun getEmojiList(): List<Emoji>

    suspend fun saveAll(emojis: List<Emoji>)

    suspend fun getUser(username: String): User?

    suspend fun saveUser(user: User)
}
