package com.example.bliss.data.source

import com.example.bliss.data.Emoji

interface EmojiDataSource {
    suspend fun getEmojiList(): List<Emoji>
}
