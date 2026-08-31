package com.x3vo.backlogtrackr.room.game_attribute_settings.pcstores

import androidx.room3.Entity
import androidx.room3.Index
import androidx.room3.PrimaryKey
import com.x3vo.backlogtrackr.room.game_attribute_settings.GameAttributeEntity

@Entity(
    tableName = "PCStores",
    indices = [Index(value = ["pcStoreName"], unique = true)]
)
data class PCStoreEntity(
    @PrimaryKey(autoGenerate = true) override val id: Long = 0L,
    val pcStoreName: String
): GameAttributeEntity {
    override val attributeName: String
        get() = pcStoreName
}