package com.example.carsharing.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.carsharing.ProfileViewModel
import com.example.carsharing.R
import org.koin.androidx.compose.getViewModel

@Composable
fun Profile() {
    val viewModel: ProfileViewModel = getViewModel()


    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            shape = CircleShape
        ) {
            AsyncImage(
                modifier = Modifier.size(50.dp),
                model = "https://cdn-icons-png.flaticon.com/512/3177/3177440.png",
                placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                error = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "The delasign logo",
                contentScale = ContentScale.Crop
            )
        }
    }
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth(),

            border = BorderStroke(2.dp, Color.Red),
            onClick = {
            
        }) {
            Text(text = "ВЫХОД")
        }
    }
}