package com.example.bliss.data.source.remote

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.bliss.data.source.Repository

class RepositoryDataSourceFactory(
    private val githubService: GithubService
) : DataSource.Factory<Int, Repository>() {
    val repoLiveDataSource = MutableLiveData<RepositoryDataSource>()

    override fun create(): DataSource<Int, Repository> {
        val repoDataSource = RepositoryDataSource(githubService)
        repoLiveDataSource.postValue(repoDataSource)
        return repoDataSource
    }
}
