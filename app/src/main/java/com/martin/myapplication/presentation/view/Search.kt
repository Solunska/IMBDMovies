package com.martin.myapplication.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.martin.myapplication.domain.model.SearchMovieModel
import com.martin.myapplication.presentation.state.SearchUiState
import com.martin.myapplication.presentation.ui.theme.poppins
import com.martin.myapplication.presentation.viewmodel.MovieDetailsViewModel
import com.martin.myapplication.presentation.viewmodel.SearchViewModel
import kotlinx.coroutines.delay

@Composable
fun SearchPage(goBack: () -> Unit) {

    val searchViewModel: SearchViewModel = hiltViewModel()
    val searchState = searchViewModel.state.collectAsState()
    val searchInput = searchViewModel.searchText.collectAsState()
    val isSearching = searchViewModel.isSearching.collectAsState()

    SearchResults(goBack, searchViewModel, searchState, searchInput, isSearching)
}

sealed class ColItem(
    val photo: Int,
    val name: String,
    val rating: Double,
    val genre: String,
    val year: Int,
    val duration: Int,
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
fun SearchResults(
    goBack: () -> Unit,
    searchViewModel: SearchViewModel,
    searchState: State<SearchUiState>,
    searchInput: State<String>,
    isSearching: State<Boolean>,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val hasResults by remember {
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
                        text = "Search",
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
            SearchBar(innerPadding, searchInput, searchViewModel, searchState)

            if (isSearching.value && searchInput.value != "") {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 30.dp),
                    color = Color(0xFF0296E5)
                )
            } else if (searchState.value.movies.isEmpty() && searchInput.value != "" && !isSearching.value) {
                NoResultsPage()
            } else {
                SearchContent(searchState.value.movies)
            }
        }
    }
}

@Composable
fun SearchBar(
    innerPadding: PaddingValues,
    searchInput: State<String>,
    searchViewModel: SearchViewModel,
    searchState: State<SearchUiState>,
) {
    TextField(
        modifier = Modifier
            .height(53.dp)
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        value = searchInput.value,
        placeholder = {
            Text(
                modifier = Modifier
                    .padding(start = 5.dp),
                text = "Search",
                color = Color(0xFF67686D),
                fontSize = 16.sp,
            )
        },
        onValueChange = {
            searchViewModel.onSearchTextChange(it)
            if (it.isEmpty()) {
                searchViewModel.onEmptyInput()
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(20.dp),
        trailingIcon = {
            IconButton(
                modifier = Modifier.padding(end = 10.dp),
                onClick = { searchViewModel.getMovieResults(searchInput.value) }) {
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
            disabledIndicatorColor = Color.Transparent,
            textColor = Color.White,
            cursorColor = Color.Gray,
        ),
    )
}

@Composable
fun SearchContent(movie: List<SearchMovieModel.Result>) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .fillMaxSize()
    ) {
        itemsIndexed(movie) { id, movie ->
            MovieCard(movie)
        }
    }
}

@Composable
fun MovieCard(movieItem: SearchMovieModel.Result) {
    val movieDetailsViewModel: MovieDetailsViewModel = hiltViewModel()
    val state = movieDetailsViewModel.state.collectAsState()
    val imageUrl = IMAGE_BASE_URL + movieItem.posterPath

    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        AsyncImage(
            modifier = Modifier
                .height(150.dp)
                .width(106.dp),
            model = imageUrl,
            contentDescription = movieItem.originalTitle,
        )

        Column(
            modifier = Modifier
                .height(150.dp)
                .padding(start = 12.dp, top = 4.dp)
                .fillMaxSize(),
        ) {
            Text(
                text = movieItem.originalTitle,
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
                state.value.movieDetails?.genres?.get(movieItem.genreIds.get(1))?.name?.let {
                    IconWithText(
                        modifier = Modifier,
                        icon = R.drawable.ticket,
                        text = it,
                        details = false

                    )
                }
                IconWithText(
                    modifier = Modifier,
                    icon = R.drawable.calendarblank,
                    text = movieItem.releaseDate,
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

@Composable
fun NoResultsPage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .size(85.dp)
                .padding(bottom = 16.dp),
            painter = painterResource(id = R.drawable.search_image),
            contentDescription = "No search results image"
        )
        Text(
            text = "We Are Sorry, We Can Not Find The Movie :(",
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
            text = "Find your movie by Type title, categories, years, etc ",
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
//fun SearchPreview() {
//    SearchPage()
//}