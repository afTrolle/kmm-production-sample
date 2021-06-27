package com.github.jetbrains.rssreader.androidApp.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.jetbrains.rssreader.app.model.Filter
import com.github.jetbrains.rssreader.core.entity.Feed

private sealed class Icons {
    class FilterIcon(val filter: Filter) : Icons()
    object Divider : Icons()
    object All : Icons()
    class FeedIcon(val feed: Feed) : Icons()
    object Edit : Icons()
}

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun MainFeedBottomBar(
    filters: List<Filter>,
    feeds: List<Feed>,
    selectedFeed: Feed?,
    onFilterClick: (Filter) -> Unit,
    onFeedClick: (Feed?) -> Unit,
    onEditClick: () -> Unit
) {
    val items = buildList {
        addAll(filters.map { Icons.FilterIcon(it) })
        add(Icons.Divider)
        add(Icons.All)
        addAll(feeds.map { Icons.FeedIcon(it) })
        add(Icons.Edit)
    }
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(items) { item ->
            when (item) {
                is Icons.FilterIcon -> FeedIcon(
                    shortName = item.filter.name,
                    isSelected = item.filter.enabled,
                    description = item.filter.text,
                    onClick = { onFilterClick(item.filter) }
                )
                is Icons.Divider -> Box(
                    modifier = Modifier
                        .size(width = 1.dp, height = 32.dp)
                        .background(color = MaterialTheme.colors.onSurface)
                )
                is Icons.All -> FeedIcon(
                    feed = null,
                    isSelected = selectedFeed == null,
                    onClick = { onFeedClick(null) }
                )
                is Icons.FeedIcon -> FeedIcon(
                    feed = item.feed,
                    isSelected = selectedFeed == item.feed,
                    onClick = { onFeedClick(item.feed) }
                )
                is Icons.Edit -> EditIcon(onClick = onEditClick)
            }
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}