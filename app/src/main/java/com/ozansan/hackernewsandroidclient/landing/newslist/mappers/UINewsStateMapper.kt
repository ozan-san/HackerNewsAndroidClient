package com.ozansan.hackernewsandroidclient.landing.newslist.mappers

import com.ozansan.hackernewsandroidclient.landing.newslist.UINewsState
import com.ozansan.hackernewsandroidclient.network.model.HNItem

fun HNItem.toUINewsState() = UINewsState(
    id = id,
    deleted = deleted,
    type = type,
    by = by,
    time = time,
    text = text,
    dead = dead,
    parent = parent,
    poll = poll,
    kids = kids,
    url = url,
    score = score,
    title = title,
    parts = parts,
    descendants = descendants
)
