package com.martin.myapplication.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.IconButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextField
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.martin.myapplication.R
import com.martin.myapplication.presentation.ui.theme.poppins
import androidx.navigation.NavHostController as NavHostController1

@Composable
fun Search(navController: NavHostController1) {
    SearchPage()
}

@Composable
fun SearchPage() {
    SearchResults()
}

sealed class ColItem(
    val photo: Int,
    val name: String,
    val rating: Double,
    val genre: String,
    val year: Int,
    val duration: Int
) {
    data object Movie1 : ColItem(R.drawable.movie_1, "name", 9.5, "genre", 2020, 120)
    data object Movie2 : ColItem(R.drawable.movie_1, "name", 9.5, "genre", 2020, 120)
    data object Movie3 : ColItem(R.drawable.movie_1, "name", 9.5, "genre", 2020, 120)

}

var movies: MutableList<ColItem> = mutableListOf(
    ColItem.Movie1,
    ColItem.Movie2,
    ColItem.Movie3,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResults() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        backgroundColor = Color(0xFF242A32),
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(horizontal = 24.dp),
                title = {
                    Text(
                        text = "Search",
                        fontFamily = poppins,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.8.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(R.drawable.icon_button_back),
                            contentDescription = "back button",
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(R.drawable.info_circle),
                            contentDescription = "info button",
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF242A32),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                scrollBehavior = scrollBehavior
            )
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            SearchBar(innerPadding)
            SearchContent()
        }
    }
}

@Composable
fun SearchBar(innerPadding: PaddingValues) {
    var searchInput by remember {
        mutableStateOf("")
    }

    TextField(
        modifier = Modifier
            .height(53.dp)
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        value = searchInput,
        placeholder = {
            Text(
                modifier = Modifier
                    .padding(start = 5.dp),
                text = "Search",
                color = Color(0xFF67686D),
                fontSize = 16.sp,
            )
        },
        onValueChange = { searchInput = it },
        singleLine = true,
        shape = RoundedCornerShape(20.dp),
        trailingIcon = {
            IconButton(
                modifier = Modifier.padding(end = 10.dp),
                onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(R.drawable.search_icon_only),
                    contentDescription = "sda",
                    tint = Color(0xFF67686D)
                )
            }

        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color(0xFF3A3F47),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
    )
}

@Composable
fun SearchContent() {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .fillMaxSize()
    ) {
        items(movies) { movie ->
            MovieCard(movieItem = movie)
        }
    }
}

@Composable
fun MovieCard(movieItem: ColItem) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        Image(
            modifier = Modifier
                .height(150.dp)
                .width(106.dp),
            painter = painterResource(id = movieItem.photo),
            contentDescription = "movie photo"
        )
        Column(
            modifier = Modifier
                .height(150.dp)
                .padding(start = 12.dp, top = 4.dp)
                .fillMaxSize(),
        ) {
            Text(
                text = movieItem.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                fontFamily = poppins
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 4.dp),
                verticalArrangement = Arrangement.Bottom,
            ) {
                IconWithText(
                    modifier = Modifier,
                    icon = R.drawable.star,
                    text = movieItem.rating.toString(),
                    details = false
                )
                IconWithText(
                    modifier = Modifier,
                    icon = R.drawable.ticket,
                    text = movieItem.genre,
                    details = false

                )
                IconWithText(
                    modifier = Modifier,
                    icon = R.drawable.calendarblank,
                    text = movieItem.year.toString(),
                    details = false

                )
                IconWithText(
                    modifier = Modifier,
                    icon = R.drawable.clock,
                    text = "${movieItem.duration} minutes",
                    details = false
                )
            }
        }
    }
}

@Composable
fun IconWithText(modifier: Modifier, icon: Int, text: String, details: Boolean) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .padding(top = 4.dp)
            .height(22.dp)
    ) {
        Icon(
            modifier = Modifier.height(24.dp),
            painter = painterResource(icon),
            contentDescription = "sda",
            tint = if (details) Color(0xFF92929D) else if (icon == R.drawable.star) Color(0xFFFF8700) else Color.White
        )
        Box(modifier = Modifier.height(24.dp), contentAlignment = Alignment.Center) {
            Text(
                text = text,
                fontFamily = poppins,
                color = if (details) Color(0xFF92929D) else if (icon == R.drawable.star) Color(
                    0xFFFF8700
                ) else Color.White,
                fontSize = 14.sp,
                textAlign = TextAlign.Justify,
                fontWeight = if (details) FontWeight.Medium else if (icon == R.drawable.star) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}

@Preview
@Composable
fun SearchPreview() {
    SearchPage()
}