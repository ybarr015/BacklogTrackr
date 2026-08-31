package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable

@Serializable
data class LanguageSupportResult(val languagesupports: List<LanguageSupport>)
