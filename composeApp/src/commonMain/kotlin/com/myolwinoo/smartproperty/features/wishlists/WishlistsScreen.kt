@file:OptIn(ExperimentalMaterial3Api::class)

package com.myolwinoo.smartproperty.features.wishlists

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowWidthSizeClass
import com.myolwinoo.smartproperty.common.EmptyView
import com.myolwinoo.smartproperty.common.LoadingOverlay
import com.myolwinoo.smartproperty.common.PropertyList
import com.myolwinoo.smartproperty.common.propertyList
import com.myolwinoo.smartproperty.data.model.Property
import com.myolwinoo.smartproperty.design.theme.AppDimens
import com.myolwinoo.smartproperty.utils.ColumnHelper
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WishlistsScreen(modifier: Modifier = Modifier) {
    val viewModel: WishlistsViewModel = koinViewModel<WishlistsViewModel>()
    val properties = viewModel.properties.collectAsStateWithLifecycle()

    Screen(
        modifier = modifier,
        windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass,
        isLoading = viewModel.isLoading,
        properties = properties.value,
        onClick = {},
        onRefresh = viewModel::refresh,
        onFavoriteClick = viewModel::toggleFavorite
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Screen(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowWidthSizeClass,
    isLoading: Boolean,
    properties: List<Property>,
    onRefresh: () -> Unit,
    onClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit
) {
    val statusBarInset = WindowInsets.statusBars.asPaddingValues()
    val pullToRefreshState = rememberPullToRefreshState()

    val column = ColumnHelper.calculate(windowSizeClass)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = statusBarInset.calculateTopPadding())
    ) {
        Text(
            modifier = Modifier.padding(
                horizontal = AppDimens.Spacing.xl,
                vertical = 20.dp
            ),
            text = "Your Wishlists",
            style = MaterialTheme.typography.headlineSmall
        )

        PullToRefreshBox(
            state = pullToRefreshState,
            isRefreshing = isLoading,
            onRefresh = onRefresh
        ) {
            PropertyList(
                modifier = Modifier
                    .fillMaxSize(),
                column = column,
                properties = properties,
                onClick = onClick,
                onFavoriteClick = onFavoriteClick
            )

            if (properties.isEmpty()) {
                EmptyView(
                    text = "No data in wishlist."
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Screen(
        isLoading = true,
        windowSizeClass = WindowWidthSizeClass.COMPACT,
        properties = listOf(),
        onRefresh = {},
        onClick = {},
        onFavoriteClick = {}
    )
}