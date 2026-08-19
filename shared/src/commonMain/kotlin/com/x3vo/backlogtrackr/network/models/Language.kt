package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable




@Serializable
data class Language(
    val id: ULong,
    val name: String,
    val nativeName: String,
    val locale: String,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
    val checksum: String
)
