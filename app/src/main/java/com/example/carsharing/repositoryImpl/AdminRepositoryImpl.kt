package com.example.carsharing.repositoryImpl

import android.content.Context
import android.media.Image
import android.net.Uri
import com.example.carsharing.data.DatabaseHandler
import com.example.carsharing.entities.Car
import com.example.carsharing.repository.AdminRepository

class AdminRepositoryImpl:AdminRepository {
    override suspend fun getCars(): List<Car> {
       return DatabaseHandler().getCars()
    }

    override suspend fun deleteCars(carId: String) {
        DatabaseHandler().deleteCar(carId)
    }

    override suspend fun addCar(car: Car, uri: Uri, context: Context, name: String) {
        DatabaseHandler().addCar(car, uri, context, name)
    }


}