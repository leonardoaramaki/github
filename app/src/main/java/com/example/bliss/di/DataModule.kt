package com.example.bliss.di

import android.content.Context
import androidx.room.Room
import com.example.bliss.data.DefaultEmojiRepository
import com.example.bliss.data.EmojiRepository
import com.example.bliss.data.source.EmojiDataSource
import com.example.bliss.data.source.Preferences
import com.example.bliss.data.source.local.AppDatabase
import com.example.bliss.data.source.local.LocalEmojiDataSource
import com.example.bliss.data.source.local.PreferencesImpl
import com.example.bliss.data.source.remote.EmojiResponseConverter
import com.example.bliss.data.source.remote.GithubService
import com.example.bliss.data.source.remote.RemoteEmojiDataSource
import com.squareup.moshi.Moshi
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

    @EmojiLocalDataSource
    @Provides
    fun provideEmojiLocalDataSource(db: AppDatabase): EmojiDataSource =
        LocalEmojiDataSource(db)

    @EmojiRemoteDataSource
    @Provides
    fun provideEmojiRemoteDataSource(githubService: GithubService): EmojiDataSource =
        RemoteEmojiDataSource(githubService)

    @Provides
    fun provideEmojiRepository(
        preferences: Preferences,
        @EmojiRemoteDataSource remoteDataSource: EmojiDataSource,
        @EmojiLocalDataSource localDataSource: EmojiDataSource,
    ): EmojiRepository =
        DefaultEmojiRepository(preferences, remoteDataSource, localDataSource)
}