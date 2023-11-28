package com.example.carsharing.navigation

sealed class Screen(var route:String) {
    object ChooseCarScreen: Screen("choose_car")
}