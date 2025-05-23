@file:OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)

package com.myolwinoo.smartproperty.features.explore

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.window.core.layout.WindowWidthSizeClass
import com.myolwinoo.smartproperty.common.PropertyList
import com.myolwinoo.smartproperty.common.propertyList
import com.myolwinoo.smartproperty.data.model.Property
import com.myolwinoo.smartproperty.design.theme.AppDimens
import com.myolwinoo.smartproperty.utils.ColumnHelper
import com.myolwinoo.smartproperty.utils.PreviewData
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.ic_edit
import smartproperty.composeapp.generated.resources.label_start_explore
import smartproperty.composeapp.generated.resources.search
import smartproperty.composeapp.generated.resources.title_create_property

@Serializable
object ExploreRoute

fun NavController.navigateExplore() {
    navigate(ExploreRoute)
}

fun NavGraphBuilder.homeScreen(
    modifier: Modifier = Modifier,
    navigateToSearch: () -> Unit,
    navigateToPropertyDetail: (String) -> Unit,
    onCreateProperty: () -> Unit
) {
    composable<ExploreRoute> {
        ExploreScreen(
            modifier = modifier,
            navigateToSearch = navigateToSearch,
            navigateToPropertyDetail = navigateToPropertyDetail,
            onCreateProperty = onCreateProperty
        )
    }
}

@Composable
fun ExploreScreen(
    modifier: Modifier = Modifier,
    navigateToSearch: () -> Unit,
    navigateToPropertyDetail: (String) -> Unit,
    onCreateProperty: () -> Unit
) {
    val viewModel: ExploreViewModel = koinViewModel<ExploreViewModel>()
    val properties = viewModel.properties.collectAsStateWithLifecycle()
    val showCreateProperty = viewModel.showCreateProperty.collectAsStateWithLifecycle()

    Screen(
        modifier = modifier,
        windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass,
        isLoading = viewModel.isLoading,
        showCreateProperty = showCreateProperty.value,
        properties = properties.value,
        onRefresh = viewModel::refresh,
        onFavoriteClick = viewModel::toggleFavorite,
        navigateToSearch = navigateToSearch,
        navigateToPropertyDetail = navigateToPropertyDetail,
        onCreateProperty = onCreateProperty
    )
}

@Composable
private fun Screen(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowWidthSizeClass,
    isLoading: Boolean,
    showCreateProperty: Boolean,
    properties: List<Property>,
    onRefresh: () -> Unit,
    onFavoriteClick: (String) -> Unit,
    navigateToSearch: () -> Unit,
    navigateToPropertyDetail: (String) -> Unit,
    onCreateProperty: () -> Unit
) {
    val statusBarInset = WindowInsets.statusBars.asPaddingValues()
    val pullToRefreshState = rememberPullToRefreshState()
    val column = ColumnHelper.calculate(windowSizeClass)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = statusBarInset.calculateTopPadding())
    ) {
        SearchBar(
            modifier = Modifier
                .padding(
                    horizontal = AppDimens.Spacing.xl,
                    vertical = 8.dp
                ),
            onSearch = navigateToSearch
        )

        PullToRefreshBox(
            state = pullToRefreshState,
            isRefreshing = isLoading,
            onRefresh = onRefresh
        ) {
            PropertyList(
                modifier = modifier
                    .fillMaxSize(),
                column = column,
                properties = properties,
                onClick = navigateToPropertyDetail,
                onFavoriteClick = onFavoriteClick
            )

            if (showCreateProperty) {
                ExtendedFloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(
                            bottom = AppDimens.Spacing.xl,
                            end = AppDimens.Spacing.xl
                        ),
                    onClick = { onCreateProperty() },
                    icon = { Icon(painterResource(Res.drawable.ic_edit), "") },
                    text = { Text(text = stringResource(Res.string.title_create_property)) },
                )
            }
        }
    }
}

@Composable
private fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: () -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = RoundedCornerShape(50),
        onClick = { onSearch() }
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(Res.drawable.search),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(Res.string.label_start_explore)
            )
        }
    }
}

@Preview
@Composable
private fun ExpandedPreview() {
    Screen(
        windowSizeClass = WindowWidthSizeClass.EXPANDED,
        isLoading = false,
        properties = PreviewData.properties,
        showCreateProperty = true,
        onRefresh = {},
        onFavoriteClick = {},
        navigateToSearch = {},
        navigateToPropertyDetail = {},
        onCreateProperty = {}
    )
}

@Preview
@Composable
private fun CompactPreview() {
    Screen(
        windowSizeClass = WindowWidthSizeClass.COMPACT,
        isLoading = false,
        properties = PreviewData.properties,
        showCreateProperty = true,
        onRefresh = {},
        onFavoriteClick = {},
        navigateToSearch = {},
        navigateToPropertyDetail = {},
        onCreateProperty = {}
    )
}