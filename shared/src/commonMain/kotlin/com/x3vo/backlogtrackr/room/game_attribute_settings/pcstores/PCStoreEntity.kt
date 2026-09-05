package com.x3vo.backlogtrackr.room.game_attribute_settings.pcstores

import androidx.room3.Entity
import androidx.room3.Index
import androidx.room3.PrimaryKey

@Entity(
    tableName = "PCStores",
    indices = [Index(value = ["pcStoreName"], unique = true)]
)
data class PCStoreEntity(
    @PrimaryKey val pcStoreName: String
)