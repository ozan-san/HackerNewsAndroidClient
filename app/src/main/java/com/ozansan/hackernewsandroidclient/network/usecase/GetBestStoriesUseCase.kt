package com.ozansan.hackernewsandroidclient.network.usecase

import com.ozansan.hackernewsandroidclient.network.model.HNItem
import com.ozansan.hackernewsandroidclient.network.repository.HackerNewsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetBestStoriesUseCase @Inject constructor(
    private val repository: HackerNewsRepository
) {
    suspend operator fun invoke(n: Int = 5): List<HNItem> = coroutineScope {
        val topStoryIds = repository.getBestStoryIds().take(n)
        topStoryIds.map { id ->
            async {
                repository.getItemContent(id)
            }
        }.awaitAll()
    }
}