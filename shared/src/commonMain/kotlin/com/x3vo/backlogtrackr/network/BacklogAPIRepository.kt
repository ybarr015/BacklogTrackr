package com.x3vo.backlogtrackr.network

import com.x3vo.backlogtrackr.ui.BacklogApi

class BacklogAPIRepository (private val backlogApi: BacklogApi) {
    suspend fun getAuthData(): AuthResponse {
        return try {
            backlogApi.getAuthData()
        } catch (e: Exception) {
            e.printStackTrace()
            AuthResponse("", -1, "")
        }
    }
}