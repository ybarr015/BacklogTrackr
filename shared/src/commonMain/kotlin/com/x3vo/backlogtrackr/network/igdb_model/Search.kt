package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable




@Serializable
data class Search(
    val id: ULong? = null,
    val alternativeName: String? = null,
    val character: Character? = null,
    val collection: CollectionDto? = null,
    val company: Company? = null,
    val description: String? = null,
    val game: Game? = null,
    val name: String? = null,
    val platform: Platform? = null,
    val publishedAt: Long? = null,
    val testDummy: TestDummy? = null,
    val theme: Theme? = null,
    val checksum: String? = null
)
