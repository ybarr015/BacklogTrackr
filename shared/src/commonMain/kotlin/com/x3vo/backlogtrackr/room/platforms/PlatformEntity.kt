package com.x3vo.backlogtrackr.room.platforms

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "platforms")
data class PlatformEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val platformName: String
)
