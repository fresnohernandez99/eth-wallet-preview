package com.gocash.wallet.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.gocash.wallet.preferences.PreferencesRepositoryImpl
import kotlinx.serialization.json.Json

class PreferencesModule(
    dataStore: DataStore<Preferences>
) {
    private val preferences: PreferencesRepositoryImpl = PreferencesRepositoryImpl(dataStore)

    private val json: Json = Json

    private val KEY_OWNER = "owner"


    /**
     * Global Variables
     */

    suspend fun clearPreferences() {
        preferences.resetAll()
    }

//    suspend fun setSecretReady(value: Boolean): Boolean {
//        preferences.store(KEY_SECRET_ROOM_READY, value)
//        return true
//    }
//
//    suspend fun getSecretCode(): String = preferences.retrieve(KEY_SECRET_ROOM_CODE, "1234")
//
//    suspend fun setSecretCode(value: String): Boolean {
//        preferences.store(KEY_SECRET_ROOM_CODE, value)
//        return true
//    }
//
//    /**
//     * RootUrl
//     */
//    private val keyCallQuality = "KEY_CALL_QUALITY"
//    suspend fun setCallQuality(value: CallQuality) {
//        preferences.store(keyCallQuality, json.encodeToString(value))
//    }
//
//    suspend fun getCallQuality(): CallQuality {
//        val actualJson = preferences.retrieve(
//            keyCallQuality,
//            ""
//        )
//
//        return if (actualJson.isBlank()) CallQuality()
//        else json.decodeFromString(
//            actualJson
//        )
//    }
}