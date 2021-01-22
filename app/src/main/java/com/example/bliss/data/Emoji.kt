package com.example.bliss.data

/**
 * Models an emoji as returned from an api.
 *
 * @property shortCode the string representation of this emoji.
 * @property url the url from where to fetch the raster/vector image of the emoji.
 */
data class Emoji(
    val shortCode: String,
    val url: String
)
