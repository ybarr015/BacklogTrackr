package com.x3vo.backlogtrackr.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.x3vo.backlogtrackr.auth.AuthRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    val authToken: StateFlow<String?> = authRepository.authToken
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun authenticate(token: String) {
        viewModelScope.launch {
            authRepository.saveAuthToken(token)
        }
    }

    fun clearAuth() {
        viewModelScope.launch {
            authRepository.clearAuthToken()
        }
    }
}
