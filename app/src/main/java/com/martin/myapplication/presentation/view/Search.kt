package com.martin.myapplication.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController

@Composable
fun Search(navController: NavHostController) {
    Column(
        Modifier
            .background(color = Color(0xFF242A32))
            .fillMaxSize()
    ) {
        Text(text = "Search screen")
    }
}

