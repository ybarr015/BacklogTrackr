package com.x3vo.backlogtrackr.room

import com.x3vo.backlogtrackr.room.platforms.PlatformDao
import com.x3vo.backlogtrackr.room.platforms.PlatformEntity


class BacklogTrackrDBRepository (
    private val platformDao: PlatformDao
) {

    suspend fun getAllPlatforms() = platformDao.getAllPlatforms()

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