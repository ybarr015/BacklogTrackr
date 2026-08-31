package com.x3vo.backlogtrackr.ui.common.components

data class BasicListUIState<T>(
    val isLoading: Boolean = false,
    val data: List<T> = emptyList(),
    val error: String? = null
)