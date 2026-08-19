package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable

@Serializable
data class PlatformVersionCompany(
    val id: ULong,
    val comment: String,
    val company: Company? = null,
    val developer: Boolean,
    val manufacturer: Boolean,
    val checksum: String
)
