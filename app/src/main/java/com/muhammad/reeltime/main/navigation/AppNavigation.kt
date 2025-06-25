package com.muhammad.reeltime.main.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.muhammad.reeltime.auth.presentation.intro.IntroScreen
import com.muhammad.reeltime.auth.presentation.login.LoginScreen
import com.muhammad.reeltime.auth.presentation.register.RegisterScreen
import com.muhammad.reeltime.categories.presentation.category.CategoriesScreen
import com.muhammad.reeltime.categories.presentation.category_list.CategoriesListScreen
import com.muhammad.reeltime.details.presentation.detail.DetailScreen
import com.muhammad.reeltime.details.presentation.detail.DetailViewModel
import com.muhammad.reeltime.details.presentation.similarList.SimilarScreen
import com.muhammad.reeltime.details.presentation.watchVideo.WatchVideoScreen
import com.muhammad.reeltime.favourite.presentation.favourite.FavouriteScreen
import com.muhammad.reeltime.favourite.presentation.favourite_list.FavouriteListScreen
import com.muhammad.reeltime.home.presentation.HomeScreen
import com.muhammad.reeltime.home.presentation.HomeViewModel
import com.muhammad.reeltime.home.presentation.movies.MovieScreen
import com.muhammad.reeltime.home.presentation.trending.TrendingScreen
import com.muhammad.reeltime.home.presentation.tv.TvScreen
import com.muhammad.reeltime.profile.presentation.ProfileScreen
import com.muhammad.reeltime.search.presentation.SearchScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(navHostController: NavHostController, isLoggedIn: Boolean) {
    val detailViewModel = koinViewModel<DetailViewModel>()
    val  homeViewModel = koinViewModel<HomeViewModel>()
    val destination = if (isLoggedIn) Destinations.HomeScreen else Destinations.IntroScreen
    NavHost(navController = navHostController, startDestination = destination) {
        composable<Destinations.IntroScreen> {
            IntroScreen(onSignInClick = {
                navHostController.navigate(Destinations.LoginScreen)
            }, onSignUpClick = {
                navHostController.navigate(Destinations.RegisterScreen)
            })
        }
        composable<Destinations.LoginScreen> {
            LoginScreen(onLoginSuccess = {
                navHostController.navigate(Destinations.HomeScreen) {
                    popUpTo(Destinations.LoginScreen) {
                        inclusive = true
                    }
                }
            }, onSignUpClick = {
                navHostController.navigate(Destinations.RegisterScreen)
            })
        }
        composable<Destinations.RegisterScreen> {
            RegisterScreen(onSignInClick = {
                navHostController.navigate(Destinations.LoginScreen)
            }, onSuccessfulRegistration = {
                navHostController.navigate(Destinations.LoginScreen) {
                    popUpTo(Destinations.RegisterScreen) {
                        inclusive = true
                        saveState = true
                    }
                }
            })
        }
        composable<Destinations.HomeScreen> {
            HomeScreen(navHostController = navHostController, viewModel = homeViewModel)
        }
        composable<Destinations.CategoriesScreen> {
            CategoriesScreen(navHostController = navHostController)
        }
        composable<Destinations.CategoriesListScreen> {
            val category = it.toRoute<Destinations.CategoriesListScreen>().category
            CategoriesListScreen(navHostController = navHostController, category = category)
        }
        composable<Destinations.DetailsScreen> {
            val mediaId = it.toRoute<Destinations.DetailsScreen>().mediaId
            DetailScreen(navHostController = navHostController, mediaId = mediaId, viewModel = detailViewModel)
        }
        composable<Destinations.FavoritesScreen> {
            FavouriteScreen(navHostController = navHostController)
        }
        composable<Destinations.LikedListScreen> {
            FavouriteListScreen(
                navHostController = navHostController,
                destination = Destinations.LikedListScreen
            )
        }
        composable<Destinations.BookmarkedScreen> {
            FavouriteListScreen(
                navHostController = navHostController,
                destination = Destinations.BookmarkedScreen
            )
        }
        composable<Destinations.SearchScreen>{
            SearchScreen(navHostController = navHostController)
        }
        composable<Destinations.SimilarScreen>{
            SimilarScreen(navHostController = navHostController, viewModel = detailViewModel)
        }
        composable <Destinations.WatchVideoScreen>{
            WatchVideoScreen(viewModel = detailViewModel)
        }
        composable<Destinations.ProfileScreen>{
            ProfileScreen(onLogout = {
                navHostController.navigate(Destinations.IntroScreen){
                    popUpTo(Destinations.ProfileScreen){
                        inclusive = true
                    }
                }
            })
        }
        composable<Destinations.TrendingScreen>{
            TrendingScreen(navHostController = navHostController, viewModel = homeViewModel)
        }
        composable<Destinations.TvScreen>{
            TvScreen(navHostController = navHostController, viewModel = homeViewModel)
        }
        composable<Destinations.MovieScreen>{
            MovieScreen(navHostController = navHostController, viewModel = homeViewModel)
        }
    }
}