package com.martin.myapplication.presentation.view

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.martin.myapplication.BuildConfig.IMAGE_BASE_URL
import com.martin.myapplication.R
import com.martin.myapplication.data.remote.api.MoviesApi
import com.martin.myapplication.presentation.state.DetailsUiState
import com.martin.myapplication.presentation.ui.theme.poppins
import com.martin.myapplication.presentation.viewmodel.MovieDetailsViewModel

@Composable
fun MovieDetailsPage(goBack: () -> Unit, id: Int) {
    val movieDetailsViewModel: MovieDetailsViewModel = hiltViewModel()
    val movieDetailsState = movieDetailsViewModel.state.collectAsState()
    val isAdded by movieDetailsViewModel.isAdded.collectAsState()

    LaunchedEffect(id) {
        movieDetailsViewModel.fetchMovieDetails(id)
    }

    Details(goBack, movieDetailsState, id, movieDetailsViewModel,isAdded)
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
    val cast: MutableList<Cast>,
)

class Review(
    val name: String,
    val reviewContent: String,
    val reviewStars: Double,
)

class Cast(
    val name: String,
    val image: Int,
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
    cast = mutableListOf(
        Cast("Tom Holland", R.drawable.tom_holand),
        Cast("Tom Holland", R.drawable.tom_holand),
        Cast("Tom Holland", R.drawable.tom_holand),
    )
)


val colorStops = arrayOf(
    0.0f to Color(37, 40, 54),
    1.0f to Color(0xFF43474E)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Details(
    goBack: () -> Unit,
    state: State<DetailsUiState>,
    id: Int,
    movieDetailsViewModel: MovieDetailsViewModel,
    isAdded: Boolean,
) {


    val scrollBehaviour = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val watchlistRequest = MoviesApi.WatchlistRequest(
        media_type = "movie",
        media_id = id,
        watchlist = true
    )

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
                    IconButton(onClick = { goBack() }) {
                        Icon(
                            painter = painterResource(R.drawable.icon_button_back),
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        movieDetailsViewModel.addMovie(21456817, watchlistRequest)
                    }) {
                        Icon(
                            painter = painterResource(if (isAdded) R.drawable.saved else R.drawable.save),
                            contentDescription = "save a movie",
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF242A32),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White,
                )
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxWidth()) {
            MovieDetailsContent(state, innerPadding)
        }

    }


}

