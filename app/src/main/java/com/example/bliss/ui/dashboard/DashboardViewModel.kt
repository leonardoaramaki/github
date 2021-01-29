package com.example.bliss.ui.dashboard

import androidx.lifecycle.*
import com.example.bliss.data.Emoji
import com.example.bliss.data.GithubRepository
import com.example.bliss.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : ViewModel() {
    private val _randomEmoji = MutableLiveData<Result<Emoji?>>()
    private val _user = MutableLiveData<Result<User?>>()
    val randomEmoji: LiveData<Result<Emoji?>> = _randomEmoji
    val user: LiveData<Result<User?>> = _user

    fun loadRandomEmoji() {
        viewModelScope.launch {
            try {
                val emojis = githubRepository.getEmojiList()
                if (emojis.isEmpty()) {
                    _randomEmoji.postValue(Result.success(null))
                } else {
                    _randomEmoji.postValue(Result.success(emojis.random()))
                }
            } catch (ex: Exception) {
                Timber.e(ex)
                _randomEmoji.postValue(Result.failure(ex))
            }
        }
    }

    fun searchUser(username: String) {
        viewModelScope.launch {
            try {
                _user.postValue(Result.success(githubRepository.getUser(username)))
            } catch (ex: Exception) {
                Timber.e(ex)
                _user.postValue(Result.failure(ex))
            }
        }
    }
}
