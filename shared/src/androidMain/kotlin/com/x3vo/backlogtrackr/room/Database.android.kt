package com.x3vo.backlogtrackr.room

import android.content.Context
import androidx.room3.Room
import androidx.room3.RoomDatabase

fun getDatabaseBuilder(context: Context) : RoomDatabase.Builder<BacklogTrackrDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("room_database.db")
    return Room.databaseBuilder<BacklogTrackrDatabase> (
        context = appContext,
        name = dbFile.absolutePath
    )

}