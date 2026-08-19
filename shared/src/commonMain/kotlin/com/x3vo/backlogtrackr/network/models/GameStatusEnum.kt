package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable

@Serializable
enum class GameStatusEnum {
    @Deprecated("Deprecated by proto definition")
    RELEASED,
    @Deprecated("Deprecated by proto definition")
    ALPHA,
    @Deprecated("Deprecated by proto definition")
    BETA,
    @Deprecated("Deprecated by proto definition")
    EARLY_ACCESS,
    @Deprecated("Deprecated by proto definition")
    OFFLINE,
    @Deprecated("Deprecated by proto definition")
    CANCELLED,
    @Deprecated("Deprecated by proto definition")
    RUMORED,
    @Deprecated("Deprecated by proto definition")
    DELISTED
}
