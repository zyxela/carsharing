package com.example.carsharing.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun ScreenGraph(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.ChooseCarScreen.route ){

    }
}