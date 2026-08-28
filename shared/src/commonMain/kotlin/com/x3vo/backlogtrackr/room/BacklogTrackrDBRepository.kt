package com.x3vo.backlogtrackr.room

import com.x3vo.backlogtrackr.room.platforms.PlatformDao
import com.x3vo.backlogtrackr.room.platforms.PlatformEntity


class BacklogTrackrDBRepository (
    private val platformDao: PlatformDao
) {

    private val defaultPlatforms = listOf("PC", "PS5", "Xbox Series X", "Switch")

    suspend fun getAllPlatforms() = platformDao.getAllPlatforms()

    suspend fun resetPlatforms() {
        platformDao.deleteAllPlatforms()
        for (platform in defaultPlatforms) {
            addPlatform(platform)
        }
    }

    suspend fun addPlatform(platformName: String)  {
        val newEntity = PlatformEntity(
            platformName = platformName
        )
        platformDao.addPlatform(newEntity)
    }

    suspend fun deletePlatform(platformName: String)  {
        platformDao.deletePlatform(platformName)
    }
}