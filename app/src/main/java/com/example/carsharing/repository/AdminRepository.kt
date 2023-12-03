package com.example.carsharing.repository

import android.content.Context
import android.media.Image
import android.net.Uri
import com.example.carsharing.entities.Car

interface AdminRepository {
    suspend fun getCars():List<Car>
    suspend fun deleteCars(carId:String)
    suspend fun addCar(car: Car, uri: Uri, context: Context, name: String)
}