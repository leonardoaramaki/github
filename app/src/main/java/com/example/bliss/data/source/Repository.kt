package com.example.bliss.data.source

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

/**
 * Representation of a Github repository.
 *
 * @property fullName the name of the repository
 * @property page the page on which this was included. This is not a Github
 * intrinsic property, but the one used as a key for pagination requests
 * @property username the username who owns this [Repository]
 */
@Entity(tableName = "repositories")
data class Repository(
    @PrimaryKey @ColumnInfo(name = "id") @Json(name = "id") val id: Long,
    @ColumnInfo(name = "full_name") @Json(name = "full_name") val fullName: String,
    @Transient @ColumnInfo(name = "page") val page: Int = 0,
    @Transient @ColumnInfo(name = "username") var username: String? = null
)
