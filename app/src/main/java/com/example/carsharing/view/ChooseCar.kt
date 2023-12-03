package com.example.carsharing.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.carsharing.ChooseCarViewModel
import com.example.carsharing.R
import com.example.carsharing.entities.UserRent
import com.example.carsharing.navigation.Screen
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import org.koin.androidx.compose.getViewModel
import java.io.File

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChooseCar(navController: NavController) {

    val viewModel: ChooseCarViewModel = getViewModel()

    val context = LocalContext.current

    val userId = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        .getString("USER_ID", "HeD4qcjnDQgFv7cQ25rzytfwzTK2")!!

    val email by viewModel.email.observeAsState()
    viewModel.getUserEmail()

    val carList by viewModel.carsList.observeAsState(emptyList())
    viewModel.getCars()

    val abilityToRent by viewModel.abilityToRent.observeAsState()
    viewModel.checkRent(userId)

    val pageState = rememberPagerState {
        carList.size
    }

    var prof by remember {
        mutableStateOf<Bitmap?>(null)
    }
    LaunchedEffect(Unit){
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.End
    ) {
        Card(
            shape = CircleShape
        ) {
            if(prof!=null) {
                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .clickable {
                            navController.navigate(Screen.ProfileScreen.route)
                        },
                    bitmap = prof!!.asImageBitmap(),
                    contentDescription = ""
                )
            }
        }
    }

    val cars by viewModel.carsList.observeAsState(emptyList())
    viewModel.getCars()
    var image by remember {
        mutableStateOf<Bitmap?>(null)
    }

    Column(
        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 40.dp)) {

            HorizontalPager(
                state = pageState,
                key = {
                    carList[it]
                }
            ) { i ->


                LaunchedEffect(Unit) {
                    val ref =
                        Firebase.storage.reference.child("cars/${cars[i].imageUrl}.jpg")
                    val localFile = File.createTempFile("images", "jpg")
                    ref.getFile(localFile).addOnSuccessListener {
                        image = BitmapFactory.decodeFile(localFile.absolutePath)
                    }.addOnFailureListener {
                        // Handle any errors
                    }
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
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
                    Text(
                        text = carList[i].mark,
                        fontSize = 28.sp,
                        fontWeight = FontWeight(550)
                    )


                    LazyColumn(modifier = Modifier.height(210.dp)) {
                        items(carList[i].location.count()) { lc ->
                            var check by remember {
                                mutableStateOf(false)
                            }
                            Card(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .width(250.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .width(250.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = SpaceBetween
                                ) {
                                    Text(text = carList[i].location[lc])

                                    CircleCheckbox(check) {
                                        check = !check
                                    }

                                }
                            }

                        }
                    }

                    Button(
                        modifier = Modifier
                        .width(250.dp),
                        onClick = {
                            viewModel.addRent(
                                UserRent(
                                    userId,
                                    carList[i].id
                                )
                            )
                            viewModel.abilityToRent.value = false
                        },
                        enabled = abilityToRent!!) {
                        Text(text = "Выбрать")
                    }
                }

            }

        }

    }


}


@Composable
fun CircleCheckbox(selected: Boolean, enabled: Boolean = true, onChecked: () -> Unit) {

    val color = MaterialTheme.colorScheme
    val imageVector = if (selected) Icons.Filled.CheckCircle else Icons.Outlined.Circle
    val tint = if (selected) color.primary.copy(alpha = 0.8f) else Color.White.copy(alpha = 0.8f)
    val background = if (selected) Color.White else Color.Transparent

    IconButton(
        onClick = { onChecked() },
        modifier = Modifier.offset(x = 4.dp, y = 4.dp),
        enabled = enabled
    ) {

        Icon(
            imageVector = imageVector, tint = tint,
            modifier = Modifier.background(background, shape = CircleShape),
            contentDescription = "checkbox"
        )
    }
}