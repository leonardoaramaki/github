package com.example.bliss.data.source.remote

import com.example.bliss.data.Emoji
import com.example.bliss.data.source.EmojiDataSource
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RemoteEmojiDataSource : EmojiDataSource {
    val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.HEADERS })
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .client(client)
        .addConverterFactory(
            MoshiConverterFactory.create(Moshi.Builder().add(EmojiResponseConverter()).build())
        )
        .build()

    val github = retrofit.create(GithubService::class.java)

    override suspend fun getEmojiList(): List<Emoji> {
        return github.getEmojis()
    }

    override suspend fun saveAll(emojis: List<Emoji>) {
        // No-op
    }
}