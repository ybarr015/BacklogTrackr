package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable

@Serializable
data class MultiplayerMode(
    val id: ULong,
    val campaigncoop: Boolean,
    val dropin: Boolean,
    val game: Game? = null,
    val lancoop: Boolean,
    val offlinecoop: Boolean,
    val offlinecoopmax: Int,
    val offlinemax: Int,
    val onlinecoop: Boolean,
    val onlinecoopmax: Int,
    val onlinemax: Int,
    val platform: Platform? = null,
    val splitscreen: Boolean,
    val splitscreenonline: Boolean,
    val checksum: String
)
