package com.example.carsharing.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.carsharing.ProfileViewModel
import com.example.carsharing.navigation.Screen
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import org.koin.androidx.compose.getViewModel
import java.io.File

@Composable
fun Profile(navController: NavController) {
    val context = LocalContext.current

    val userId =
        context.getSharedPreferences("prefs", Context.MODE_PRIVATE).getString("USER_ID", "")

    val viewModel: ProfileViewModel = getViewModel()

    val user by viewModel.user.observeAsState()
    viewModel.getUser(
        userId!!
    )

    val email by viewModel.email.observeAsState()
    viewModel.getEmail()

    val currentRent by viewModel.currentRent.observeAsState()
    viewModel.getCurrentRent(userId)

    var prof by remember {
        mutableStateOf<Bitmap?>(null)
    }
    LaunchedEffect(Unit) {
        val ref =
            Firebase.storage.reference.child("profile/${email}.jpg")
        val localFile = File.createTempFile("images", "jpg")
        ref.getFile(localFile).addOnSuccessListener {
            prof = BitmapFactory.decodeFile(localFile.absolutePath)
        }.addOnFailureListener {
            // Handle any errors
        }
    }


    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 15.dp),
            shape = CircleShape
        ) {
            if (prof != null) {
                Image(
                    modifier = Modifier
                        .size(150.dp)
                        .clickable {
                            navController.navigate(Screen.ProfileScreen.route)
                        },
                    bitmap = prof!!.asImageBitmap(),
                    contentDescription = ""
                )
            }
        }
        Text(text = email ?: "loading")

        var image by remember {
            mutableStateOf<Bitmap?>(null)
        }

        // LaunchedEffect(Unit) {
        val ref =
            Firebase.storage.reference.child("cars/${currentRent?.imageUrl}.jpg")
        val localFile = File.createTempFile("images", "jpg")
        ref.getFile(localFile).addOnSuccessListener {
            image = BitmapFactory.decodeFile(localFile.absolutePath)
        }.addOnFailureListener {
            // Handle any errors
        }
        //}

        Box(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 40.dp)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Card {
                    if (image != null) {
                        Image(
                            modifier = Modifier
                                .size(250.dp, 200.dp),
                            bitmap = image!!.asImageBitmap(),
                            contentDescription = "The delasign logo",
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Text(text = currentRent?.mark ?: "")
                if (image != null) {
                    Button(onClick = {
                        viewModel.closeRent()
                        viewModel.getCurrentRent(userId)
                    }) {
                        Text(text = "Завершить аренду")
                    }
                }


            }

        }

    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),

            border = BorderStroke(3.dp, Color.Red),
            onClick = {
                navController.navigate(Screen.MeetScreen.route)
            }) {
            Text(
                text = "ВЫХОД",
                fontWeight = FontWeight(700),
                color = Color.Red
            )
        }
    }
}