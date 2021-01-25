package com.example.bliss.ui.avatarlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bliss.data.GithubRepository
import com.example.bliss.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AvatarListViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : ViewModel() {
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    fun loadAvatars() {
        viewModelScope.launch {
            _users.postValue(githubRepository.getUsers())
        }
    }

    fun removeUser(user: User) {
        viewModelScope.launch {
            githubRepository.removeUser(user)
        }
    }
}
