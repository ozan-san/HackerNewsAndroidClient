package com.ozansan.hackernewsandroidclient.landing

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LandingScreen(viewModel: LandingViewModel = viewModel()) {
    val topStoryTitles by viewModel.topStoryTitles.collectAsStateWithLifecycle()
    LazyColumn {
        items(topStoryTitles) { title ->
            Text(text = title, modifier = Modifier.padding(16.dp))
        }
    }
}