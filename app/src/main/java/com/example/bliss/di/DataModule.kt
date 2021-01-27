package com.example.bliss.di

import android.content.Context
import androidx.room.Room
import com.example.bliss.data.DefaultGithubRepository
import com.example.bliss.data.GithubRepository
import com.example.bliss.data.User
import com.example.bliss.data.source.GithubDataSource
import com.example.bliss.data.source.Preferences
import com.example.bliss.data.source.local.AppDatabase
import com.example.bliss.data.source.local.LocalGithubDataSource
import com.example.bliss.data.source.local.PreferencesImpl
import com.example.bliss.data.source.remote.EmojiResponseConverter
import com.example.bliss.data.source.remote.GithubService
import com.example.bliss.data.source.remote.RemoteGithubDataSource
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class EmojiRemoteDataSource

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class EmojiLocalDataSource

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "bliss.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideOkHttpClient() =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.HEADERS
            })
            .build()

    @Provides
    fun provideRetrofit(client: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3001")
            .client(client)
            .addConverterFactory(
                MoshiConverterFactory.create(Moshi.Builder()
                    .add(EmojiResponseConverter())
                    .addLast(KotlinJsonAdapterFactory())
                    .build()).asLenient()
            )
            .build()

    @Provides
    fun provideGithubService(retrofit: Retrofit) =
        retrofit.create(GithubService::class.java)

    @Provides
    fun providePreferences(@ApplicationContext context: Context): Preferences =
        PreferencesImpl(context)

    @EmojiLocalDataSource
    @Provides
    fun provideEmojiLocalDataSource(db: AppDatabase): GithubDataSource =
        LocalGithubDataSource(db)

    @EmojiRemoteDataSource
    @Provides
    fun provideEmojiRemoteDataSource(githubService: GithubService): GithubDataSource =
        RemoteGithubDataSource(githubService)

    @Provides
    fun provideEmojiRepository(
        preferences: Preferences,
        @EmojiRemoteDataSource remoteDataSource: GithubDataSource,
        @EmojiLocalDataSource localDataSource: GithubDataSource,
    ): GithubRepository =
        DefaultGithubRepository(preferences, remoteDataSource, localDataSource)
}