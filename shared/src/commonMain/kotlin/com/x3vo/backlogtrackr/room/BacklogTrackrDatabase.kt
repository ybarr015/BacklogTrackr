package com.x3vo.backlogtrackr.room

import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase
import androidx.room3.RoomDatabaseConstructor
import com.x3vo.backlogtrackr.room.LibraryGame.GamePcStoreCrossRef
import com.x3vo.backlogtrackr.room.LibraryGame.GamePlatformCrossRef
import com.x3vo.backlogtrackr.room.LibraryGame.LibraryGameDao
import com.x3vo.backlogtrackr.room.LibraryGame.LibraryGameEntity
import com.x3vo.backlogtrackr.room.game_attribute_settings.gameprogress.GameProgressOptionEntity
import com.x3vo.backlogtrackr.room.game_attribute_settings.gameprogress.GameProgressOptionsDao
import com.x3vo.backlogtrackr.room.game_attribute_settings.pcstores.PCStoreDAO
import com.x3vo.backlogtrackr.room.game_attribute_settings.pcstores.PCStoreEntity
import com.x3vo.backlogtrackr.room.game_attribute_settings.platforms.PlatformDao
import com.x3vo.backlogtrackr.room.game_attribute_settings.platforms.PlatformEntity

@Database(
    entities = [
        PlatformEntity::class,
        PCStoreEntity::class,
        GameProgressOptionEntity::class,
        LibraryGameEntity::class,
        GamePlatformCrossRef::class,
        GamePcStoreCrossRef::class
    ],
    version = 6,
    exportSchema = true
)
@ConstructedBy(AppDataBaseConstructor::class)
abstract class BacklogTrackrDatabase : RoomDatabase() {
    abstract fun getPlatformDao(): PlatformDao
    abstract fun getPCStoreDAO(): PCStoreDAO
    abstract fun getGameProgressOptionsDao(): GameProgressOptionsDao
    abstract fun getLibraryGameDao(): LibraryGameDao

}

@Suppress("KotlinNoActualForExpect")
expect object AppDataBaseConstructor: RoomDatabaseConstructor<BacklogTrackrDatabase> {
    override fun initialize(): BacklogTrackrDatabase
}
