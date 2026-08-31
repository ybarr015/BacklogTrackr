package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable




@Serializable
data class Character(
    val id: ULong,
    val akas: List<String>,
    val countryName: String,
    val createdAt: Long? = null,
    val description: String,
    val games: List<Game>,
    @Deprecated("Deprecated by proto definition")
    val gender: GenderGenderEnum,
    val mugShot: CharacterMugShot? = null,
    val name: String,
    val slug: String,
    @Deprecated("Deprecated by proto definition")
    val species: CharacterSpeciesEnum,
    val updatedAt: Long? = null,
    val url: String,
    val checksum: String,
    val characterGender: CharacterGender? = null,
    val characterSpecies: CharacterSpecie? = null
)
