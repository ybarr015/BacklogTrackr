package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable

@Serializable
enum class ExternalGameMediaEnum {
    @Deprecated("Deprecated by proto definition")
    EXTERNALGAME_MEDIA_NULL,
    @Deprecated("Deprecated by proto definition")
    EXTERNALGAME_DIGITAL,
    @Deprecated("Deprecated by proto definition")
    EXTERNALGAME_PHYSICAL
}
