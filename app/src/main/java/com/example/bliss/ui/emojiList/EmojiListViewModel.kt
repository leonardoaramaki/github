package com.example.bliss.ui.emojiList

import androidx.lifecycle.*
import com.example.bliss.data.Emoji
import com.example.bliss.data.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmojiListViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : ViewModel() {
    private val _emojiList = MutableLiveData<List<Emoji>>()
    val emojiList: LiveData<List<Emoji>> = _emojiList

    fun loadEmojis() {
        viewModelScope.launch {
            try {
                _emojiList.postValue(githubRepository.getEmojiList())
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}