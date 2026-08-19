package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable

@Serializable
enum class AgeRatingCategoryEnum {
    @Deprecated("Deprecated by proto definition")
    AGERATING_CATEGORY_NULL,
    @Deprecated("Deprecated by proto definition")
    ESRB,
    @Deprecated("Deprecated by proto definition")
    PEGI,
    @Deprecated("Deprecated by proto definition")
    CERO,
    @Deprecated("Deprecated by proto definition")
    USK,
    @Deprecated("Deprecated by proto definition")
    GRAC,
    @Deprecated("Deprecated by proto definition")
    CLASS_IND,
    @Deprecated("Deprecated by proto definition")
    ACB
}
