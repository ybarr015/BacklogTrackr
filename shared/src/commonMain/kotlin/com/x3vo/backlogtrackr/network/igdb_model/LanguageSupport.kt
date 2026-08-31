package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable




@Serializable
data class LanguageSupport(
    val id: ULong,
    val game: Game? = null,
    val language: Language? = null,
    val languageSupportType: LanguageSupportType? = null,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
    val checksum: String
)
