package com.x3vo.backlogtrackr.domain.model


enum class DefaultPlatforms(val platformName: String) {
    PC("PC"),
    PLAYSTATION("PS5"),
    XBOX("Xbox Series X/S"),
    SWITCH("Switch 1/2"),
    MOBILE("Mobile")
}
enum class DefaultPCStores(val storeName: String) {
    STEAM("Steam"),
    EPIC("Epic Games"),
    GOG("GOG"),
    UBISOFT("Ubisoft"),
    EA("EA")
}
enum class DefaultGameProgress(val progressName: String){
    NOT_STARTED("Not Started"),
    STARTED("Started"),
    IN_PROGRESS("In Progress"),
    FINISHED("Finished"),
    DROPPED("Dropped")

}

val DefaultPlatformsSet = DefaultPlatforms.entries.map { GameAttribute.PlatformModel(it.platformName) }.toSet()
val DefaultPCStoresSet = DefaultPCStores.entries.map { GameAttribute.PCStoreModel(it.storeName) }.toSet()
val DefaultGameProgressSet = DefaultGameProgress.entries.map { GameAttribute.GameProgressModel(it.progressName) }.toSet()

sealed interface GameAttribute{
    val attributeName: String

    data class PlatformModel(val platformName: String): GameAttribute {
        override val attributeName: String = platformName
    }
    data class PCStoreModel(val pcStoreName: String): GameAttribute {
        override val attributeName: String = pcStoreName
    }
    data class GameProgressModel(val progressName: String): GameAttribute {
        override val attributeName: String = progressName
    }
}