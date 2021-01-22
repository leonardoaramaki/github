package com.example.bliss.data

import com.example.bliss.data.source.EmojiDataSource

interface EmojiRepository {
    /**
     * Return a list of [Emoji]s.
     */
    suspend fun getEmojiList(): List<Emoji>
}

/**
 * Default implementation for [EmojiRepository] that uses Github as a data source.
 */
class DefaultEmojiRepository(
    private val remoteDataSource: EmojiDataSource,
    private val localDataSource: EmojiDataSource
) : EmojiRepository {

    override suspend fun getEmojiList(): List<Emoji> {
        TODO("Not yet implemented")
    }
}
