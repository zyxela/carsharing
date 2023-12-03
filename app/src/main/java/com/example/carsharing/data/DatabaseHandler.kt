package com.example.carsharing.data

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.example.carsharing.ProfileViewModel
import com.example.carsharing.entities.Car
import com.example.carsharing.entities.User
import com.example.carsharing.entities.UserRent
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import kotlinx.coroutines.runBlocking
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
        db.collection("cars").get().addOnSuccessListener { result ->
            for (document in result) {

                val id = document.id
                val mark = document.data["mark"].toString()
                val imageUrl = document.data["imageUrl"].toString()
                val location = document.data["location"] as List<String>
                val status = document.data["status"] as Boolean
                val item = Car(
                    id, mark, imageUrl, location, status
                )
                cars.add(item)


            }
            continuation.resume(cars)
        }.addOnFailureListener { exception ->
            Log.e("ERROR", "Error getting documents.", exception)
            continuation.resume(cars)
        }

    }

    suspend fun getCurrentRent(userId: String, viewModel: ProfileViewModel) {
        connect()


        db.collection("usersRent").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (userId == document.data["userId"].toString()) {
                        var carId = document.data["carId"].toString()
                        var urid = document.id

                        db.collection("cars").get().addOnSuccessListener { result ->
                            for (doc in  result) {

                                val id = doc.id
                                if (carId == id) {
                                    val mark = doc.data["mark"].toString()
                                    val imageUrl = doc.data["imageUrl"].toString()
                                    val location = doc.data["location"] as List<String>
                                    val status = doc.data["status"] as Boolean
                                    val item = Car(
                                        urid, mark, imageUrl, location, status
                                    )
                                    viewModel.currentRent.value = item
                                }

                            }

                        }.addOnFailureListener { exception ->
                            Log.e("ERROR", "Error getting documents.", exception)
                        }


                    }
                }
            }

    }

    suspend fun getUser(id: String): User? = suspendCoroutine { continuation ->
        connect()
        db.collection("users").get().addOnSuccessListener { result ->
            for (document in result) {
                if (id == document.id) {
                    continuation.resume(
                        User(
                            document.id,
                            document.data["login"].toString(),
                            document.data["password"].toString(),
                            document.data["imageUrl"].toString(),
                            document.data["status"] as Boolean,
                        )
                    )
                }
            }

        }.addOnFailureListener { e ->
            Log.e("getUser", "${e.message}: ${e.cause}")
            continuation.resume(null)
        }
    }

    suspend fun uploadToStorage(uri: Uri, context: Context, name: String, folder:String = "cars") {
        val storage = Firebase.storage

        var storageRef = storage.reference


        var spaceRef: StorageReference = storageRef.child("${folder}/$name.jpg")


        val byteArray: ByteArray? =
            context.contentResolver.openInputStream(uri)?.use { it.readBytes() }

        byteArray?.let {

            var uploadTask = spaceRef.putBytes(byteArray)
            uploadTask
                .addOnFailureListener {
                    Log.e("uploadToStorage", "${it.cause}")

                }
        }


    }

    suspend fun addCar(car: Car, uri: Uri, context: Context, name: String) = runBlocking {
        connect()
        uploadToStorage(uri, context, name).toString()
        db.collection("cars").add(car)
    }

    suspend fun deleteCar(id: String) {
        connect()
        db.collection("cars").document(id).delete()
    }


    suspend fun registration(login: String, password: String, context: Context): Boolean =
        suspendCoroutine {
            val firebaseAuth = Firebase.auth
            firebaseAuth.createUserWithEmailAndPassword(login, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                        Toast.makeText(
                            context,
                            "Вы зарегестрировались",
                            Toast.LENGTH_SHORT,
                        ).show()

                        val userID = firebaseAuth.currentUser
                        context.getSharedPreferences("prefs", Context.MODE_PRIVATE).edit()
                            .putString("USER_ID", userID!!.uid).apply()
                        it.resume(true)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            context,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        it.resume(false)

                    }
                }
        }

    suspend fun auth(login: String, password: String, context: Context): Boolean =
        suspendCoroutine { sc ->
            val firebaseAuth = Firebase.auth
            firebaseAuth.signInWithEmailAndPassword(login, password)
                .addOnCompleteListener {
                    Toast.makeText(
                        context,
                        "Вы вошли",
                        Toast.LENGTH_SHORT,
                    ).show()

                    val userID = firebaseAuth.currentUser
                    context.getSharedPreferences("prefs", Context.MODE_PRIVATE).edit()
                        .putString("USER_ID", userID!!.uid).apply()
                    sc.resume(true)
                }


        }

    suspend fun addRent(rent: UserRent) {
        connect()
        db.collection("usersRent").add(rent)

    }

    fun closeRent(id:String){
        connect()
        db.collection("usersRent").document(id).delete()
    }

}