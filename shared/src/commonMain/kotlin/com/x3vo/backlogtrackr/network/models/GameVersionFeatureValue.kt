package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable

@Serializable
data class GameVersionFeatureValue(
    val id: ULong,
    val game: Game? = null,
    val gameFeature: GameVersionFeature? = null,
    val includedFeature: GameVersionFeatureValueIncludedFeatureEnum,
    val note: String,
    val checksum: String
)
