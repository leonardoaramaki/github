package com.example.bliss.data.source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.createDataStore
import com.example.bliss.data.source.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesImpl @Inject constructor(context: Context) : Preferences {
    private val dataStore: DataStore<androidx.datastore.preferences.core.Preferences> =
        context.createDataStore(name = "preferences")

    override suspend fun getLastUpdatedEmojisEpoch(): Flow<Long> {
        return dataStore.data.map { preferences ->
            preferences[LAST_UPDATED_EMOJIS] ?: 0L
        }
    }

    override suspend fun putLastUpdatedEmojisEpoch(lastUpdated: Long) {
        dataStore.edit { preferences ->
            preferences[LAST_UPDATED_EMOJIS] = lastUpdated
        }
    }

    private companion object {
        val LAST_UPDATED_EMOJIS = longPreferencesKey("last_updated_emojis")
    }
}
