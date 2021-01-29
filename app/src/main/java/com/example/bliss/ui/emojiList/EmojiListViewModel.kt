package com.example.bliss.ui.emojiList

import androidx.lifecycle.*
import com.example.bliss.data.Emoji
import com.example.bliss.data.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EmojiListViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : ViewModel() {
    private val _emojiList = MutableLiveData<Result<List<Emoji>>>()
    val emojiList: LiveData<Result<List<Emoji>>> = _emojiList

    fun loadEmojis(refresh: Boolean = false) {
        viewModelScope.launch {
            try {
                _emojiList.postValue(Result.success(githubRepository.getEmojiList()))
            } catch (ex: Exception) {
                Timber.e(ex)
                _emojiList.postValue(Result.failure(ex))
            }
        }
    }
}