package com.martin.myapplication.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.martin.myapplication.R
import com.martin.myapplication.presentation.ui.theme.montserrat
import com.martin.myapplication.presentation.ui.theme.poppins


@Composable
fun Home(navController: NavController, modifier: Modifier) {
    HomePage(Modifier.padding(top = 42.dp))
}

@Composable
fun HomePage(modifier: Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF242A32))
            .padding(start = 24.dp)
    ) {
        TopRatedMovies()
        MovieCategories()
    }

}

sealed class RowItem(val photo: Int, val placement: Int, val category: String) {
    data object Movie1 : RowItem(R.drawable.movie_1, 1, "Now playing")
    data object Movie2 : RowItem(R.drawable.movie_2, 2, "Upcoming")
    data object Movie3 : RowItem(R.drawable.movie_2, 3, "Top rated")
}

var topMovies: MutableList<RowItem> = mutableListOf(
    RowItem.Movie1,
    RowItem.Movie2,
    RowItem.Movie3,
    RowItem.Movie1,
    RowItem.Movie2,
    RowItem.Movie3,
)

@Composable
fun TopRatedMovies() {
    Column(Modifier.background(color = Color(0xFF242A32))) {
        Text(
            modifier = Modifier.padding(top = 42.dp, end = 26.dp),
            text = "What do you want to watch?",
            color = Color.White,
            fontSize = 20.sp,
            fontFamily = poppins,
            fontWeight = FontWeight.Bold,
        )
        Box {
            LazyRow(horizontalArrangement = Arrangement.spacedBy((-20).dp)) {
                items(topMovies) { movie ->
                    ShowImage(movieItem = movie)
                }

            }
        }
    }
}

@Composable
fun ShowImage(movieItem: RowItem) {
    Image(
        painter = painterResource(id = movieItem.photo),
        contentDescription = "Movie poster",
        modifier = Modifier
            .padding(top = 24.dp)
            .padding(start = 12.dp)
            .size(height = 210.dp, width = 144.61.dp)
    )
    OutlinedText(movieItem.placement.toString())
}

@Composable
fun OutlinedText(placement: String) {
    Box(modifier = Modifier.absoluteOffset(x = (-150).dp, y = 150.dp)) {
        Text(
            text = placement,
            style = fill,
        )
        Text(
            text = placement,
            style = outline,
        )
    }
}

val fill: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = montserrat,
            fontSize = 96.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF242A32),
        )
    }

val outline: TextStyle
    @Composable
    get() {
        return fill.copy(
            color = Color(0xFF0296E5),
            drawStyle = Stroke(
                miter = 10f,
                width = 2f,
                join = StrokeJoin.Miter,
            )
        )

    }


var categories: MutableList<String> = mutableListOf(
    "Now playing",
    "Upcoming",
    "Top rated",
    "Popular"
)

@Composable
fun MovieCategories() {
    LazyRow(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(top = 32.dp, end = 24.dp)
            .fillMaxWidth(),
    ) {
        items(categories) { category ->
            ClickableText(
                onClick = {},
                text = AnnotatedString(category),
                style = TextStyle(
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    fontSize = 14.sp
                ),
            )
        }
    }
    LazyVerticalGrid(
        horizontalArrangement = Arrangement.SpaceBetween,
        columns = GridCells.Adaptive(minSize = 85.dp),
        modifier = Modifier.padding(end = 24.dp)
    ) {
        items(topMovies) { movie ->
                Image(
                    painter = painterResource(id = movie.photo),
                    contentDescription = "Movie poster",
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .size(height = 145.dp, width = 100.dp)
                )


        }

    }
}

@Preview
@Composable
fun HomePreview() {
    HomePage(Modifier)
}