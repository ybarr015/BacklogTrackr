package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable

@Serializable
data class Website(
    val id: ULong,
    @Deprecated("Deprecated by proto definition")
    val category: WebsiteCategoryEnum,
    val game: Game? = null,
    val trusted: Boolean,
    val url: String,
    val checksum: String,
    val type: WebsiteType? = null
)
