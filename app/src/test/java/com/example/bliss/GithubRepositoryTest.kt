package com.example.bliss

import com.example.bliss.data.DefaultGithubRepository
import com.example.bliss.data.Emoji
import com.example.bliss.data.User
import com.example.bliss.data.source.GithubDataSource
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

class GithubRepositoryTest {
    val preferences = mockk<Preferences>(relaxed = true)
    val localDataSource = mockk<GithubDataSource>(relaxed = true)
    val remoteDataSource = mockk<GithubDataSource>()
    val timeToLive = 2000L // Expire data after 2 seconds
    val sut = DefaultGithubRepository(preferences, remoteDataSource, localDataSource, timeToLive)

    @Before
    fun setup() {
        coEvery { preferences.getLastUpdateForEmoji() } returns flowOf(0)
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
        // Mock call to getUser from github
        coEvery { remoteDataSource.getUser(any()) } returns User(42, "blissapps", "https...")
        // Mock call to getUser from cache
        coEvery { localDataSource.getUser(any()) } returns null
        coEvery { localDataSource.getEmojiList() } returns emptyList()
    }

    @Test
    fun `Call to getEmojiList() should return a list of emojis`() {
        var emojiList: List<Emoji> = runBlocking { sut.getEmojiList() }
        assertThat(emojiList).hasSize(4)

        // First request should get data from remote since cache is empty
        coVerify(exactly = 1) { remoteDataSource.getEmojiList() }
        coVerify(exactly = 0) { localDataSource.getEmojiList() }

        coEvery { preferences.getLastUpdateForEmoji() } returns flowOf(
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
    fun `Call to getEmojiList() should get from remote after cache expiry time`() {
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

        coEvery { preferences.getLastUpdateForEmoji() } returns flowOf(
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

    @Test
    fun `Call to getUser() should return a User from cache or remote`() {
        var user = runBlocking { sut.getUser("blissapps") }

        coVerify(exactly = 1) { localDataSource.getUser("blissapps") }
        coVerify(exactly = 1) { remoteDataSource.getUser("blissapps") }
        coVerify(exactly = 1) { localDataSource.saveUser(any()) }
        assertThat(user).isNotNull()
        assertThat(user!!.id).isEqualTo(42)
        assertThat(user.login).isEqualTo("blissapps")
        assertThat(user.avatarUrl).isEqualTo("https...")

        // Simulate cached data
        coEvery { sut.getUser("blissapps") } returns User(42, "blissapps", "https...")
        user = runBlocking { sut.getUser("blissapps") }

        coVerify(exactly = 1) { remoteDataSource.getUser("blissapps") }
        coVerify(exactly = 2) { localDataSource.getUser("blissapps") }
        coVerify(exactly = 1) { localDataSource.saveUser(any()) }
        assertThat(user).isNotNull()
        assertThat(user!!.id).isEqualTo(42)
        assertThat(user.login).isEqualTo("blissapps")
        assertThat(user.avatarUrl).isEqualTo("https...")
    }
}
