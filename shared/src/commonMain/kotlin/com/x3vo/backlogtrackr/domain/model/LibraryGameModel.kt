package com.x3vo.backlogtrackr.domain.model

data class LibraryGameDetailModel (
    val gameId: Long,
    val name: String,
    val coverUrl: String,
    val timeToBeat: String,
    val summary: String,
    val isOwned: Boolean,
    val progressOption: GameAttribute.GameProgressModel,
    val platforms: List<GameAttribute.PlatformModel>,
    val pcStores: List<GameAttribute.PCStoreModel>,
    val addedAt: Long
)