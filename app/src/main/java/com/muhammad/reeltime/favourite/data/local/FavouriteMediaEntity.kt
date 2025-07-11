package com.muhammad.reeltime.favourite.data.local

import androidx.room.*

@Entity
data class FavoriteMediaEntity(
    @PrimaryKey val mediaId: Int,

    val isSynced: Boolean,
    val isDeletedLocally: Boolean,

    val isLiked: Boolean,
    val isBookmarked: Boolean,

    val adult: Boolean,
    val backdropPath: String,
    val genreIds: String,
    var mediaType: String,
    val originCountry: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int,
    var category: String,

    val runTime: Int,
    val tagLine: String,

    val videosIds: String,
    val similarMediaIds: String
)