@SuppressLint("InvalidColorHexValue")
@Composable
fun MovieDetailsContent(state: State<DetailsUiState>, innerPadding: PaddingValues) {

    val imageUrl = IMAGE_BASE_URL + state.value.movieDetails?.posterPath

    var activeTab by remember {
        mutableStateOf("About")
    }

    if (state.value.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color(0xFF0296E5))
        }
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    modifier = Modifier
                        .height(240.dp)
                        .fillMaxWidth(),
                    painter = painterResource(id = R.drawable.cover1),
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
                            .padding(bottom = 30.dp, end = 32.dp)
                            .width(76.dp)
                            .height(40.dp)
                            .clip(RoundedCornerShape(16.dp)),
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
                                    .padding(horizontal = 12.dp)
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
                                        fontSize = 16.sp,
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
                AsyncImage(
                    modifier = Modifier
                        .width(120.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    model = imageUrl,
                    contentDescription = state.value.movieDetails?.title,
                )
                Text(
                    text = state.value.movieDetails?.title.toString(),
                    fontSize = 32.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    modifier = Modifier.width(210.dp),
                    lineHeight = 34.sp
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
                        text = state.value.movieDetails?.releaseDate.toString(),
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
                        icon = R.drawable.clock,
                        text = movie.duration.toString(),
                        details = true
                    )
                    Box(
                        modifier = Modifier.fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "|", color = Color(0xFF92929D))
                    }


                    state.value.movieDetails?.genres?.get(1)?.name?.let {
                        IconWithText(
                            modifier = Modifier.height(20.dp),
                            icon = R.drawable.ticket,
                            text = it,
                            details = true
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .absoluteOffset(y = (-20).dp)
                    .padding(start = 30.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Box(
                        modifier = if (activeTab == "About") Modifier
                            .background(Color(0xFF3A3F47))
                            .height(30.dp)
                        else Modifier
                    ) {
                        ClickableText(
                            modifier = Modifier
                                .background(Color(0xFF242A32))
                                .padding(bottom = 4.dp)
                                .width(110.dp),
                            onClick = {
                                activeTab = "About"
                            },
                            text = AnnotatedString("About Movie"),
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = poppins,
                                fontWeight = if (activeTab == "About") FontWeight.Bold else FontWeight.Medium,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            ),
                        )
                    }
                    Box(
                        modifier = if (activeTab == "Reviews") Modifier
                            .background(Color(0xFF3A3F47))
                            .height(30.dp)
                        else Modifier
                    ) {
                        ClickableText(
                            modifier = Modifier
                                .background(Color(0xFF242A32))
                                .width(75.dp)
                                .padding(bottom = 4.dp),
                            onClick = {
                                activeTab = "Reviews"
                            },
                            text = AnnotatedString("Reviews"),
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = poppins,
                                fontWeight = if (activeTab == "Reviews") FontWeight.Bold else FontWeight.Medium,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            ),
                        )
                    }
                    Box(
                        modifier = if (activeTab == "Cast") Modifier
                            .background(Color(0xFF3A3F47))
                            .height(30.dp) else Modifier
                    ) {
                        ClickableText(
                            modifier = Modifier
                                .background(Color(0xFF242A32))
                                .width(50.dp)
                                .padding(bottom = 4.dp),
                            onClick = {
                                activeTab = "Cast"
                            },
                            text = AnnotatedString("Cast"),
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = poppins,
                                fontWeight = if (activeTab == "Cast") FontWeight.Bold else FontWeight.Medium,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            ),
                        )
                    }
                }
            }

            if (activeTab == "About") {
                AboutContent(state.value.movieDetails?.overview)
            } else if (activeTab == "Reviews") {
                LazyColumn(
                    modifier = Modifier.padding(start = 30.dp, end = 24.dp, bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    state.value.movieReviews?.let {
                        items(it.results) { review ->
                            ReviewsContent(
                                name = review.authorDetails.name ?: "",
                                rating = review.authorDetails.rating?.toDouble() ?: 0.0,
                                review = review.content,
                                avatar = review.authorDetails.avatarPath ?: ""
                            )
                        }
                    }
                }
            } else LazyVerticalGrid(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                columns = GridCells.Adaptive(minSize = 140.dp)
            ) {
                items(movie.cast) { cast ->
                    CastContent(name = cast.name, photo = cast.image)
                }
            }
        }
    }

}

@Composable
fun AboutContent(overview: String?) {
    Text(
        modifier = Modifier.padding(start = 30.dp, end = 40.dp),
        text = overview.toString(),
        fontFamily = poppins,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.White,
        lineHeight = 18.sp
    )
}


@Composable
fun ReviewsContent(name: String, rating: Double, review: String, avatar: String) {
    val imageUrl = IMAGE_BASE_URL + avatar
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {

            if (avatar.isEmpty()) {
                Log.d("MovieCard", "Poster path is null")
                Image(
                    modifier = Modifier
                        .height(44.dp)
                        .width(44.dp),
                    painter = painterResource(id = R.drawable.profile_photo),
                    contentDescription = "no poster image",

                    )
            } else {
                AsyncImage(
                    modifier = Modifier
                        .height(44.dp)
                        .width(44.dp),
                    model = imageUrl,
                    contentDescription = name,
                )
            }
            Text(
                modifier = Modifier.width(44.dp),
                text = rating.toString(),
                textAlign = TextAlign.Center,
                color = Color(0xFF0296E5),
                fontWeight = FontWeight.Medium
            )

        }
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = if (name == "") "No Username" else name,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = poppins,
                fontSize = 14.sp
            )
            Text(
                text = review,
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontFamily = poppins,
                fontSize = 12.sp,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
fun CastContent(name: String, photo: Int) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(120.dp),
            painter = painterResource(id = photo),
            contentDescription = "Actor Image"
        )
        Text(
            modifier = Modifier.width(100.dp),
            text = name,
            textAlign = TextAlign.Center,
            fontFamily = poppins,
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )
    }
}
//
//@Preview
//@Composable
//fun AppBarPreview() {
//    Details()
//}