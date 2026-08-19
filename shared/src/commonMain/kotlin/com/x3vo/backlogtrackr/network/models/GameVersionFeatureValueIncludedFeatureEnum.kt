package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable

@Serializable
enum class GameVersionFeatureValueIncludedFeatureEnum {
    NOT_INCLUDED,
    INCLUDED,
    PRE_ORDER_ONLY
}
