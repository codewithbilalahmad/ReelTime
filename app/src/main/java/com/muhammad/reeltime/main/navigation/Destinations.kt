package com.muhammad.reeltime.main.navigation

import kotlinx.serialization.Serializable

sealed interface Destinations {
    @Serializable
    data object LoginScreen : Destinations
    @Serializable
    data object IntroScreen : Destinations
    @Serializable
    data object RegisterScreen : Destinations
    @Serializable
    data object HomeScreen : Destinations
    @Serializable
    data object TrendingScreen : Destinations
    @Serializable
    data object TvScreen : Destinations
    @Serializable
    data object MovieScreen : Destinations
    @Serializable
    data object ProfileScreen : Destinations
    @Serializable
    data class DetailsScreen(val mediaId : Int) : Destinations
    @Serializable
    data object WatchVideoScreen : Destinations
    @Serializable
    data object SimilarScreen : Destinations
    @Serializable
    data object SearchScreen : Destinations
    @Serializable
    data object FavoritesScreen : Destinations
    @Serializable
    data object LikedListScreen : Destinations
    @Serializable
    data object BookmarkedScreen : Destinations
    @Serializable
    data object CategoriesScreen : Destinations
    @Serializable
    data class CategoriesListScreen(val category : String) : Destinations
}