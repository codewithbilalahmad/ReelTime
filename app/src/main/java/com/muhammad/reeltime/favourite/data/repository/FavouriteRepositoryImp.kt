package com.muhammad.reeltime.favourite.data.repository

import com.muhammad.reeltime.favourite.data.local.FavoriteMediaDao
import com.muhammad.reeltime.favourite.data.local.FavoriteMediaEntity
import com.muhammad.reeltime.favourite.data.mapper.toFavoriteMediaEntity
import com.muhammad.reeltime.favourite.data.mapper.toMedia
import com.muhammad.reeltime.favourite.data.mapper.toMediaEntity
import com.muhammad.reeltime.favourite.data.mapper.toMediaRequest
import com.muhammad.reeltime.favourite.domain.BackendFavouriteRepository
import com.muhammad.reeltime.favourite.domain.FavouriteRepository
import com.muhammad.reeltime.home.data.local.MediaDao
import com.muhammad.reeltime.home.domain.model.Media
import com.muhammad.reeltime.utils.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

class FavouriteRepositoryImp(
    private val  backendFavoritesRepository: BackendFavouriteRepository,
    private val favouriteMediaDao : FavoriteMediaDao,
    private val mediaDao : MediaDao
) : FavouriteRepository{
    private val _favouriteUpdateEvents = Channel<Boolean>()
    override suspend fun favouriteDbUpdateEventFlow(): Flow<Boolean> {
       return _favouriteUpdateEvents.receiveAsFlow()
    }

    override suspend fun upsetFavoritesMediaItem(media: Media) {
        favouriteMediaDao.upsertFavoriteMediaItem(media.toFavoriteMediaEntity())
        syncFavoritesMedia()
        _favouriteUpdateEvents.send(true)
    }

    override suspend fun deleteFavoritesMediaItem(media: Media) {
        val favouriteEntity = media.toFavoriteMediaEntity().copy(isDeletedLocally = true)
        favouriteMediaDao.upsertFavoriteMediaItem(favouriteEntity)
        syncFavoritesMedia()
        _favouriteUpdateEvents.send(true)
    }

    override suspend fun getMediaItemById(mediaId: Int): Media? {
        favouriteMediaDao.getFavoriteMediaItemById(mediaId)?.let {
            return it.toMedia()
        }
        val response = backendFavoritesRepository.getMediaById(mediaId = mediaId)
        when(response){
            is Result.Failure ->{
                Result.Failure(response.error)
                return null
            }
            is Result.Success -> {
                val media =response.data
                favouriteMediaDao.upsertFavoriteMediaItem(media.toFavoriteMediaEntity())
                mediaDao.upsertMediaItem(media.toMediaEntity())
                return favouriteMediaDao.getFavoriteMediaItemById(mediaId)?.toMedia()
            }
        }
    }

    override suspend fun getLikedMediaList(): List<Media> {
        val likedMediaList = favouriteMediaDao.getLikedMediaList()
        if(likedMediaList.isNotEmpty()){
            return likedMediaList.map { it.toMedia() }
        }
        val response =  backendFavoritesRepository.getLikedMediaList()
        when(response){
            is Result.Failure -> {
                Result.Failure(response.error)
                return emptyList()
            }
            is Result.Success -> {
                val media = response.data
                favouriteMediaDao.upsertFavoriteMediaList(media.map { it.toFavoriteMediaEntity() })
                mediaDao.upsertMediaList(media.map { it.toMediaEntity() })
                return favouriteMediaDao.getLikedMediaList().map { it.toMedia() }
            }
        }
    }

    override suspend fun getBookmarkedMediaList(): List<Media> {
        val bookmarkedMediaList = favouriteMediaDao.getBookmarkedList()
        if(bookmarkedMediaList.isNotEmpty()){
            return bookmarkedMediaList.map { it.toMedia() }
        }
        val response =  backendFavoritesRepository.getBookmarkMediaList()
        when(response){
            is Result.Failure -> {
                Result.Failure(response.error)
                return emptyList()
            }
            is Result.Success -> {
                val media = response.data
                favouriteMediaDao.upsertFavoriteMediaList(media.map { it.toFavoriteMediaEntity() })
                mediaDao.upsertMediaList(media.map { it.toMediaEntity() })
                return favouriteMediaDao.getBookmarkedList().map { it.toMedia() }
            }
        }
    }
    private suspend fun syncFavoritesMedia(){
        val favouriteMedias = favouriteMediaDao.getAllFavoriteMediaItem()
        favouriteMedias.forEach { favourite ->
            if(favourite.isDeletedLocally){
                syncLocallyDeleteMedia(favourite)
            } else if(!favourite.isSynced){
                syncUnSyncedMedia(favourite)
            }
        }
    }
    private suspend fun syncLocallyDeleteMedia(
        favouriteMediaEntity : FavoriteMediaEntity
    ){
        val wasDeleted = backendFavoritesRepository.deleteMediaFromUser(favouriteMediaEntity.mediaId)
        if(wasDeleted is Result.Success){
            favouriteMediaDao.deleteFavoriteMediaItem(favouriteMediaEntity)

        }
    }
    private suspend fun syncUnSyncedMedia(
        favouriteMediaEntity : FavoriteMediaEntity
    ){
        val wasSynced = backendFavoritesRepository.upsertMediaToUser(
            favouriteMediaEntity.toMediaRequest()
        )
        if(wasSynced  is Result.Success){
            favouriteMediaDao.upsertFavoriteMediaItem(favouriteMediaEntity.copy(isSynced = true))
        }
    }
    override suspend fun clearMainDb() {
        favouriteMediaDao.deleteAllFavoriteMediaItems()
    }
}