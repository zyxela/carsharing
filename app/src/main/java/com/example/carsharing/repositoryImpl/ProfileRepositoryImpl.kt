package com.example.carsharing.repositoryImpl

import com.example.carsharing.ProfileViewModel
import com.example.carsharing.data.DatabaseHandler
import com.example.carsharing.entities.User
import com.example.carsharing.repository.ProfileRepository

class ProfileRepositoryImpl:ProfileRepository {
    override suspend fun getCurrentRent(id:String, viewModel:ProfileViewModel) {
         DatabaseHandler().getCurrentRent(id, viewModel)
    }

    override suspend fun getCurrentUser(id: String): User? {
        return DatabaseHandler().getUser(id)
    }

    override suspend fun closeRent(id: String) {
        DatabaseHandler().closeRent(id)
    }


}