package com.example.bliss.ui.googlerepos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.bliss.data.GithubRepository
import com.example.bliss.data.source.Repository
import com.example.bliss.data.source.remote.GithubService
import com.example.bliss.data.source.remote.RepositoryDataSource
import com.example.bliss.data.source.remote.RepositoryDataSourceFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class GoogleReposViewModel @Inject constructor(
    private val githubService: GithubService,
    private val githubRepository: GithubRepository
): ViewModel() {
    val repositoryPagedList: LiveData<PagedList<Repository>>
    private val liveDataSource: LiveData<RepositoryDataSource>

    init {
        val itemDataSourceFactory = RepositoryDataSourceFactory(githubService)
        liveDataSource = itemDataSourceFactory.repoLiveDataSource
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(15)
            .build()
        repositoryPagedList = LivePagedListBuilder(itemDataSourceFactory, config)
            .build()
    }

    fun loadGoogleRepos() {
//        viewModelScope.launch {
//            try {
//                _repos.postValue(Result.success(githubRepository.getGoogleRepos()))
//            } catch (ex: Exception) {
//                ex.printStackTrace()
//                _repos.postValue(Result.failure(ex))
//            }
//        }
    }
}
