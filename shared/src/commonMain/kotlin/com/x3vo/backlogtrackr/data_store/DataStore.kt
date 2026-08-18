package com.x3vo.backlogtrackr.data_store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath


internal const val dataStoreFileName = "backlog.preferences_pb"

fun createDataStore(path: () -> String) =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = { path().toPath() }
    )

