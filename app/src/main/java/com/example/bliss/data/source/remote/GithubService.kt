package com.example.bliss.data.source.remote

import com.example.bliss.data.Emoji
import com.example.bliss.data.User
import com.example.bliss.data.source.Repository
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {
    @GET("/emojis")
    suspend fun getEmojis(): List<Emoji>?

    @GET("/users/{username}")
    suspend fun getUser(@Path("username") username: String): User?

    @GET("/users/{username}/repos")
    suspend fun getUserRepos(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<Repository>
}
