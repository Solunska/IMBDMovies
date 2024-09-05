package com.martin.myapplication.presentation.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.martin.myapplication.BuildConfig.IMAGE_BASE_URL
import com.martin.myapplication.R
import com.martin.myapplication.data.remote.dto.Genre
import com.martin.myapplication.domain.model.WatchListMoviesModel
import com.martin.myapplication.presentation.ui.theme.poppins
import com.martin.myapplication.presentation.viewmodel.MovieDetailsViewModel
import com.martin.myapplication.presentation.viewmodel.WatchListViewModel

@Composable
fun SavedMoviesPage(goBack: () -> Unit) {
    val watchListViewModel: WatchListViewModel = hiltViewModel()
    val watchListState = watchListViewModel.state.collectAsState()
    val watchListMovies = watchListState.value.movies
    val genres = watchListState.value.genres

    println("Watch List movies $watchListMovies")

    LaunchedEffect(21456817) {
        watchListViewModel.fetchMoviesFromWatchList(21456817)
    }

    SavedMovies(goBack, watchListMovies, genres, watchListViewModel)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedMovies(
    goBack: () -> Unit,
    watchListMovies: List<WatchListMoviesModel.Result>,
    genres: List<Genre>,
    watchListViewModel: WatchListViewModel,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val hasMoviesInWatchList by remember {
        mutableStateOf(true)
    }

    Scaffold(
        backgroundColor = Color(0xFF242A32),
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(horizontal = 24.dp),
                title = {
                    Text(
                        text = "Watch list",
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
                            contentDescription = "back button",
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
        }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if (hasMoviesInWatchList)
                SavedContent(watchListMovies, genres, watchListViewModel)
            else
                EmptyWatchList()
        }
    }
}

@Composable
fun SavedContent(
    watchListMovies: List<WatchListMoviesModel.Result>,
    genres: List<Genre>,
    watchListViewModel: WatchListViewModel,
) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .fillMaxSize()
    ) {
        itemsIndexed(watchListMovies) { id, movie ->
            SavedMovieCard(movieItem = movie, genres, watchListViewModel)
        }
    }
}


@Composable
fun SavedMovieCard(
    movieItem: WatchListMoviesModel.Result,
    genres: List<Genre>,
    watchListViewModel: WatchListViewModel,
) {
    val movieDetailsViewModel: MovieDetailsViewModel = hiltViewModel()
    val state = movieDetailsViewModel.state.collectAsState()
    val imageUrl = IMAGE_BASE_URL + movieItem.posterPath

    val genreName = watchListViewModel.getGenreNameById(movieItem.genreIds, genres)

    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        if (movieItem.posterPath.isEmpty()) {
            Log.d("MovieCard", "Poster path is null")
            Image(
                modifier = Modifier
                    .height(150.dp)
                    .width(106.dp),
                painter = painterResource(id = R.drawable.poster_placeholder),
                contentDescription = "no poster image",

                )
        } else {
            Log.d("MovieCard", "Poster Path: ${movieItem.posterPath}")
            AsyncImage(
                modifier = Modifier
                    .width(106.dp)
                    .clip(RoundedCornerShape(20.dp)),
                model = imageUrl,
                contentDescription = movieItem.title,
            )
        }

        Column(
            modifier = Modifier
                .height(150.dp)
                .padding(start = 12.dp, top = 4.dp)
                .fillMaxSize(),
        ) {
            Text(
                text = movieItem.title,
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
                    text = 9.5.toString(),
                    details = false
                )
                IconWithText(
                    modifier = Modifier,
                    icon = R.drawable.ticket,
                    text = genreName,
                    details = false

                )
                IconWithText(
                    modifier = Modifier,
                    icon = R.drawable.calendarblank,
                    text = movieItem.releaseDate.take(4),
                    details = false

                )
                IconWithText(
                    modifier = Modifier,
                    icon = R.drawable.clock,
                    text = "120 minutes",
                    details = false
                )
            }
        }
    }
}


@Composable
fun EmptyWatchList() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .size(85.dp)
                .padding(bottom = 16.dp),
            painter = painterResource(id = R.drawable.empty_watch_list),
            contentDescription = "Empty Watch List Image"
        )
        Text(
            text = "There Is No Movie Yet!",
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White,
            textAlign = TextAlign.Center,
            lineHeight = 25.6.sp,
            letterSpacing = 0.12.sp,
            fontFamily = poppins,
            modifier = Modifier
                .width(188.dp)
                .padding(bottom = 8.dp),
        )
        Text(
            text = "Find your movie by Type title, categories, years, etc",
            fontFamily = poppins,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF92929D),
            modifier = Modifier.width(200.dp),
            textAlign = TextAlign.Center
        )
    }
}


//@Preview
//@Composable
//fun SavedMoviesPagePreview() {
//    SavedMoviesPage()
//}