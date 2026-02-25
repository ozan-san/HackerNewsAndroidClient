package com.ozansan.hackernewsandroidclient.network.repository

import com.ozansan.hackernewsandroidclient.network.model.HNItem

interface HackerNewsRepository {
    suspend fun getTopStoryIds(): List<Long>
    suspend fun getItemContent(id: Long): HNItem
}