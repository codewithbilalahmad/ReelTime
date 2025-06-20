package com.muhammad.reeltime.favourite.data.remote.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class MediaByIdRequest(
    val mediaId: Int,
    val email: String
)