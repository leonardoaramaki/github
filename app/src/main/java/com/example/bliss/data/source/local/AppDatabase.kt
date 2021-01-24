package com.example.bliss.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bliss.data.Emoji

@Database(entities = [Emoji::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun emojiDao(): EmojiDao
}
