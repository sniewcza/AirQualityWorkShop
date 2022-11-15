package com.example.airquality.ui.stationList

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun StationListScreen() {
    val viewModel: StationListViewModel = hiltViewModel()
    val state = viewModel.state

    SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = state.isRefreshing),
        onRefresh = { viewModel.onPullToRefresh() }, modifier = Modifier.padding(bottom = 10.dp)
    ) {
        AnimatedVisibility(
            visible = !state.isRefreshing,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            LazyColumn() {
                items(state.stations) {
                    Card(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        shape = MaterialTheme.shapes.medium,
                        colors = CardDefaults.cardColors(containerColor = Color.LightGray),
                        elevation = CardDefaults.cardElevation(defaultElevation = 15.dp)

                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .weight(1f)
                            ) {
                                Text(
                                    text = it.title,
                                    style = MaterialTheme.typography.headlineMedium
                                )
                                Text(
                                    text = it.subtitle,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(text = it.label, style = MaterialTheme.typography.labelSmall)
                            }
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current).data(it.imageUrl)
                                    .build(),
                                contentDescription = "Image",
                                modifier = Modifier
                                    .size(90.dp)
                                    .padding(end = 10.dp)
                            )
                        }

                    }
                }
            }
        }
    }
}
