package com.example.carsharing.repository

import com.example.carsharing.entities.Car

interface ProfileRepository {
    suspend fun getCurrentRent(id:String): Car?
}