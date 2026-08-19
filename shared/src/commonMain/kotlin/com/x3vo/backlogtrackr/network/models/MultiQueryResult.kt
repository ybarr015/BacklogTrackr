package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable

@Serializable
data class MultiQueryResult(val name: String, val results: List<ByteArray>, val count: Long)
