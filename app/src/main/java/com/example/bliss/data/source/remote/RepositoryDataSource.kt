package com.example.bliss.data.source.remote

import androidx.paging.PageKeyedDataSource
import com.example.bliss.data.source.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepositoryDataSource(
    private val githubService: GithubService
) : PageKeyedDataSource<Int, Repository>() {
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Repository>
    ) {
        scope.launch {
            val repos = githubService.getUserRepos("google", 1, 15)
            callback.onResult(repos, null, 2)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Repository>) {
        scope.launch {
            val repos = githubService.getUserRepos("google", params.key, 15)
            callback.onResult(repos, if (params.key > 0) params.key - 1 else 0)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Repository>) {
        scope.launch {
            val repos = githubService.getUserRepos("google", params.key, 15)
            callback.onResult(repos, params.key + 1)
        }
    }
}
