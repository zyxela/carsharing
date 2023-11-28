package com.example.carsharing.repository

import com.example.carsharing.entities.Car

interface ChooseCarRepository {
    suspend fun getCars():List<Car>
}