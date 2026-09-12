package com.x3vo.backlogtrackr.domain.model

data class LibraryGameDetailModel (
    val gameId: Long = 0L,
    val name: String = "",
    val coverUrl: String = "",
    val timeToBeat: String = "",
    val summary: String = "",
    val isOwned: Boolean = false,
    val progressOption: GameAttribute.GameProgressModel = DefaultGameProgress.NOT_STARTED.toGameProgressModel(),
    val platforms: Set<GameAttribute.PlatformModel> = setOf(),
    val pcStores: Set<GameAttribute.PCStoreModel> = setOf(),
    val addedAt: Long = 0L
)