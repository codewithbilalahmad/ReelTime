package com.muhammad.reeltime.home.domain.usecase

import com.muhammad.reeltime.utils.APIConstants.genres

object GenreIdsToString {
    fun genreIdsToString(genreIds : List<String>) : String{
        return genreIds.joinToString(" - ") { id ->
            genres.find { genre ->
                genre.genreId.toString() == id
            }?.genreName.toString()
        }
    }
}