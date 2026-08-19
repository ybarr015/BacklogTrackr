package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable

@Serializable
data class PlayerPerspectiveResult(val playerperspectives: List<PlayerPerspective>)
