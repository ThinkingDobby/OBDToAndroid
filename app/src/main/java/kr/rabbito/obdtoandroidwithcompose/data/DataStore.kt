package kr.rabbito.obdtoandroidwithcompose.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStore(private val context: Context) {
    private val Context.dataStore by preferencesDataStore(name = "dataStore")

    private val macAddressKey = stringPreferencesKey("macAddressKey")

    val macAddress : Flow<String?> = context.dataStore.data
        .catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }
        .map {preferences ->
            preferences[macAddressKey]
        }

    suspend fun setMacAddress(macAddress : String){
        context.dataStore.edit { preferences ->
            preferences[macAddressKey] = macAddress
        }
    }

    suspend fun clearMacAddress(){   // 테스트용
        context.dataStore.edit {
            it.clear()
        }
    }
}