package com.example.carsharing

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carsharing.entities.User
import com.example.carsharing.entities.Car
import com.example.carsharing.repository.ProfileRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: ProfileRepository):ViewModel() {
    val user = MutableLiveData<User>()
    val currentRent = MutableLiveData<Car>()
    val email = MutableLiveData("")

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

    fun getEmail(){
        viewModelScope.launch{
            val auth = FirebaseAuth.getInstance()
            val user: FirebaseUser? = auth.currentUser

            if (user != null) {
                email.value = user.email

            }
        }
    }


}