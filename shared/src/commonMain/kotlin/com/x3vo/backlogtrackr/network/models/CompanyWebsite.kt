package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable

@Serializable
data class CompanyWebsite(
    val id: ULong,
    @Deprecated("Deprecated by proto definition")
    val category: WebsiteCategoryEnum,
    val trusted: Boolean,
    val url: String,
    val checksum: String,
    val type: WebsiteType? = null
)
