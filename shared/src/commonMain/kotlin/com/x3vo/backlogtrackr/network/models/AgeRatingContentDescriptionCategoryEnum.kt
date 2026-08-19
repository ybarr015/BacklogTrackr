package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable

@Serializable
enum class AgeRatingContentDescriptionCategoryEnum {
    @Deprecated("Deprecated by proto definition")
    AGERATINGCONTENTDESCRIPTION_CATEGORY_NULL,
    @Deprecated("Deprecated by proto definition")
    ESRB_ALCOHOL_REFERENCE,
    @Deprecated("Deprecated by proto definition")
    ESRB_ANIMATED_BLOOD,
    @Deprecated("Deprecated by proto definition")
    ESRB_BLOOD,
    @Deprecated("Deprecated by proto definition")
    ESRB_BLOOD_AND_GORE,
    @Deprecated("Deprecated by proto definition")
    ESRB_CARTOON_VIOLENCE,
    @Deprecated("Deprecated by proto definition")
    ESRB_COMIC_MISCHIEF,
    @Deprecated("Deprecated by proto definition")
    ESRB_CRUDE_HUMOR,
    @Deprecated("Deprecated by proto definition")
    ESRB_DRUG_REFERENCE,
    @Deprecated("Deprecated by proto definition")
    ESRB_FANTASY_VIOLENCE,
    @Deprecated("Deprecated by proto definition")
    ESRB_INTENSE_VIOLENCE,
    @Deprecated("Deprecated by proto definition")
    ESRB_LANGUAGE,
    @Deprecated("Deprecated by proto definition")
    ESRB_LYRICS,
    @Deprecated("Deprecated by proto definition")
    ESRB_MATURE_HUMOR,
    @Deprecated("Deprecated by proto definition")
    ESRB_NUDITY,
    @Deprecated("Deprecated by proto definition")
    ESRB_PARTIAL_NUDITY,
    @Deprecated("Deprecated by proto definition")
    ESRB_REAL_GAMBLING,
    @Deprecated("Deprecated by proto definition")
    ESRB_SEXUAL_CONTENT,
    @Deprecated("Deprecated by proto definition")
    ESRB_SEXUAL_THEMES,
    @Deprecated("Deprecated by proto definition")
    ESRB_SEXUAL_VIOLENCE,
    @Deprecated("Deprecated by proto definition")
    ESRB_SIMULATED_GAMBLING,
    @Deprecated("Deprecated by proto definition")
    ESRB_STRONG_LANGUAGE,
    @Deprecated("Deprecated by proto definition")
    ESRB_STRONG_LYRICS,
    @Deprecated("Deprecated by proto definition")
    ESRB_STRONG_SEXUAL_CONTENT,
    @Deprecated("Deprecated by proto definition")
    ESRB_SUGGESTIVE_THEMES,
    @Deprecated("Deprecated by proto definition")
    ESRB_TOBACCO_REFERENCE,
    @Deprecated("Deprecated by proto definition")
    ESRB_USE_OF_ALCOHOL,
    @Deprecated("Deprecated by proto definition")
    ESRB_USE_OF_DRUGS,
    @Deprecated("Deprecated by proto definition")
    ESRB_USE_OF_TOBACCO,
    @Deprecated("Deprecated by proto definition")
    ESRB_VIOLENCE,
    @Deprecated("Deprecated by proto definition")
    ESRB_VIOLENT_REFERENCES,
    @Deprecated("Deprecated by proto definition")
    ESRB_ANIMATED_VIOLENCE,
    @Deprecated("Deprecated by proto definition")
    ESRB_MILD_LANGUAGE,
    @Deprecated("Deprecated by proto definition")
    ESRB_MILD_VIOLENCE,
    @Deprecated("Deprecated by proto definition")
    ESRB_USE_OF_DRUGS_AND_ALCOHOL,
    @Deprecated("Deprecated by proto definition")
    ESRB_DRUG_AND_ALCOHOL_REFERENCE,
    @Deprecated("Deprecated by proto definition")
    ESRB_MILD_SUGGESTIVE_THEMES,
    @Deprecated("Deprecated by proto definition")
    ESRB_MILD_CARTOON_VIOLENCE,
    @Deprecated("Deprecated by proto definition")
    ESRB_MILD_BLOOD,
    @Deprecated("Deprecated by proto definition")
    ESRB_REALISTIC_BLOOD_AND_GORE,
    @Deprecated("Deprecated by proto definition")
    ESRB_REALISTIC_VIOLENCE,
    @Deprecated("Deprecated by proto definition")
    ESRB_ALCOHOL_AND_TOBACCO_REFERENCE,
    @Deprecated("Deprecated by proto definition")
    ESRB_MATURE_SEXUAL_THEMES,
    @Deprecated("Deprecated by proto definition")
    ESRB_MILD_ANIMATED_VIOLENCE,
    @Deprecated("Deprecated by proto definition")
    ESRB_MILD_SEXUAL_THEMES,
    @Deprecated("Deprecated by proto definition")
    ESRB_USE_OF_ALCOHOL_AND_TOBACCO,
    @Deprecated("Deprecated by proto definition")
    ESRB_ANIMATED_BLOOD_AND_GORE,
    @Deprecated("Deprecated by proto definition")
    ESRB_MILD_FANTASY_VIOLENCE,
    @Deprecated("Deprecated by proto definition")
    ESRB_MILD_LYRICS,
    @Deprecated("Deprecated by proto definition")
    ESRB_REALISTIC_BLOOD,
    @Deprecated("Deprecated by proto definition")
    PEGI_VIOLENCE,
    @Deprecated("Deprecated by proto definition")
    PEGI_SEX,
    @Deprecated("Deprecated by proto definition")
    PEGI_DRUGS,
    @Deprecated("Deprecated by proto definition")
    PEGI_FEAR,
    @Deprecated("Deprecated by proto definition")
    PEGI_DISCRIMINATION,
    @Deprecated("Deprecated by proto definition")
    PEGI_BAD_LANGUAGE,
    @Deprecated("Deprecated by proto definition")
    PEGI_GAMBLING,
    @Deprecated("Deprecated by proto definition")
    PEGI_ONLINE_GAMEPLAY,
    @Deprecated("Deprecated by proto definition")
    PEGI_IN_GAME_PURCHASES,
    @Deprecated("Deprecated by proto definition")
    CERO_LOVE,
    @Deprecated("Deprecated by proto definition")
    CERO_SEXUAL_CONTENT,
    @Deprecated("Deprecated by proto definition")
    CERO_VIOLENCE,
    @Deprecated("Deprecated by proto definition")
    CERO_HORROR,
    @Deprecated("Deprecated by proto definition")
    CERO_DRINKING_SMOKING,
    @Deprecated("Deprecated by proto definition")
    CERO_GAMBLING,
    @Deprecated("Deprecated by proto definition")
    CERO_CRIME,
    @Deprecated("Deprecated by proto definition")
    CERO_CONTROLLED_SUBSTANCES,
    @Deprecated("Deprecated by proto definition")
    CERO_LANGUAGES_AND_OTHERS,
    @Deprecated("Deprecated by proto definition")
    GRAC_SEXUALITY,
    @Deprecated("Deprecated by proto definition")
    GRAC_VIOLENCE,
    @Deprecated("Deprecated by proto definition")
    GRAC_FEAR_HORROR_THREATENING,
    @Deprecated("Deprecated by proto definition")
    GRAC_LANGUAGE,
    @Deprecated("Deprecated by proto definition")
    GRAC_ALCOHOL_TOBACCO_DRUG,
    @Deprecated("Deprecated by proto definition")
    GRAC_CRIME_ANTI_SOCIAL,
    @Deprecated("Deprecated by proto definition")
    GRAC_GAMBLING,
    @Deprecated("Deprecated by proto definition")
    CLASS_IND_VIOLENCIA,
    @Deprecated("Deprecated by proto definition")
    CLASS_IND_VIOLENCIA_EXTREMA,
    @Deprecated("Deprecated by proto definition")
    CLASS_IND_CONTEUDO_SEXUAL,
    @Deprecated("Deprecated by proto definition")
    CLASS_IND_NUDEZ,
    @Deprecated("Deprecated by proto definition")
    CLASS_IND_SEXO,
    @Deprecated("Deprecated by proto definition")
    CLASS_IND_SEXO_EXPLICITO,
    @Deprecated("Deprecated by proto definition")
    CLASS_IND_DROGAS,
    @Deprecated("Deprecated by proto definition")
    CLASS_IND_DROGAS_LICITAS,
    @Deprecated("Deprecated by proto definition")
    CLASS_IND_DROGAS_ILICITAS,
    @Deprecated("Deprecated by proto definition")
    CLASS_IND_LINGUAGEM_IMPROPRIA,
    @Deprecated("Deprecated by proto definition")
    CLASS_IND_ATOS_CRIMINOSOS
}
