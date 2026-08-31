package com.x3vo.backlogtrackr.room.game_attribute_settings.gameprogress

import androidx.room3.Entity
import androidx.room3.Index
import androidx.room3.PrimaryKey
import com.x3vo.backlogtrackr.room.game_attribute_settings.GameAttributeEntity

@Entity(
    tableName = "GameProgressOptions",
    indices = [Index(value = ["progressOption"], unique = true)]
)
data class GameProgressOptionEntity(
    @PrimaryKey(autoGenerate = true) override val id: Long = 0L,
    val progressOption: String
): GameAttributeEntity {
    override val attributeName: String
        get() = progressOption
}
