package com.muhammad.reeltime.favourite.data.mapper

import com.muhammad.reeltime.favourite.data.local.FavoriteMediaEntity
import com.muhammad.reeltime.favourite.data.remote.dto.request.MediaRequest
import com.muhammad.reeltime.favourite.data.remote.dto.response.MediaResponse
import com.muhammad.reeltime.home.data.local.MediaEntity
import com.muhammad.reeltime.home.domain.model.Media

fun FavoriteMediaEntity.toMedia(): Media {
    return Media(
        mediaId = mediaId,

        isLiked = isLiked,
        isBookmarked = isBookmarked,

        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        genreIds = try {
            genreIds.split(",").map { it }
        } catch (e: Exception) {
            emptyList()
        },
        adult = adult,
        mediaType = mediaType,
        originCountry = try {
            originCountry.split(",").map { it }
        } catch (e: Exception) {
            emptyList()
        },
        originalTitle = originalTitle,
        category = category,

        runTime = runTime,
        tagLine = tagLine,

        videosIds = try {
            videosIds.split(",").map { it }
        } catch (e: Exception) {
            emptyList()
        },
        similarMediaIds = try {
            similarMediaIds.split(",").map { it.toInt() }
        } catch (e: Exception) {
            emptyList()
        }
    )
}

fun MediaResponse.toMedia(): Media {
    return Media(
        mediaId = mediaId,

        isLiked = isLiked,
        isBookmarked = isBookmarked,

        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        genreIds = try {
            genreIds.split(",").map { it }
        } catch (e: Exception) {
            emptyList()
        },
        adult = adult,
        mediaType = mediaType,
        originCountry = try {
            originCountry.split(",").map { it }
        } catch (e: Exception) {
            emptyList()
        },
        originalTitle = originalTitle,
        category = category,

        runTime = runTime,
        tagLine = tagLine,

        videosIds = try {
            videosIds.split(",").map { it }
        } catch (e: Exception) {
            emptyList()
        },
        similarMediaIds = try {
            similarMediaIds.split(",").map { it.toInt() }
        } catch (e: Exception) {
            emptyList()
        }
    )
}

fun MediaResponse.toMediaEntity(): MediaEntity {
    return MediaEntity(
        mediaId = mediaId,

        isLiked = isLiked,
        isBookmarked = isBookmarked,

        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        genreIds = genreIds,
        adult = adult,
        mediaType = mediaType,
        originCountry = originCountry,
        originalTitle = originalTitle,
        category = category,

        runTime = runTime,
        tagLine = tagLine,

        videosIds = videosIds,
        similarMediaIds = similarMediaIds
    )
}

fun Media.toFavoriteMediaEntity(): FavoriteMediaEntity {
    return FavoriteMediaEntity(
        mediaId = mediaId,

        isSynced = false,
        isDeletedLocally = false,

        isLiked = isLiked,
        isBookmarked = isBookmarked,

        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        genreIds = try {
            genreIds.joinToString(",")
        } catch (e: Exception) {
            ""
        },
        adult = adult,
        mediaType = mediaType,
        originCountry = try {
            originCountry.joinToString(",")
        } catch (e: Exception) {
            ""
        },
        originalTitle = originalTitle,
        category = category,

        runTime = runTime,
        tagLine = tagLine,

        videosIds = try {
            videosIds.joinToString(",")
        } catch (e: Exception) {
            ""
        },
        similarMediaIds = try {
            similarMediaIds.joinToString(",")
        } catch (e: Exception) {
            ""
        }
    )
}



fun MediaResponse.toFavoriteMediaEntity(): FavoriteMediaEntity {
    return FavoriteMediaEntity(
        mediaId = mediaId,

        isSynced = false,
        isDeletedLocally = false,

        isLiked = isLiked,
        isBookmarked = isBookmarked,

        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        genreIds = genreIds,
        adult = adult,
        mediaType = mediaType,
        originCountry = originCountry,
        originalTitle = originalTitle,
        category = category,

        runTime = runTime,
        tagLine = tagLine,

        videosIds = videosIds,
        similarMediaIds = similarMediaIds
    )
}

fun FavoriteMediaEntity.toMediaRequest(): MediaRequest {
    return MediaRequest(
        mediaId = mediaId,

        isLiked = isLiked,
        isBookmarked = isBookmarked,

        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        genreIds = genreIds,
        adult = adult,
        mediaType = mediaType,
        originCountry = originCountry,
        originalTitle = originalTitle,
        category = category,

        runTime = runTime,
        tagLine = tagLine,

        videosIds = videosIds,
        similarMediaIds = similarMediaIds
    )
}