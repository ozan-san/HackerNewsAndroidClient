package com.ozansan.hackernewsandroidclient.network.api

import com.ozansan.hackernewsandroidclient.network.model.HNItem
import retrofit2.http.GET
import retrofit2.http.Path

interface HackerNewsApi {
    @GET("topstories.json")
    suspend fun getTopStories(): List<Long>

    @GET("item/{id}.json")
    suspend fun getItem(@Path("id") id: Long): HNItem
}
