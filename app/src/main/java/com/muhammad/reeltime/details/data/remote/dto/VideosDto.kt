package com.muhammad.reeltime.details.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class VideosDto(
    val results : List<VideoDto>
)
