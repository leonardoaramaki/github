package com.example.bliss.data.source

import kotlinx.coroutines.flow.Flow

interface Preferences {
    // Get last time emoji data was fetched from remote.
    suspend fun getLastUpdatedEmojisEpoch(): Flow<Long>
    // Update the last time emoji data was fetched from remote.
    suspend fun putLastUpdatedEmojisEpoch(lastUpdated: Long)
}
