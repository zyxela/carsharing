package com.example.carsharing

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carsharing.entities.UserRent
import com.example.carsharing.entities.Car
import com.example.carsharing.repository.ChooseCarRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch

class ChooseCarViewModel(private val repository: ChooseCarRepository) : ViewModel() {
    val carsList = MutableLiveData<List<Car>>()
    val abilityToRent = MutableLiveData(true)
    val email = MutableLiveData("")
    fun getCars() {
        viewModelScope.launch {
            carsList.value = repository.getCars()
        }
    }

    fun addRent(rent: UserRent) {
        viewModelScope.launch {
            repository.addRent(rent)
        }
    }

    fun checkRent(userId: String) {
        viewModelScope.launch {
            val db = Firebase.firestore
            db.collection("usersRent").get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        if (userId == document.data["userId"].toString()) {
                            var carId = document.data["carId"].toString()

                            db.collection("cars").get().addOnSuccessListener { res ->
                                for (doc in res) {
                                    val id = doc.id
                                    if (carId == id) {
                                        abilityToRent.value = false
                                        break
                                    }
                                    abilityToRent.value = true
                                }

                            }.addOnFailureListener { exception ->
                                Log.e("ERROR", "Error getting documents.", exception)
                            }


                        }
                    }
                }
        }
    }

    fun getUserEmail(){
        viewModelScope.launch{
            val auth = FirebaseAuth.getInstance()
            val user: FirebaseUser? = auth.currentUser

            if (user != null) {
                email.value = user.email

            }
        }
    }
}