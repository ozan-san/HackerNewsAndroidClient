package com.ozansan.hackernewsandroidclient.network.repository

import com.ozansan.hackernewsandroidclient.network.api.HackerNewsApi
import com.ozansan.hackernewsandroidclient.network.model.HNItem
import javax.inject.Inject

class HackerNewsRepositoryImpl @Inject constructor(
    private val hackerNewsApi: HackerNewsApi
) : HackerNewsRepository {
    override suspend fun getTopStoryIds(): List<Long> {
        return hackerNewsApi.getTopStories()
    }

    override suspend fun getItemContent(id: Long): HNItem {
        return hackerNewsApi.getItem(id)
    }
}