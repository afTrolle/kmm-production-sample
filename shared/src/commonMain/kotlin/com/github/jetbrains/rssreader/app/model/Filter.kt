package com.github.jetbrains.rssreader.app.model

data class Filter(
    val text: String,
    val enabled: Boolean = false
) {
    val name = text.take(3)
}