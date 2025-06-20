package com.muhammad.reeltime.categories.domain

import com.muhammad.reeltime.home.domain.model.Media

interface CategoryRepository {
    suspend fun getActionAndAdventure() : List<Media>
    suspend fun getDrama(): List<Media>
    suspend fun getComedy(): List<Media>
    suspend fun getSciFiAndFantasy(): List<Media>
    suspend fun getAnimation(): List<Media>
}