package com.muhammad.reeltime.auth.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val name : String = "", val email : String, val password : String
)
