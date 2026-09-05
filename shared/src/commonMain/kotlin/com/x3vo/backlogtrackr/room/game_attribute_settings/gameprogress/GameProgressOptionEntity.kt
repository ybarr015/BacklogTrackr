package com.x3vo.backlogtrackr.room.game_attribute_settings.gameprogress

import androidx.room3.Entity
import androidx.room3.Index
import androidx.room3.PrimaryKey

@Entity(
    tableName = "GameProgressOptions",
    indices = [Index(value = ["progressOption"], unique = true)]
)
data class GameProgressOptionEntity(
    @PrimaryKey val progressOption: String
)
