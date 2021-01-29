package com.example.bliss.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Models an emoji as returned from an api.
 *
 * @property shortCode the string representation of this emoji
 * @property url the url from where to fetch the raster/vector image of the emoji
 */
@Entity(tableName = "emojis")
data class Emoji(
    @PrimaryKey @ColumnInfo(name = "short_code") val shortCode: String,
    @ColumnInfo(name = "url") val url: String
)
