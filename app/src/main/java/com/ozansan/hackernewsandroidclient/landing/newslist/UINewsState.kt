package com.ozansan.hackernewsandroidclient.landing.newslist

data class UINewsState(
    val id: Long,
    val deleted: Boolean? = null,
    val type: String? = null,
    val by: String? = null,
    val time: Long? = null,
    val text: String? = null,
    val dead: Boolean? = null,
    val parent: Long? = null,
    val poll: Long? = null,
    val kids: List<Long>? = null,
    val url: String? = null,
    val score: Int? = null,
    val title: String? = null,
    val parts: List<Long>? = null,
    val descendants: Int? = null
)

