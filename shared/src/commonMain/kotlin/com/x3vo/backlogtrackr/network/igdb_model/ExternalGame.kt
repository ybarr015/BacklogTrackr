package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable




@Serializable
data class ExternalGame(
    val id: ULong,
    @Deprecated("Deprecated by proto definition")
    val category: ExternalGameCategoryEnum,
    val createdAt: Long? = null,
    val game: Game? = null,
    val name: String,
    val uid: String,
    val updatedAt: Long? = null,
    val url: String,
    val year: Int,
    @Deprecated("Deprecated by proto definition")
    val media: ExternalGameMediaEnum,
    val platform: Platform? = null,
    val countries: List<Int>,
    val checksum: String,
    val externalGameSource: ExternalGameSource? = null,
    val gameReleaseFormat: GameReleaseFormat? = null
)
