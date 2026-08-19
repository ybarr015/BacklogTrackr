package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable

@Serializable
data class NetworkTypeResult(val networktypes: List<NetworkType>)
