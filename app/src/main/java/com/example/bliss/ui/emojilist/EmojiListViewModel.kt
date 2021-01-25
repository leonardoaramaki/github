package com.example.bliss.ui.emojilist

import androidx.lifecycle.*
import com.example.bliss.data.Emoji
import com.example.bliss.data.EmojiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmojiListViewModel @Inject constructor(
    private val emojiRepository: EmojiRepository
) : ViewModel() {
    private val _emojiList = MutableLiveData<List<Emoji>>()
    val emojiList: LiveData<List<Emoji>> = _emojiList

    fun loadEmojis() {
        viewModelScope.launch {
            _emojiList.postValue(emojiRepository.getEmojiList())
        }
    }
}