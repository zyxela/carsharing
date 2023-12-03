package com.example.carsharing.repositoryImpl

import com.example.carsharing.data.DatabaseHandler
import com.example.carsharing.entities.UserRent
import com.example.carsharing.entities.Car
import com.example.carsharing.repository.ChooseCarRepository

class ChooseCarRepositoryImpl: ChooseCarRepository {
    override suspend fun getCars(): List<Car> {
        return DatabaseHandler().getCars()
    }
    override suspend fun addRent(rent: UserRent) {
        DatabaseHandler().addRent(rent)
    }
}