package com.x3vo.backlogtrackr.room.platforms

import androidx.room3.Entity
import androidx.room3.Index
import androidx.room3.PrimaryKey

@Entity(
    tableName = "platforms",
    indices = [Index(value = ["platformName"], unique = true)]
)
data class PlatformEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val platformName: String
)
