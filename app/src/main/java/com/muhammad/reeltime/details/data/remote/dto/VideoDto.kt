package com.muhammad.reeltime.details.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class VideoDto(
    val key : String,
    val site : String?=null
)
