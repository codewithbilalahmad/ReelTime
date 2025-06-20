package com.muhammad.reeltime.favourite.data.remote.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class UpsertMediaRequest(
    val mediaRequest: MediaRequest,
    val email: String,
)
