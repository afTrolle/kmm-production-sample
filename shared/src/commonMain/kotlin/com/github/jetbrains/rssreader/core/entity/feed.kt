package com.github.jetbrains.rssreader.core.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Feed(
    @SerialName("title") val title: String,
    @SerialName("link") val link: String,
    @SerialName("description") val desc: String,
    @SerialName("imageUrl") val imageUrl: String?,
    @SerialName("posts") val posts: List<Post>,
    @SerialName("sourceUrl") val sourceUrl: String,
    @SerialName("isDefault") val isDefault: Boolean
) {
    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        other == null -> false
        other::class != Feed::class -> false
        other is Feed && sourceUrl != other.sourceUrl -> false
        else -> true
    }

    override fun hashCode(): Int = sourceUrl.hashCode()
}

@Serializable
data class Post(
    @SerialName("title") val title: String,
    @SerialName("link") val link: String?,
    @SerialName("description") val desc: String?,
    @SerialName("imageUrl") val imageUrl: String?,
    @SerialName("date") val date: Long
)