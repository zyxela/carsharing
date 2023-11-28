package com.example.carsharing

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carsharing.entities.Car
import com.example.carsharing.repository.ChooseCarRepository
import kotlinx.coroutines.launch

class ChooseCarViewModel(private val repository: ChooseCarRepository):ViewModel(){
    val carsList = MutableLiveData<List<Car>>()

    fun getCars(){
        viewModelScope.launch{
            carsList.value = repository.getCars()
        }
    }
}