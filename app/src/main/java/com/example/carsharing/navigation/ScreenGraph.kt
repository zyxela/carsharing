package com.example.carsharing.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.carsharing.view.Admin
import com.example.carsharing.view.ChooseCar
import com.example.carsharing.view.Meet
import com.example.carsharing.view.Profile

@Composable
fun ScreenGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MeetScreen.route) {
        composable(Screen.ChooseCarScreen.route) {
            ChooseCar(navController)
        }
        composable(Screen.MeetScreen.route){
            Meet(navController = navController)
        }
        composable(Screen.ProfileScreen.route){
            Profile(navController = navController)
        }
        composable(Screen.AdminScreen.route){
            Admin(navController = navController)
        }
    }
}