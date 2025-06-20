package com.muhammad.reeltime.auth.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val name : String, val token : String
)
