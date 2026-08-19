package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable

@Serializable
data class CharacterSpecieResult(val characterspecies: List<CharacterSpecie>)
