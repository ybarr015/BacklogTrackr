package com.x3vo.backlogtrackr.room.game_attribute_settings.platforms

import androidx.room3.Entity
import androidx.room3.Index
import androidx.room3.PrimaryKey

@Entity(
    tableName = "platforms",
    indices = [Index(value = ["platformName"], unique = true)]
)
data class PlatformEntity(
    @PrimaryKey val platformName: String
)
