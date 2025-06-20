package com.muhammad.reeltime.main.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.muhammad.reeltime.auth.presentation.intro.IntroScreen
import com.muhammad.reeltime.auth.presentation.login.LoginScreen
import com.muhammad.reeltime.auth.presentation.register.RegisterScreen

@Composable
fun AppNavigation(navHostController: NavHostController, isLoggedIn : Boolean) {
    val destination = if(isLoggedIn) Destinations.MainScreen else  Destinations.IntroScreen
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
                navHostController.navigate(Destinations.MainScreen){
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
                navHostController.navigate(Destinations.MainScreen){
                    popUpTo(Destinations.RegisterScreen){
                        inclusive = true
                    }
                }
            })
        }
        composable<Destinations.MainScreen>{  }
    }
}