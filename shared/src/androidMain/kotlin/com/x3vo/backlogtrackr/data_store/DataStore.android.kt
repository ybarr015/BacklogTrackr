package com.x3vo.backlogtrackr.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences


fun provideDataStore(context: Context): DataStore<Preferences> = createDataStore(
    path = { context.filesDir.resolve(dataStoreFileName).absolutePath }
)