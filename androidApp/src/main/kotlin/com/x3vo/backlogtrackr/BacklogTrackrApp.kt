package com.x3vo.backlogtrackr

import android.app.Application
import com.x3vo.backlogtrackr.network.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.KoinConfiguration

class BacklogTrackrApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin(
            configuration = KoinConfiguration {
                androidContext(this@BacklogTrackrApp)
            }
        )
    }
}
