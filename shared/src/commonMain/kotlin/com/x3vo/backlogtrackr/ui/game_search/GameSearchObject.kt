package com.x3vo.backlogtrackr.ui.game_search

data class GameSearchObject(
    val id: ULong = 1UL,
    val name: String = "",
    val coverURL: String = "",
    val summary: String = "",
    val timeToBeat: String? = null
)
