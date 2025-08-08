package com.devsant.maisvida.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devsant.maisvida.ui.screens.SignInScreen
import com.devsant.maisvida.ui.screens.SignUpScreen


@Composable
fun AppNavigation(modifier: Modifier = Modifier){
    val navController  = rememberNavController()

    NavHost(
        navController  = navController,
        startDestination = "sign_up"
    ){
        composable("sign_up"){
            SignUpScreen(navController)
        }
        composable("sign_in"){
            SignInScreen(navController)
        }
    }
}