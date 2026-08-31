package com.x3vo.backlogtrackr.room.game_attribute_settings.platforms

import androidx.room3.Entity
import androidx.room3.Index
import androidx.room3.PrimaryKey
import com.x3vo.backlogtrackr.room.game_attribute_settings.GameAttributeEntity

@Entity(
    tableName = "platforms",
    indices = [Index(value = ["platformName"], unique = true)]
)
data class PlatformEntity(
    @PrimaryKey(autoGenerate = true) override val id: Long = 0L,
    val platformName: String
): GameAttributeEntity {
    override val attributeName: String
        get() = platformName
}
