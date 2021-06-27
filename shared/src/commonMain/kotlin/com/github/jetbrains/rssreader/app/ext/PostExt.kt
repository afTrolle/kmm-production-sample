package com.github.jetbrains.rssreader.app.ext

import com.github.jetbrains.rssreader.app.model.Filter
import com.github.jetbrains.rssreader.core.entity.Post


internal fun Post.containsFilter(filters: List<Filter>) = filters.all {
    if (it.enabled) {
        containsFilter(it)
    } else true
}

internal fun Post.containsFilter(filter: Filter): Boolean {
    if (!filter.enabled) return false

    val text = filter.text

    val isInTitle = title.contains(
        text,
        ignoreCase = false
    )
    val isInDescription = desc?.contains(
        text,
        ignoreCase = false
    ) ?: false

    return isInTitle || isInDescription
}