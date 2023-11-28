package com.example.carsharing.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.carsharing.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChooseCar() {

    val uriList = mutableListOf<String>(
        "https://polinka.top/uploads/posts/2023-05/1684989385_polinka-top-p-kartinka-mashini-na-belom-fone-vkontakte-10.png",
        "https://www.1zoom.me/big/55/69974-Nika.jpg",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSXS16QY5VJrGSGqsVdtWBPXmx1bnMD89I5Ow&usqp=CAU"
    )


    val pageState = rememberPagerState {
        uriList.size
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
                    uriList[it]
                }
            ) { i ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card {
                        AsyncImage(
                            modifier = Modifier.size(250.dp, 200.dp),
                            model = uriList[i],
                            placeholder = painterResource(id = R.drawable.ic_launcher_background),
                            error = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = "The delasign logo",
                            contentScale = ContentScale.Crop
                        )
                    }
                    Text(
                        text = "car $i",
                        fontSize = 28.sp,
                        fontWeight = FontWeight(550)
                    )


                    LazyColumn(modifier = Modifier.height(210.dp)) {
                        items(uriList.count() + 2) { lc ->
                            var check by remember {
                                mutableStateOf(false)
                            }
                            Card(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .width(250.dp)
                            ) {
                                Row(modifier = Modifier.padding(4.dp).width(250.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = SpaceBetween) {
                                    Text(text = "location $lc")

                                        CircleCheckbox(check) {
                                            check = !check
                                        }

                                    // Checkbox(checked = check, onCheckedChange = {check =!check})
                                }
                            }
                        }
                    }
                }
            }

        }
        Button(modifier = Modifier
            .width(250.dp),
            onClick = {

        }) {
            Text(text = "Выбрать")
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