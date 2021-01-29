package com.example.bliss.data.source.local

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.bliss.data.source.GithubDataSource
import com.example.bliss.data.source.Repository
import timber.log.Timber

@OptIn(ExperimentalPagingApi::class)
class RepositoryRemoteMediator(
    private val remoteDataSource: GithubDataSource,
    private val localDataSource: GithubDataSource
) : RemoteMediator<Int, Repository>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Repository>
    ): MediatorResult {
        val loadKey = when (loadType) {
            LoadType.REFRESH -> null
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
                lastItem.page
            }
        }

        val page = if (loadKey != null) loadKey + 1 else 1
        return try {
            val repos = remoteDataSource.getUserRepos("google", page, 15)
            if (loadType == LoadType.REFRESH) {
                localDataSource.clearAllRepos()
            }
            localDataSource.saveRepos(repos.map { it.copy(username = "google", page = page) })

            MediatorResult.Success(
                endOfPaginationReached = repos.isEmpty()
            )
        } catch (ex: Exception) {
            Timber.e(ex)
            MediatorResult.Error(ex)
        }
    }
}
