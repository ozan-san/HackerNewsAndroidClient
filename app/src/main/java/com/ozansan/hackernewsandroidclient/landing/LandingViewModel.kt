package com.ozansan.hackernewsandroidclient.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozansan.hackernewsandroidclient.network.usecase.GetTopStoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    private val getTopStoriesUseCase: GetTopStoriesUseCase
) : ViewModel() {

    private val _topStoryTitles = MutableStateFlow<List<String>>(listOf())
    val topStoryTitles = _topStoryTitles.asStateFlow()


    init {
        fetch()
    }

    fun fetch() {
        viewModelScope.launch {
            val topStories = getTopStoriesUseCase()
            _topStoryTitles.value = topStories.mapNotNull { it.title }
        }
    }
}