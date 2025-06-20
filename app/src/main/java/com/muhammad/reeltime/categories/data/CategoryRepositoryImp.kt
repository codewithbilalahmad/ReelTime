package com.muhammad.reeltime.categories.data

import com.muhammad.reeltime.categories.domain.CategoryRepository
import com.muhammad.reeltime.home.data.local.MediaDao
import com.muhammad.reeltime.home.data.mapper.toMedia
import com.muhammad.reeltime.home.domain.model.Media
import com.muhammad.reeltime.utils.APIConstants.genres

class CategoryRepositoryImp(
    private val mediaDao: MediaDao
) : CategoryRepository{
    override suspend fun getActionAndAdventure(): List<Media> {
        val actionAndAdventureGenres = genres.filter { genre ->
            genre.genreName in listOf(
                "Adventure",
                "Horror",
                "Action",
                "Western",
                "Thriller",
                "Crime",
                "War"
            )
        }.map { it.genreId.toString() }
        return getMediaListByGenreIds(actionAndAdventureGenres)
    }

    override suspend fun getDrama(): List<Media> {
        val dramaGenres = genres.filter { genre ->
            genre.genreName in listOf(
                "Drama",
                "Comedy",
                "Family",
                "Romance",
                "Music"
            )
        }.map { it.genreId.toString() }
        return getMediaListByGenreIds(dramaGenres)
    }

    override suspend fun getComedy(): List<Media> {
        val comedyGenres = genres.filter { genre ->
            genre.genreName in listOf(
                "Comedy",
                "Family",
                "Romance"
            )
        }.map { it.genreId.toString() }
        return getMediaListByGenreIds(comedyGenres)
    }

    override suspend fun getSciFiAndFantasy(): List<Media> {
        val sciFiAndFantasyGenres = genres.filter { genre ->
            genre.genreName in listOf(
                "Fantasy",
                "Horror",
                "Thriller",
                "Crime",
                "Documentary",
                "Science Fiction",
                "Mystery"
            )
        }.map { it.genreId.toString() }
        return getMediaListByGenreIds(sciFiAndFantasyGenres)
    }

    override suspend fun getAnimation(): List<Media> {
        val animationGenres = genres.filter { genre ->
            genre.genreName == "Animation"
        }.map { it.genreId.toString() }
        return getMediaListByGenreIds(animationGenres)
    }
    private suspend fun getMediaListByGenreIds(genreIds : List<String>) : List<Media>{
        val mediaList = mediaDao.getMediaList().map { it.toMedia() }
        return mediaList.filter { media ->
            media.genreIds.any{genreId ->
                genreId in genreIds
            }
        }.shuffled()
    }
}