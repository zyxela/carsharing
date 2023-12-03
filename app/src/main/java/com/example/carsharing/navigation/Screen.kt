package com.example.carsharing.navigation

sealed class Screen(var route:String) {
    object ChooseCarScreen: Screen("choose_car")
    object MeetScreen: Screen("meet")
    object ProfileScreen: Screen("profile")
    object AdminScreen: Screen("admin")
}