package com.muhammad.reeltime.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun NonFocusedTopBar(
    title: String = "",
    name: String = "",modifier : Modifier = Modifier,
    navHostController: NavHostController,
) {
    val background = if (title.isNotEmpty()) MaterialTheme.colorScheme.background else Color.Transparent
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(background)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        NonFocusedSearchBar(name = name, navHostController = navHostController)
        if (title.isEmpty()) {
            Text(text = title, color = MaterialTheme.colorScheme.onBackground, fontSize = 19.sp)
        }
    }
}
