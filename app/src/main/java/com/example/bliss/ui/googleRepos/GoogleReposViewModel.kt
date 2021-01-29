package com.example.bliss.ui.googleRepos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.bliss.data.GithubRepository
import com.example.bliss.data.source.Repository
import com.example.bliss.data.source.local.RepositoryRemoteMediator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onErrorReturn
import kotlinx.coroutines.flow.retry
import java.lang.RuntimeException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
@HiltViewModel
class GoogleReposViewModel @Inject constructor(
    repository: GithubRepository
) : ViewModel() {
    val repos = repository.getGoogleRepos().cachedIn(viewModelScope)
}
