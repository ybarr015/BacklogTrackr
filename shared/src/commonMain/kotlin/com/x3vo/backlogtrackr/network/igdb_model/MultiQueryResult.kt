package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable

@Serializable
data class MultiQueryResult(val name: String, val results: List<ByteArray>, val count: Long)
