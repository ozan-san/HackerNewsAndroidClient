package com.ozansan.hackernewsandroidclient.landing

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ozansan.hackernewsandroidclient.components.NewsItem

import com.ozansan.hackernewsandroidclient.components.TabSelector

@Composable
fun LandingScreen(
    viewModel: LandingViewModel = hiltViewModel()
) {
    val topStoriesList by viewModel.topStories.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "HACKER_FEED",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(Modifier.height(16.dp))
        LandingScreenContent(viewModel)
        Spacer(Modifier.height(32.dp))
        LazyColumn {
            items(topStoriesList) { story ->
                NewsItem(uiNews = story)
            }
        }
    }
}

@Composable
private fun LandingScreenContent(viewModel: LandingViewModel) {
    val selectedTab by viewModel.selectedTab.collectAsState()

    TabSelector(
        options = listOf("NEW", "TOP", "BEST"),
        selectedOption = selectedTab,
        onOptionSelected = viewModel::onTabSelected
    )
}
