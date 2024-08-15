package com.martin.myapplication.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.martin.myapplication.R
import com.martin.myapplication.presentation.ui.theme.poppins

@Composable
fun MovieDetails() {
    MovieDetailsPage()
}

@Composable
fun MovieDetailsPage() {
    Details()
}

class Movie(
    val photo: Int,
    val name: String,
    val rating: Double,
    val year: Int,
    val duration: Int,
    val genre: String,
    val description: String,
    val reviews: MutableList<Review>,
    val cast: MutableList<String>
)

class Review(
    val name: String,
    val reviewContent: String,
    val reviewStars: Double
)

var movie = Movie(
    R.drawable.movie_2,
    "Spiderman No Way Home",
    9.5,
    2021,
    148,
    "Action",
    "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.",
    reviews = mutableListOf(
        Review(
            "Iqbal Shafiq Rozaan",
            "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government.",
            6.3
        ),
        Review(
            "Iqbal Shafiq Rozaan",
            "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government.",
            6.3
        ),
        Review(
            "Iqbal Shafiq Rozaan",
            "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government.",
            6.3
        )
    ),
    cast = mutableListOf("Tom Holland", "Zendaya", "Benedict Cumberbatch", "Brad Pitt")
)

val colorStops = arrayOf(
    0.0f to Color(37, 40, 54),
    1.0f to Color(0xFF43474E)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Details() {
    val scrollBehaviour = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        backgroundColor = Color(0xFF242A32),
        modifier = Modifier.nestedScroll(scrollBehaviour.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(horizontal = 24.dp),
                title = {
                    Text(
                        text = "Details",
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
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(R.drawable.save),
                            contentDescription = "save a movie",

                            )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF242A32),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                scrollBehavior = scrollBehaviour
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxWidth()) {
            MovieDetailsContent(innerPadding)
        }

    }


}

@Composable
fun MovieDetailsContent(innerPadding: PaddingValues) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth(),
                painter = painterResource(id = R.drawable.cover_photo),
                contentDescription = "Cover Photo"
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Box(
                    modifier = Modifier
                        .padding(bottom = 25.dp, end = 20.dp)
                        .width(56.dp)
                        .height(26.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Box(
                        modifier = Modifier
                            .height(40.dp)
                            .fillMaxHeight()
                            .background(Brush.horizontalGradient(colorStops = colorStops))
                            .clip(RoundedCornerShape(20.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                        ) {
                            Icon(
                                modifier = Modifier.height(35.dp),
                                painter = painterResource(id = R.drawable.star),
                                contentDescription = "Stars",
                                tint = Color(0xFFFF8700)
                            )
                            Box(
                                modifier = Modifier.height(35.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = movie.rating.toString(),
                                    color = Color(0xFFFF8700),
                                    fontSize = 12.sp,
                                    fontFamily = poppins,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp)
                .absoluteOffset(y = (-70).dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Image(
                modifier = Modifier
                    .height(140.dp)
                    .width(95.dp),
                painter = painterResource(movie.photo),
                contentDescription = "Movie Photo"
            )
            Text(
                text = movie.name,
                fontSize = 18.sp,
                fontFamily = poppins,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier.width(210.dp)
            )
        }

        Box(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .absoluteOffset(y = (-50).dp),
            contentAlignment = Alignment.Center,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconWithText(
                    modifier = Modifier,
                    icon = R.drawable.calendarblank,
                    text = movie.year.toString(),
                    details = true
                )
                Box(
                    modifier = Modifier.fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "|", color = Color(0xFF92929D))
                }
                IconWithText(
                    modifier = Modifier.height(20.dp),
                    icon = R.drawable.calendarblank,
                    text = movie.year.toString(),
                    details = true
                )
                Box(
                    modifier = Modifier.fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "|", color = Color(0xFF92929D))
                }
                IconWithText(
                    modifier = Modifier.height(20.dp),
                    icon = R.drawable.calendarblank,
                    text = movie.year.toString(),
                    details = true
                )
            }

        }
    }
}

@Preview
@Composable
fun AppBarPreview() {
    Details()
}