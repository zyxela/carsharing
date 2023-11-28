package com.example.carsharing.repositoryImpl

import com.example.carsharing.data.DatabaseHandler
import com.example.carsharing.entities.Car
import com.example.carsharing.repository.ProfileRepository

class ProfileRepositoryImpl:ProfileRepository {
    override suspend fun getCurrentRent(id:String): Car? {
        return DatabaseHandler().getCurrentRent(id)
    }
}