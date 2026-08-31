package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable

@Serializable
data class PlatformVersion(
    val id: ULong,
    val companies: List<PlatformVersionCompany>,
    val connectivity: String,
    val cpu: String,
    val graphics: String,
    val mainManufacturer: PlatformVersionCompany? = null,
    val media: String,
    val memory: String,
    val name: String,
    val os: String,
    val output: String,
    val platformLogo: PlatformLogo? = null,
    val platformVersionReleaseDates: List<PlatformVersionReleaseDate>,
    val resolutions: String,
    val slug: String,
    val sound: String,
    val storage: String,
    val summary: String,
    val url: String,
    val checksum: String
)
