package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable




@Serializable
data class Search(
    val id: ULong,
    val alternativeName: String,
    val character: Character? = null,
    val collection: CollectionDto? = null,
    val company: Company? = null,
    val description: String,
    val game: Game? = null,
    val name: String,
    val platform: Platform? = null,
    val publishedAt: Long? = null,
    val testDummy: TestDummy? = null,
    val theme: Theme? = null,
    val checksum: String
)
