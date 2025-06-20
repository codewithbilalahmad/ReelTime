package com.muhammad.reeltime.home.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class MediaListDto(
    val page: Int? = null,
    val results: List<MediaDto>? = null
)