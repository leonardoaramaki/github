package com.example.bliss.data.source.local

import com.example.bliss.data.Emoji
import com.example.bliss.data.source.EmojiDataSource

class LocalEmojiDataSource(private val db: AppDatabase) : EmojiDataSource {
    override suspend fun getEmojiList(): List<Emoji> {
        return db.emojiDao().getAll()
    }

    override suspend fun saveAll(emojis: List<Emoji>) {
        db.emojiDao().insertAll(*emojis.toTypedArray())
    }
}