package com.example.bliss.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bliss.data.Emoji
import com.example.bliss.data.EmojiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val emojiRepository: EmojiRepository
) : ViewModel() {
    private val _randomEmoji = MutableLiveData<Emoji>()
    val randomEmoji: LiveData<Emoji> = _randomEmoji

    fun loadRandomEmoji() {
        viewModelScope.launch {
            val emojiList = emojiRepository.getEmojiList()
            _randomEmoji.postValue(emojiList.random())
        }
    }
}
