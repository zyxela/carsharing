package com.example.carsharing.repository

import com.example.carsharing.ProfileViewModel
import com.example.carsharing.entities.User

interface ProfileRepository {
    suspend fun getCurrentRent(id:String, viewModel: ProfileViewModel)
    suspend fun getCurrentUser(id:String): User?
    suspend fun closeRent(id: String)

}