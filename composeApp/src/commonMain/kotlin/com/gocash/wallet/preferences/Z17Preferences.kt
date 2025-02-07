package com.gocash.wallet.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

open class Z17Preferences(
    val dataStore: DataStore<Preferences>
) : PreferencesRepositoryImpl(dataStore)