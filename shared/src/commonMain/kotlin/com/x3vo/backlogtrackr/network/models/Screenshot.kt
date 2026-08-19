package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable

@Serializable
data class Screenshot(
    val id: ULong,
    val alphaChannel: Boolean,
    val animated: Boolean,
    val game: Game? = null,
    val height: Int,
    val imageId: String,
    val url: String,
    val width: Int,
    val checksum: String
)
