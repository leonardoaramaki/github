package com.example.bliss.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bliss.data.Emoji

@Dao
interface EmojiDao {
    @Query("SELECT * FROM emojis")
    suspend fun getAll(): List<Emoji>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg emoji: Emoji)

    @Query("DELETE FROM emojis")
    suspend fun deleteAll()
}