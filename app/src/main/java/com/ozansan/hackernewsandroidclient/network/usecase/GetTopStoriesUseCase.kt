package com.ozansan.hackernewsandroidclient.network.usecase

import com.ozansan.hackernewsandroidclient.network.model.HNItem
import com.ozansan.hackernewsandroidclient.network.repository.HackerNewsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetTopStoriesUseCase @Inject constructor(
    private val repository: HackerNewsRepository
) {
    // Retrieve the N=5 top story IDs first (N variable, default 5)
    // Go on to retrieve their HNItem content and return them
    suspend operator fun invoke(n: Int = 5): List<HNItem> = coroutineScope {
        val topStoryIds = repository.getTopStoryIds().take(n)
        topStoryIds.map { id ->
            async {
                repository.getItemContent(id)
            }
        }.awaitAll()
    }
}