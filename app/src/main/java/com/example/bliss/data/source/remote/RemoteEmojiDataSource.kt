package com.example.bliss.data.source.remote

import com.example.bliss.data.Emoji
import com.example.bliss.data.source.EmojiDataSource
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class RemoteEmojiDataSource @Inject constructor(
    private val github: GithubService
) : EmojiDataSource {

    override suspend fun getEmojiList(): List<Emoji> {
        return github.getEmojis()
    }

    override suspend fun saveAll(emojis: List<Emoji>) {
        // No-op
    }
}