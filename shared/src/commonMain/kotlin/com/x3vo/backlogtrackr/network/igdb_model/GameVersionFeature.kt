package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable

@Serializable
data class GameVersionFeature(
    val id: ULong,
    val category: GameVersionFeatureCategoryEnum,
    val description: String,
    val position: Int,
    val title: String,
    val values: List<GameVersionFeatureValue>,
    val checksum: String
)
