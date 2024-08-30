package com.martin.myapplication.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.martin.myapplication.R

sealed class BottomNavItem(val route: String, val icon: Int, val label: String) {
    object Home : BottomNavItem("home", R.drawable.home, "Home")
    object Search : BottomNavItem("search", R.drawable.search, "Search")
    object Saved : BottomNavItem("saved", R.drawable.saved, "Profile")
}

val items: List<BottomNavItem> = listOf(
    BottomNavItem.Home,
    BottomNavItem.Search,
    BottomNavItem.Saved
)

@Composable
fun MainPage() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .background(color = Color(0xFF0296E5))
            ) {
                BottomAppBar(
                    containerColor = Color(0xFF242A32),
                    modifier = Modifier.padding(top = 0.6.dp)
                ) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

                    items.forEach { item ->
                        NavigationBarItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(id = item.icon),
                                    contentDescription = stringResource(id = item.icon),
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF0296E5),
                                unselectedIconColor = Color(0xFF67686D),
                                indicatorColor = Color.Transparent
                            ),
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = BottomNavItem.Home.route,
            Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Home.route) {
                HomePage(goToDetails = { movieId ->
                    navController.navigate("movie_details/$movieId")
                })
            }
            composable(BottomNavItem.Search.route) { SearchPage(goBack = { navController.navigateUp() }) }
            composable(BottomNavItem.Saved.route) { SavedMoviesPage(goBack = { navController.navigateUp() }) }
            composable(
                "movie_details/{movieId}",
                arguments = listOf(navArgument("movieId") { type = NavType.IntType })
            ) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
                MovieDetailsPage(goBack = { navController.navigateUp() }, id = movieId)
            }
        }

    }
}

@Preview
@Composable
fun MainPagePreview() {
    MainPage()
}