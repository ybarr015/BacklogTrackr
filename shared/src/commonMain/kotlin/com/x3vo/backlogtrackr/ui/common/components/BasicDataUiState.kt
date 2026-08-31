package com.x3vo.backlogtrackr.ui.common.components

data class BasicDataUiState<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val error: String? = null
)