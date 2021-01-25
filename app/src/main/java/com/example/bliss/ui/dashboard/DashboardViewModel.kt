package com.example.bliss.ui.dashboard

import androidx.lifecycle.*
import com.example.bliss.data.Emoji
import com.example.bliss.data.GithubRepository
import com.example.bliss.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : ViewModel() {
    private val _randomEmoji = MutableLiveData<Emoji>()
    private val _user = MutableLiveData<User>()
    val randomEmoji: LiveData<Emoji> = _randomEmoji
    val user: LiveData<User> = _user

    fun loadRandomEmoji() {
        viewModelScope.launch {
            _randomEmoji.postValue(githubRepository.getEmojiList().random())
        }
    }

    fun searchUser(username: String) {
        viewModelScope.launch {
            _user.postValue(githubRepository.getUser(username))
        }
    }
}
