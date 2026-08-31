package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable

@Serializable
data class GameVersionFeatureResult(val gameversionfeatures: List<GameVersionFeature>)
