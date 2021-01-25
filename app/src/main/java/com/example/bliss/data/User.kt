package com.example.bliss.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "users")
data class User(
    @PrimaryKey @field:Json(name = "id") val id: Long,
    @field:Json(name = "login") val login: String,
    @field:Json(name = "avatar_url") val avatarUrl: String
)
