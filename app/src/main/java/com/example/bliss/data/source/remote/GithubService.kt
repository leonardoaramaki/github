package com.example.bliss.data.source.remote

import com.example.bliss.data.Emoji
import retrofit2.http.GET

interface GithubService {
    @GET("/emojis")
    suspend fun getEmojis(): List<Emoji>
}
