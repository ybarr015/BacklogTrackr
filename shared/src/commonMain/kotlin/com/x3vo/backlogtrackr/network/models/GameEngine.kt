package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable




@Serializable
data class GameEngine(
    val id: ULong,
    val companies: List<Company>,
    val createdAt: Long? = null,
    val description: String,
    val logo: GameEngineLogo? = null,
    val name: String,
    val platforms: List<Platform>,
    val slug: String,
    val updatedAt: Long? = null,
    val url: String,
    val checksum: String
)
