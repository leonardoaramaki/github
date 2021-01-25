package com.example.bliss.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "users")
data class User(
    @PrimaryKey @ColumnInfo(name = "id") @field:Json(name = "id") val id: Long,
    @ColumnInfo(name = "username") @Json(name = "login") val login: String,
    @ColumnInfo(name = "avatar_url") @Json(name = "avatar_url") val avatarUrl: String
)
