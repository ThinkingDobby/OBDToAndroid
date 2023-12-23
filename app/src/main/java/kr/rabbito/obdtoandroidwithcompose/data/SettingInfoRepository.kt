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

interface SettingInfoRepository {
    suspend fun getMacAddress(): Flow<String?>
    suspend fun setMacAddress(macAddress : String)
    suspend fun clearMacAddress()
}

class DataStoreRepository(private val context: Context) : SettingInfoRepository{
    private val Context.dataStore by preferencesDataStore(name = "dataStore")
    private val macAddressKey = stringPreferencesKey("macAddressKey")

    override suspend fun getMacAddress(): Flow<String?> {
        return context.dataStore.data
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
    }


    override suspend fun setMacAddress(macAddress : String){
        context.dataStore.edit { preferences ->
            preferences[macAddressKey] = macAddress
        }
    }

    override suspend fun clearMacAddress() {   // 테스트용
        context.dataStore.edit {
            it.clear()
        }
    }
}