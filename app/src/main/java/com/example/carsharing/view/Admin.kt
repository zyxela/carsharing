package com.example.carsharing.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.carsharing.AdminViewModel
import com.example.carsharing.R
import com.example.carsharing.entities.Car
import com.example.carsharing.navigation.Screen
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import org.koin.androidx.compose.getViewModel
import java.io.File

@Composable
fun Admin(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {

        val viewModel: AdminViewModel = getViewModel()

        var addDialog by remember {
            mutableStateOf(false)
        }


        var delDialog by remember {
            mutableStateOf(false)
        }



        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(){
                if (addDialog) {
                    AddCar(viewModel = viewModel)
                }

                if (delDialog) {
                    DeleteCar(viewModel = viewModel)
                }
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                onClick = {
                    addDialog = !addDialog
                },
            ) {
                Text(text = "Добавить автомобиль")
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                onClick = {
                    delDialog = !delDialog
                }) {
                Text(text = "Убрать автомобиль")
            }
        }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddCar(viewModel: AdminViewModel) {

    val context = LocalContext.current

    var mark by remember {
        mutableStateOf("")
    }
    var uri by remember {
        mutableStateOf<Uri?>(null)
    }
    var location by remember {
        mutableStateOf("")
    }

    val singlePhotoPicker =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(),
            onResult = {
                uri = it
            })


    Card {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TextField(
                value = mark,
                onValueChange = {
                    mark = it
                })

            TextField(
                value = location,
                onValueChange = {
                    location = it
                })

            AsyncImage(
                modifier = Modifier
                    .clickable {
                        singlePhotoPicker.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                    .size(250.dp, 200.dp),
                model = uri,
                placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                error = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "The delasign logo",
                contentScale = ContentScale.Crop
            )
            val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

            Button(onClick = {
                val name = (1..20)
                    .map { alphabet.random() }
                    .joinToString("")
                viewModel.addCar(
                    Car(
                        mark,
                        name,
                        location.split(" "),
                        true
                    ),
                    uri!!,
                    context,
                    name
                )
            }) {
                Text(text = "Добавить")
            }

        }

    }
}

@Composable
internal fun DeleteCar(viewModel: AdminViewModel) {

    val cars by viewModel.cars.observeAsState(emptyList())
    viewModel.getCars()
    var image by remember {
        mutableStateOf<Bitmap?>(null)
    }

    LazyRow {
        items(cars.size) { i ->

            LaunchedEffect(Unit) {
                val ref =
                    Firebase.storage.reference.child("cars/${viewModel.cars.value?.get(i)!!.imageUrl}.jpg")
                val localFile = File.createTempFile("images", "jpg")
                ref.getFile(localFile).addOnSuccessListener {
                    image = BitmapFactory.decodeFile(localFile.absolutePath)
                }.addOnFailureListener {
                    // Handle any errors
                }
            }

            Box(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 40.dp)) {
                Column {
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
                        Text(text = viewModel.cars.value?.get(i)!!.mark)
                        Button(onClick = {
                            viewModel.deleteCar(cars[i].id)
                        }) {
                            Text(text = "Убрать")
                        }
                    }


                }
            }
        }
    }
}