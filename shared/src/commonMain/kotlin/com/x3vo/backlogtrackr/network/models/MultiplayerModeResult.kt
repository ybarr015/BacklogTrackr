package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable

@Serializable
data class MultiplayerModeResult(val multiplayermodes: List<MultiplayerMode>)
