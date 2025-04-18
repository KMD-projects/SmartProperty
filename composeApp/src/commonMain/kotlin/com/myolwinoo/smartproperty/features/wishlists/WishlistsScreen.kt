@file:OptIn(ExperimentalMaterial3Api::class)

package com.myolwinoo.smartproperty.features.wishlists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.myolwinoo.smartproperty.common.LoadingOverlay
import com.myolwinoo.smartproperty.common.propertyList
import com.myolwinoo.smartproperty.data.model.Property
import com.myolwinoo.smartproperty.design.theme.AppDimens
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WishlistsScreen(modifier: Modifier = Modifier) {
    val viewModel: WishlistsViewModel = koinViewModel<WishlistsViewModel>()
    val properties = viewModel.properties.collectAsStateWithLifecycle()

    Screen(
        modifier = modifier,
        isLoading = viewModel.isLoading,
        properties = properties.value,
        onClick = {},
        onRefresh = viewModel::refresh,
        onFavoriteClick = viewModel::toggleFavorite
    )
}

@Composable
private fun Screen(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    properties: List<Property>,
    onRefresh: () -> Unit,
    onClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit
) {
    val statusBarInset = WindowInsets.statusBars.asPaddingValues()
    val pullToRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        state = pullToRefreshState,
        isRefreshing = isLoading,
        onRefresh = onRefresh
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(
                top = statusBarInset.calculateTopPadding() + AppDimens.Spacing.xl,
                bottom = 20.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            stickyHeader {
                Text(
                    modifier = Modifier.padding(
                        horizontal = AppDimens.Spacing.xl,
                        vertical = AppDimens.Spacing.m
                    ),
                    text = "Your Wishlists",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            propertyList(
                properties = properties,
                onClick = onClick,
                onFavoriteClick = onFavoriteClick
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Screen(
        isLoading = true,
        properties = listOf(),
        onRefresh = {},
        onClick = {},
        onFavoriteClick = {}
    )
}