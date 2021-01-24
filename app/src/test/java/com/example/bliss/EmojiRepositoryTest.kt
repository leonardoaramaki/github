package com.example.bliss

import com.example.bliss.data.DefaultEmojiRepository
import com.example.bliss.data.Emoji
import com.example.bliss.data.source.EmojiDataSource
import com.example.bliss.data.source.Preferences
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneOffset

class EmojiRepositoryTest {
    val preferences = mockk<Preferences>(relaxed = true)
    val localDataSource = mockk<EmojiDataSource>(relaxed = true)
    val remoteDataSource = mockk<EmojiDataSource>()
    val timeToLive = 2000L // Expire data after 2 seconds
    val sut = DefaultEmojiRepository(preferences, remoteDataSource, localDataSource, timeToLive)

    @Before
    fun setup() {
        coEvery { preferences.getLastUpdatedEmojisEpoch() } returns flowOf(0)

        coEvery { remoteDataSource.getEmojiList() } returns listOf(
            Emoji("+1", "https://github.githubassets.com/images/icons/emoji/unicode/1f44d.png?v8"),
            Emoji("-1", "https://github.githubassets.com/images/icons/emoji/unicode/1f44e.png?v8"),
            Emoji(
                "100",
                "https://github.githubassets.com/images/icons/emoji/unicode/1f44af.png?v8"
            ),
            Emoji(
                "1st_place_medal",
                "https://github.githubassets.com/images/icons/emoji/unicode/1f947.png?v8"
            ),
        )

        coEvery { localDataSource.getEmojiList() } returns emptyList()
    }

    @Test
    fun `EmojiRepository getEmojiList should return a list of Emoji`() {
        var emojiList: List<Emoji> = runBlocking { sut.getEmojiList() }
        assertThat(emojiList).hasSize(4)

        // First request should get data from remote since cache is empty
        coVerify(exactly = 1) { remoteDataSource.getEmojiList() }
        coVerify(exactly = 0) { localDataSource.getEmojiList() }

        coEvery { preferences.getLastUpdatedEmojisEpoch() } returns flowOf(
            LocalDateTime.now().toEpochSecond(
                ZoneOffset.UTC
            ) * 1000
        )

        // Simulate data from remote getting cached
        coEvery { localDataSource.getEmojiList() } returns listOf(
            Emoji("+1", "https://github.githubassets.com/images/icons/emoji/unicode/1f44d.png?v8"),
            Emoji("-1", "https://github.githubassets.com/images/icons/emoji/unicode/1f44e.png?v8"),
            Emoji(
                "100",
                "https://github.githubassets.com/images/icons/emoji/unicode/1f44af.png?v8"
            ),
            Emoji(
                "1st_place_medal",
                "https://github.githubassets.com/images/icons/emoji/unicode/1f947.png?v8"
            ),
        )

        emojiList = runBlocking { sut.getEmojiList() }
        assertThat(emojiList).hasSize(4)

        // Verify subsequent requests gets data from local since cache is not empty
        coVerify(exactly = 1) { localDataSource.getEmojiList() }
        coVerify(exactly = 1) { remoteDataSource.getEmojiList() }
    }

    @Test
    fun `EmojiRepository getEmojiList should fetch from remote after cache expiry time`() {
        var emojiList: List<Emoji> = runBlocking { sut.getEmojiList() }
        assertThat(emojiList).hasSize(4)

        // First request should get data from remote since cache is empty
        coVerify(exactly = 1) { remoteDataSource.getEmojiList() }

        // Simulate data from remote getting cached
        coEvery { localDataSource.getEmojiList() } returns listOf(
            Emoji("+1", "https://github.githubassets.com/images/icons/emoji/unicode/1f44d.png?v8"),
            Emoji("-1", "https://github.githubassets.com/images/icons/emoji/unicode/1f44e.png?v8"),
            Emoji(
                "100",
                "https://github.githubassets.com/images/icons/emoji/unicode/1f44af.png?v8"
            ),
            Emoji(
                "1st_place_medal",
                "https://github.githubassets.com/images/icons/emoji/unicode/1f947.png?v8"
            ),
        )

        coEvery { preferences.getLastUpdatedEmojisEpoch() } returns flowOf(
            LocalDateTime.now().toEpochSecond(
                ZoneOffset.UTC
            ) * 1000
        )

        // Wait data to get stale
        Thread.sleep(timeToLive)

        emojiList = runBlocking { sut.getEmojiList() }
        assertThat(emojiList).hasSize(4)

        // Verify subsequent requests gets data from remote again since data is already stale
        coVerify(exactly = 2) { remoteDataSource.getEmojiList() }
        coVerify(exactly = 0) { localDataSource.getEmojiList() }
    }
}
