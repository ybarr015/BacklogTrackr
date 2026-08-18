package com.x3vo.backlogtrackr.koin

import com.x3vo.backlogtrackr.data_store.provideDataStore
import org.koin.dsl.module

actual val platformModule = module {
    single { provideDataStore() }
}
