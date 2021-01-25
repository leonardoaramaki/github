package com.example.bliss.data.source

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "repositories")
data class Repository(
    @PrimaryKey @ColumnInfo(name = "id") @Json(name = "id") val id: Long,
    @ColumnInfo(name = "full_name") @Json(name = "full_name") val fullName: String,
    @Transient @ColumnInfo(name = "username") var username: String? = null
)
