package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable

@Serializable
data class LanguageSupportResult(val languagesupports: List<LanguageSupport>)
