package com.muhammad.reeltime.details.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class DetailsDto(
    val runtime : Int?=null,
    val tagline : String?=null
)