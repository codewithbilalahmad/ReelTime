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
import com.muhammad.reeltime.home.presentation.HomeScreen

@Composable
fun AppNavigation(navHostController: NavHostController, isLoggedIn : Boolean) {
    val destination = if(isLoggedIn) Destinations.HomeScreen else  Destinations.IntroScreen
    NavHost(navController = navHostController, startDestination = destination){
        composable<Destinations.IntroScreen>{
            IntroScreen(onSignInClick = {
                navHostController.navigate(Destinations.LoginScreen)
            }, onSignUpClick = {
                navHostController.navigate(Destinations.RegisterScreen)
            })
        }
        composable<Destinations.LoginScreen>{
            LoginScreen(onLoginSuccess = {
                navHostController.navigate(Destinations.HomeScreen){
                    popUpTo(Destinations.LoginScreen){
                        inclusive = true
                    }
                }
            }, onSignUpClick = {
                navHostController.navigate(Destinations.RegisterScreen)
            })
        }
        composable<Destinations.RegisterScreen>{
            RegisterScreen(onSignInClick = {
                navHostController.navigate(Destinations.LoginScreen)
            }, onSuccessfulRegistration = {
                navHostController.navigate(Destinations.LoginScreen){
                    popUpTo(Destinations.RegisterScreen){
                        inclusive = true
                        saveState = true
                    }
                }
            })
        }
        composable<Destinations.HomeScreen>{
            HomeScreen(navHostController = navHostController)
        }
        composable<Destinations.CategoriesScreen>{
            CategoriesScreen(navHostController = navHostController)
        }
        composable<Destinations.CategoriesListScreen>{
            val category = it.toRoute<Destinations.CategoriesListScreen>().category
            CategoriesListScreen(navHostController = navHostController, category = category)
        }
        composable<Destinations.DetailsScreen>{
            val mediaId = it.toRoute<Destinations.DetailsScreen>().mediaId
            DetailScreen(navHostController = navHostController, mediaId = mediaId)
        }
    }
}