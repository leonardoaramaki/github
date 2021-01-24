package com.example.bliss.di

import android.content.Context
import androidx.room.Room
import com.example.bliss.data.source.Preferences
import com.example.bliss.data.source.local.AppDatabase
import com.example.bliss.data.source.local.PreferencesImpl
import com.example.bliss.data.source.remote.EmojiResponseConverter
import com.example.bliss.data.source.remote.GithubService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(ApplicationComponent::class)
object DataModule {
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "bliss.db")
            .build()

    @Provides
    fun provideOkHttpClient() =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.HEADERS })
            .build()

    @Provides
    fun provideRetrofit(client: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .client(client)
            .addConverterFactory(
                MoshiConverterFactory.create(Moshi.Builder().add(EmojiResponseConverter()).build())
            )
            .build()

    @Provides
    fun provideGithubService(retrofit: Retrofit) =
        retrofit.create(GithubService::class.java)

    @Provides
    fun providePreferences(@ApplicationContext context: Context): Preferences =
        PreferencesImpl(context)
}