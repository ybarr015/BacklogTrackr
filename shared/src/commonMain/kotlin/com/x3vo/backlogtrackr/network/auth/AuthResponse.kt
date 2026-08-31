package com.x3vo.backlogtrackr.network.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val access_token: String,
    val expires_in: Int,
    val token_type: String
)