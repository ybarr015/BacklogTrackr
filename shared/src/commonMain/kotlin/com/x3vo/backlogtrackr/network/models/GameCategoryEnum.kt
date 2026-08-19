package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable

@Serializable
enum class GameCategoryEnum {
    @Deprecated("Deprecated by proto definition")
    MAIN_GAME,
    @Deprecated("Deprecated by proto definition")
    DLC_ADDON,
    @Deprecated("Deprecated by proto definition")
    EXPANSION,
    @Deprecated("Deprecated by proto definition")
    BUNDLE,
    @Deprecated("Deprecated by proto definition")
    STANDALONE_EXPANSION,
    @Deprecated("Deprecated by proto definition")
    MOD,
    @Deprecated("Deprecated by proto definition")
    EPISODE,
    @Deprecated("Deprecated by proto definition")
    SEASON,
    @Deprecated("Deprecated by proto definition")
    REMAKE,
    @Deprecated("Deprecated by proto definition")
    REMASTER,
    @Deprecated("Deprecated by proto definition")
    EXPANDED_GAME,
    @Deprecated("Deprecated by proto definition")
    PORT,
    @Deprecated("Deprecated by proto definition")
    FORK,
    @Deprecated("Deprecated by proto definition")
    PACK,
    @Deprecated("Deprecated by proto definition")
    UPDATE
}
