package com.x3vo.backlogtrackr.domain.model

data class LibraryGameDetailModel (
    val gameId: Long = 0L,
    val name: String = "",
    val coverUrl: String = "",
    val timeToBeat: String = "",
    val summary: String = "",
    val isOwned: Boolean = false,
    val progressOption: GameAttribute.GameProgressModel = GameAttribute.GameProgressModel(
        DefaultGameProgress.NOT_STARTED.progressName
    ),
    val platforms: List<GameAttribute.PlatformModel> = listOf(),
    val pcStores: List<GameAttribute.PCStoreModel> = listOf(),
    val addedAt: Long = 0L
)