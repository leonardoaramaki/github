package com.example.bliss.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bliss.data.Emoji
import com.example.bliss.data.User
import com.example.bliss.data.source.Repository

@Database(entities = [Emoji::class, User::class], version = 5)
abstract class AppDatabase : RoomDatabase() {
    abstract fun emojiDao(): EmojiDao
    abstract fun userDao(): UserDao
}
