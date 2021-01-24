package com.example.bliss

import com.example.bliss.data.Emoji
import com.example.bliss.data.source.remote.EmojiResponseConverter
import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okio.Buffer
import org.junit.Test

class EmojiConverterTest {

    val sut = EmojiResponseConverter()
    val moshi = Moshi.Builder()
        .add(sut)
        .build()

    val json = """
        {
            "+1": "https://github.githubassets.com/images/icons/emoji/unicode/1f44d.png?v8",
            "-1": "https://github.githubassets.com/images/icons/emoji/unicode/1f44e.png?v8",
            "100": "https://github.githubassets.com/images/icons/emoji/unicode/1f44af.png?v8",
            "1st_place_medal": "https://github.githubassets.com/images/icons/emoji/unicode/1f947.png?v8"
        }
    """.trimIndent()

    @Test
    fun `EmojiResponseConverter fromJson call should return a list of Emojis`() {
        val emojis = sut.fromJson(JsonReader.of(Buffer().writeUtf8(json)))
        assertThat(emojis).hasSize(4)
        val emoji0 = emojis[0]
        val emoji1 = emojis[1]
        val emoji2 = emojis[2]
        val emoji3 = emojis[3]
        assertThat(emoji0.shortCode).isEqualTo("+1")
        assertThat(emoji0.url).endsWith("1f44d.png?v8")
        assertThat(emoji1.shortCode).isEqualTo("-1")
        assertThat(emoji1.url).endsWith("1f44e.png?v8")
        assertThat(emoji2.shortCode).isEqualTo("100")
        assertThat(emoji2.url).endsWith("1f44af.png?v8")
        assertThat(emoji3.shortCode).isEqualTo("1st_place_medal")
        assertThat(emoji3.url).endsWith("1f947.png?v8")
    }

    @Test
    fun `Moshi should properly convert string to a list of emojis`() {
        val type = Types.newParameterizedType(List::class.java, Emoji::class.java)
        val adapter: JsonAdapter<List<Emoji>> = moshi.adapter(type)
        val emojis = adapter.fromJson(json).orEmpty()
        assertThat(emojis).hasSize(4)
        val emoji0 = emojis[0]
        val emoji1 = emojis[1]
        val emoji2 = emojis[2]
        val emoji3 = emojis[3]
        assertThat(emoji0.shortCode).isEqualTo("+1")
        assertThat(emoji0.url).endsWith("1f44d.png?v8")
        assertThat(emoji1.shortCode).isEqualTo("-1")
        assertThat(emoji1.url).endsWith("1f44e.png?v8")
        assertThat(emoji2.shortCode).isEqualTo("100")
        assertThat(emoji2.url).endsWith("1f44af.png?v8")
        assertThat(emoji3.shortCode).isEqualTo("1st_place_medal")
        assertThat(emoji3.url).endsWith("1f947.png?v8")
    }
}