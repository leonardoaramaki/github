package com.example.bliss.data.source.local

import com.example.bliss.data.Emoji
import com.example.bliss.data.User
import com.example.bliss.data.source.GithubDataSource
import javax.inject.Inject

class LocalGithubDataSource @Inject constructor(private val db: AppDatabase) : GithubDataSource {
    override suspend fun getEmojiList(): List<Emoji> {
        return db.emojiDao().getAll()
    }

    override suspend fun saveAll(emojis: List<Emoji>) {
        db.emojiDao().insertAll(*emojis.toTypedArray())
    }

    override suspend fun getUser(username: String): User? {
        TODO("Not yet implemented")
    }
}