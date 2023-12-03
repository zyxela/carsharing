package com.example.carsharing.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.carsharing.MeetViewModel
import com.example.carsharing.R
import com.example.carsharing.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.koin.androidx.compose.getViewModel

@Composable
fun Meet(navController: NavController) {

    var isSign by remember {
        mutableIntStateOf(-1)
    }

    val viewModel: MeetViewModel = getViewModel()

    val enter by viewModel.enter.observeAsState(false)
    if (enter){
        val auth = FirebaseAuth.getInstance()
        val user: FirebaseUser? = auth.currentUser

        if (user != null) {
            val email = user.email
            if (email == "a@mail.ru")
                navController.navigate(Screen.AdminScreen.route)
            else
                navController.navigate(Screen.ChooseCarScreen.route)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp, 0.dp, 12.dp, 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Card(
                shape = CutCornerShape(
                    topStartPercent = 12,
                    topEndPercent = 12,
                    bottomEndPercent = 0,
                    bottomStartPercent = 0

                ),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSign == 1) Color.White else Color.LightGray,
                )
            ) {
                Text(
                    modifier = Modifier.clickable {
                        isSign = -1
                    },
                    text = "Войти",
                    fontSize = 22.sp
                )
            }
            Card(
                shape = CutCornerShape(
                    topStartPercent = 12,
                    topEndPercent = 12,
                    bottomEndPercent = 0,
                    bottomStartPercent = 0

                ),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSign == -1) Color.White else Color.LightGray,
                )
            ) {
                Text(
                    modifier = Modifier.clickable {
                        isSign = 1
                    },
                    text = "Зарегистрироваться",
                    fontSize = 22.sp
                )
            }
        }
        Card(colors = CardDefaults.cardColors(Color.LightGray)) {
            Column(verticalArrangement = Arrangement.Center) {
                if (isSign == 1)
                    SignUp(viewModel)
                else
                    SignIn(viewModel)

            }
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SignIn(viewModel: MeetViewModel) {

    var login by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = login,
            onValueChange = {
                login = it
            },
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text(text = "Логин")
            },

            )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text(text = "Пароль")
            },
        )

        val context = LocalContext.current
        Button(onClick = {
            viewModel.auth(login, password, context)
        }) {
            Text(text = "Войти")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SignUp(viewModel: MeetViewModel) {

    var login by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }
    var uri by remember {
        mutableStateOf<Uri?>(null)
    }

    val singlePhotoPicker =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(),
            onResult = {
                uri = it
            })


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = login,
            onValueChange = {
                login = it
            },
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text(text = "Логин")
            },

            )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text(text = "Пароль")
            },
        )
        AsyncImage(
            modifier = Modifier
                .clickable {
                    singlePhotoPicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
                .size(150.dp, 100.dp),
            model = uri,
            placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
            error = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "The delasign logo",
            contentScale = ContentScale.Crop
        )

        val context = LocalContext.current


        Button(onClick = {
            viewModel.registration(login, password, context)
            viewModel.uploadPhoto(uri!!, context, login)
        }) {
            Text(text = "Зарегестрироваться")
        }
    }
}
