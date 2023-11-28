package com.example.carsharing.data

import android.util.Log
import com.example.carsharing.entities.Car
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DatabaseHandler {
    private lateinit var db: FirebaseFirestore
    private fun connect() {
        try {
            db = Firebase.firestore
        } catch (e: Exception) {
            Log.e("DATABASE_CONNECTION_ERROR", e.message.toString())
        }
    }

    suspend fun getCars(): List<Car> = suspendCoroutine { continuation ->
        connect()
        val cars = mutableListOf<Car>()
        db.collection("cars")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    val id = document.id
                    val mark = document.data["mark"].toString()
                    val imageUrl = document.data["imageUrl"].toString()
                    val location = document.data["location"] as List<String>
                    val status = document.data["status"] as Boolean
                    val item = Car(
                        id,
                        mark,
                        imageUrl,
                        location,
                        status
                    )
                    cars.add(item)


                }
                continuation.resume(cars)
            }
            .addOnFailureListener { exception ->
                Log.e("ERROR", "Error getting documents.", exception)
                continuation.resume(cars)
            }

    }

    suspend fun getCurrentRent(carId:String): Car? = suspendCoroutine { continuation ->
        connect()

        var car: Car? = null

        db.collection("cars")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    val id = document.id
                    if (carId == id){
                        val mark = document.data["mark"].toString()
                        val imageUrl = document.data["imageUrl"].toString()
                        val location = document.data["location"] as List<String>
                        val status = document.data["status"] as Boolean
                        val item = Car(
                            id,
                            mark,
                            imageUrl,
                            location,
                            status
                        )
                        continuation.resume(item)
                    }

                }

            }
            .addOnFailureListener { exception ->
                Log.e("ERROR", "Error getting documents.", exception)
                continuation.resume(car)
            }

    }
}