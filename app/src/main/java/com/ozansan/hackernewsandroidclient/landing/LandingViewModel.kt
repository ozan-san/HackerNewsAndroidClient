package com.ozansan.hackernewsandroidclient.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozansan.hackernewsandroidclient.landing.newslist.UINewsState
import com.ozansan.hackernewsandroidclient.landing.newslist.mappers.toUINewsState
import com.ozansan.hackernewsandroidclient.network.usecase.GetTopStoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    private val getTopStoriesUseCase: GetTopStoriesUseCase
) : ViewModel() {

    private val _topStoryTitles = MutableStateFlow<List<String>>(listOf())
    val topStoryTitles = _topStoryTitles.asStateFlow()

    private val _topStories = MutableStateFlow<ImmutableList<UINewsState>>(
        listOf<UINewsState>().toImmutableList()
    )
    val topStories = _topStories.asStateFlow()

    private val _selectedTab = MutableStateFlow("TOP")
    val selectedTab = _selectedTab.asStateFlow()

    init {
        fetch()
    }

    fun fetch() {
        viewModelScope.launch {
            val topStories = getTopStoriesUseCase()
            _topStoryTitles.value = topStories.mapNotNull { it.title }
            _topStories.update { _ ->
                topStories.map {
                    it.toUINewsState()
                }.toImmutableList()
            }
        }
    }

    fun onTabSelected(tab: String) {
        _selectedTab.value = tab
    }
}