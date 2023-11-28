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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.carsharing.ChooseCarViewModel
import com.example.carsharing.R
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChooseCar() {

    val viewModel: ChooseCarViewModel = getViewModel()

    val carList by viewModel.carsList.observeAsState(emptyList())
    viewModel.getCars()

    val pageState = rememberPagerState {
        carList.size
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
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card {
                        AsyncImage(
                            modifier = Modifier.size(250.dp, 200.dp),
                            model = carList[i].imageUrl,
                            placeholder = painterResource(id = R.drawable.ic_launcher_background),
                            error = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = "The delasign logo",
                            contentScale = ContentScale.Crop
                        )
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