package com.example.carsharing

import android.content.Context
import android.media.Image
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carsharing.entities.Car
import com.example.carsharing.repository.AdminRepository
import kotlinx.coroutines.launch

class AdminViewModel(
    private val repository:AdminRepository
): ViewModel() {

    val cars = MutableLiveData<List<Car>>()


    fun getCars(){
        viewModelScope.launch {
            cars.value = repository.getCars()
        }
    }

    fun addCar(car: Car, uri: Uri, context: Context, name: String){
        viewModelScope.launch {
            repository.addCar(car, uri, context, name)
            getCars()
        }
    }

    fun deleteCar(carId:String){
        viewModelScope.launch {
            repository.deleteCars(carId)
            getCars()
        }
    }
}