package com.example.bliss.data.source

import com.example.bliss.data.Emoji

interface EmojiDataSource {
    suspend fun getEmojiList(): List<Emoji>

    suspend fun saveAll(emojis: List<Emoji>)

    /**
     * Clear data at its source if possible.
     */
    suspend fun clear() {
        // No-op
    }
}
