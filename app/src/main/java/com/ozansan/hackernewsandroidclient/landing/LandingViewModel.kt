package com.ozansan.hackernewsandroidclient.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozansan.hackernewsandroidclient.landing.newslist.HackerNewsTab
import com.ozansan.hackernewsandroidclient.landing.newslist.UINewsState
import com.ozansan.hackernewsandroidclient.landing.newslist.mappers.toUINewsState
import com.ozansan.hackernewsandroidclient.network.usecase.GetBestStoriesUseCase
import com.ozansan.hackernewsandroidclient.network.usecase.GetNewStoriesUseCase
import com.ozansan.hackernewsandroidclient.network.usecase.GetTopStoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    private val getTopStoriesUseCase: GetTopStoriesUseCase,
    private val getNewStoriesUseCase: GetNewStoriesUseCase,
    private val getBestStoriesUseCase: GetBestStoriesUseCase
) : ViewModel() {

    private val _topStoryTitles = MutableStateFlow<List<String>>(listOf())
    val topStoryTitles = _topStoryTitles.asStateFlow()

    private val _topStories = MutableStateFlow<ImmutableList<UINewsState>>(
        listOf<UINewsState>().toImmutableList()
    )
    val topStories = _topStories.asStateFlow()

    private val _selectedTab = MutableStateFlow(HackerNewsTab.TOP)
    val selectedTab = _selectedTab.asStateFlow()

    private var fetchJob: Job? = null

    init {
        fetch()
    }

    fun fetch() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            val stories = when (_selectedTab.value) {
                HackerNewsTab.TOP -> getTopStoriesUseCase(10)
                HackerNewsTab.NEW -> getNewStoriesUseCase(10)
                HackerNewsTab.BEST -> getBestStoriesUseCase(10)
            }
            
            _topStoryTitles.value = stories.mapNotNull { it.title }
            _topStories.update { _ ->
                stories.map {
                    it.toUINewsState()
                }.toImmutableList()
            }
        }
    }

    fun onTabSelected(tab: HackerNewsTab) {
        if (_selectedTab.value != tab) {
            _selectedTab.value = tab
            fetch()
        }
    }
}
