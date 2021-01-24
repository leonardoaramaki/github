package com.example.bliss.data.source.remote

import com.example.bliss.data.Emoji
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson

class EmojiResponseConverter {

    @FromJson
    fun fromJson(jsonReader: JsonReader): List<Emoji> {
        jsonReader.beginObject()
        val emojis = mutableListOf<Emoji>()
        while (jsonReader.hasNext()) {
            val key = jsonReader.nextName()
            val value = jsonReader.nextString()
            emojis.add(Emoji(shortCode = key, url = value))
        }
        jsonReader.endObject()
        return emojis
    }

    @ToJson
    fun toJson(writer: JsonWriter, emojis: List<Emoji>) {
        // No-op: Needed for Moshi adapters.
    }
}