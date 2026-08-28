package com.x3vo.backlogtrackr.room

import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase
import androidx.room3.RoomDatabaseConstructor
import com.x3vo.backlogtrackr.room.platforms.PlatformDao
import com.x3vo.backlogtrackr.room.platforms.PlatformEntity

@Database(
    entities = [
        PlatformEntity::class
    ],
    version = 1,
    exportSchema = true
)
@ConstructedBy(AppDataBaseConstructor::class)
abstract class BacklogTrackrDatabase : RoomDatabase() {
    abstract fun getPlatformDao(): PlatformDao

}

@Suppress("KotlinNoActualForExpect")
expect object AppDataBaseConstructor: RoomDatabaseConstructor<BacklogTrackrDatabase> {
    override fun initialize(): BacklogTrackrDatabase
}
