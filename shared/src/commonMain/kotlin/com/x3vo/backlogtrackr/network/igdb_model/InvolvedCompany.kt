package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable




@Serializable
data class InvolvedCompany(
    val id: ULong,
    val company: Company? = null,
    val createdAt: Long? = null,
    val developer: Boolean,
    val game: Game? = null,
    val porting: Boolean,
    val publisher: Boolean,
    val supporting: Boolean,
    val updatedAt: Long? = null,
    val checksum: String
)
