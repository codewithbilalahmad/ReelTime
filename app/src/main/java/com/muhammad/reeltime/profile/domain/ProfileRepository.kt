package com.muhammad.reeltime.profile.domain

interface ProfileRepository {
    suspend fun getName() : String
    suspend fun getEmail() : String
}