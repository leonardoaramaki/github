package com.example.bliss.data.source.local

import androidx.room.*
import com.example.bliss.data.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    suspend fun getAll(): List<User>

    @Query("SELECT * FROM users WHERE username=:username")
    suspend fun findByUsername(username: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg user: User)

    @Delete
    fun deleteAll(vararg user: User)
}
