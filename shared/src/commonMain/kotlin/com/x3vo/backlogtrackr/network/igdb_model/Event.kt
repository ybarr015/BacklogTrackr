package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable




@Serializable
data class Event(
    val id: ULong,
    val name: String,
    val description: String,
    val slug: String,
    val eventLogo: EventLogo? = null,
    val startTime: Long? = null,
    val timeZone: String,
    val endTime: Long? = null,
    val liveStreamUrl: String,
    val games: List<Game>,
    val videos: List<GameVideo>,
    val eventNetworks: List<EventNetwork>,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
    val checksum: String
)
