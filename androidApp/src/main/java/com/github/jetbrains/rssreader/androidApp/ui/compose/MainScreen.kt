package com.github.jetbrains.rssreader.androidApp.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.github.jetbrains.rssreader.androidApp.Screens
import com.github.jetbrains.rssreader.app.FeedAction
import com.github.jetbrains.rssreader.app.FeedStore
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.android.launch
import com.github.terrakok.modo.forward
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    store: FeedStore,
    modo: Modo
) {
    AppTheme {
        ProvideWindowInsets {
            val state = store.observeState().collectAsState()
            SwipeRefresh(
                state = rememberSwipeRefreshState(state.value.progress),
                onRefresh = { store.dispatch(FeedAction.Refresh(true)) },
                indicatorPadding = rememberInsetsPaddingValues(
                    LocalWindowInsets.current.statusBars,
                    applyBottom = false,
                )
            ) {
                Column {
                    val coroutineScope = rememberCoroutineScope()
                    val listState = rememberLazyListState()
                    PostList(
                        modifier = Modifier.weight(1f),
                        posts = state.value.postsFeed,
                        listState = listState
                    ) { post ->
                        post.link?.let { modo.launch(Screens.Browser(it)) }
                    }
                    MainFeedBottomBar(
                        filters = state.value.filters,
                        feeds = state.value.feeds,
                        selectedFeed = state.value.selectedFeed,
                        onFeedClick = { feed ->
                            coroutineScope.launch { listState.scrollToItem(0) }
                            store.dispatch(FeedAction.SelectFeed(feed))
                        },
                        onEditClick = { modo.forward(Screens.FeedList()) },
                        onFilterClick = { store.dispatch(FeedAction.SelectFilter(it)) }
                    )
                    Spacer(
                        Modifier
                            .navigationBarsHeight()
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}