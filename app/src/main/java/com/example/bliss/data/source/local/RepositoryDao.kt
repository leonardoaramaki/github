package com.example.bliss.data.source.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bliss.data.source.Repository

@Dao
interface RepositoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repositories: List<Repository>)

    @Query("SELECT * FROM repositories WHERE username=:username ORDER BY page ASC")
    fun pagingSource(username: String): PagingSource<Int, Repository>

    @Query("DELETE FROM repositories")
    suspend fun clearAll()
}
