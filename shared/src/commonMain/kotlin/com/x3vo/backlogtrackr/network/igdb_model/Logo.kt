package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable

@Serializable
data class Logo(
    val id: ULong,
    val alphaChannel: Boolean,
    val animated: Boolean,
    val game: Game? = null,
    val height: Int,
    val imageId: String,
    val url: String,
    val width: Int,
    val checksum: String,
    val imageType: ImageType? = null
)
