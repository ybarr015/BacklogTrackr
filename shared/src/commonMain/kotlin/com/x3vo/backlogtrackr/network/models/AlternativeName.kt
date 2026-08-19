package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable

@Serializable
data class AlternativeName(
    val id: ULong,
    val comment: String,
    val game: Game? = null,
    val name: String,
    val checksum: String
)
