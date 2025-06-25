package com.muhammad.reeltime.categories.presentation.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.muhammad.reeltime.R
import com.muhammad.reeltime.categories.presentation.CategoriesState
import com.muhammad.reeltime.categories.presentation.CategoriesViewModel
import com.muhammad.reeltime.home.domain.model.Media
import com.muhammad.reeltime.main.navigation.Destinations
import com.muhammad.reeltime.ui.components.GradientBackground
import com.muhammad.reeltime.utils.APIConstants.IMAGE_BASE_URL
import com.muhammad.reeltime.utils.Constants
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoriesScreen(
    navHostController: NavHostController,
    viewModel: CategoriesViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    CategoriesScreenContent(navHostController = navHostController, state = state)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreenContent(navHostController: NavHostController, state: CategoriesState) {
    GradientBackground(hasTopbar = true){
        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.categories),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (state.actionAndAdventureList.isNotEmpty()) {
                    CategoryImage(
                        category = Constants.actionAndAdventureList,
                        media = state.actionAndAdventureList[0],
                        navHostController = navHostController
                    )
                    Spacer(Modifier.height(8.dp))
                }
                if (state.dramaList.isNotEmpty()) {
                    CategoryImage(
                        category = Constants.dramaList,
                        media = state.dramaList[0],
                        navHostController = navHostController
                    )
                    Spacer(Modifier.height(8.dp))
                }
                if (state.comedyList.isNotEmpty()) {
                    CategoryImage(
                        category = Constants.comedyList,
                        media = state.comedyList[0],
                        navHostController = navHostController
                    )
                    Spacer(Modifier.height(8.dp))
                }
                if (state.sciFiAndFantasyList.isNotEmpty()) {
                    CategoryImage(
                        category = Constants.sciFiAndFantasyList,
                        media = state.sciFiAndFantasyList[0],
                        navHostController = navHostController
                    )
                    Spacer(Modifier.height(8.dp))
                }
                if (state.animationList.isNotEmpty()) {
                    CategoryImage(
                        category = Constants.animationList,
                        media = state.animationList[0],
                        navHostController = navHostController
                    )
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun CategoryImage(category: String, media: Media, navHostController: NavHostController) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                MaterialTheme.colorScheme.surfaceVariant
            )
            .clickable {
                navHostController.navigate(Destinations.CategoriesListScreen(category))
            }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context).data("${IMAGE_BASE_URL}${media.backdropPath}")
                .size(
                    Size.ORIGINAL
                ).build(),
            contentDescription = category,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Text(
            text = category,
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic, shadow = Shadow(
                    color = Color.Black, offset = Offset(x = 0.5f, y = 10.0f), blurRadius = 3f
                )
            )
        )
    }
}