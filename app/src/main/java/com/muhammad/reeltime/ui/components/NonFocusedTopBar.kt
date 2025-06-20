package com.muhammad.reeltime.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun NonFocusedTopBar(
    title: String = "",
    name: String = "",
    navHostController: NavHostController,
    toolbarOffsetHeightPx: Int,
) {
    val height = if (title.isNotEmpty()) 120.dp else 74.dp
    val background = if (title.isNotEmpty()) MaterialTheme.colorScheme.background else Color.Transparent
    Box(modifier = Modifier.height(height).offset {
        IntOffset(x = 0, y = toolbarOffsetHeightPx)
    }) {
        Column(
            modifier = Modifier.fillMaxSize().background(background).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            NonFocusedSearchBar(name = name, navHostController = navHostController)
            if (title.isEmpty()) {
                Text(text = title, color = MaterialTheme.colorScheme.onBackground, fontSize = 19.sp)
            }
        }
    }
}
