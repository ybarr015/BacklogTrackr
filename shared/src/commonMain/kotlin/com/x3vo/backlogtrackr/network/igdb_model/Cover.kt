package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable

@Serializable
data class Cover(
    val id: ULong,
//    val alphaChannel: Boolean,
//    val animated: Boolean,
//    val game: Game? = null,
    val height: Int? = null,
//    val imageId: String,
    val url: String? = null,
    val width: Int? = null,
//    val checksum: String,
//    val gameLocalization: GameLocalization? = null,
//    val imageType: ImageType? = null
)
