package com.example.carsharing

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carsharing.entities.User
import com.example.carsharing.entities.Car
import com.example.carsharing.repository.ProfileRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: ProfileRepository):ViewModel() {
    val user = MutableLiveData<User>()
    val currentRent = MutableLiveData<Car>()

    fun getUser(id:String){
        viewModelScope.launch {
           user.value =  repository.getCurrentUser(id)
        }
    }

    fun getCurrentRent(userId:String){
        viewModelScope.launch {
            repository.getCurrentRent(userId, this@ProfileViewModel)
        }
    }

    fun closeRent(){
        viewModelScope.launch {
            repository.closeRent(currentRent.value!!.id)
        }
    }



}