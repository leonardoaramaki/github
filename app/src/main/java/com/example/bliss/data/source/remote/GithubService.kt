package com.example.bliss.data.source.remote

import com.example.bliss.data.Emoji
import com.example.bliss.data.User
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    @GET("/emojis")
    suspend fun getEmojis(): List<Emoji>

    @GET("/users/{username}")
    suspend fun getUser(@Path("username") username: String): User?
}
