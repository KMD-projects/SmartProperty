@file:OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)

package com.myolwinoo.smartproperty.features.explore

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.myolwinoo.smartproperty.common.LoadingOverlay
import com.myolwinoo.smartproperty.common.propertyList
import com.myolwinoo.smartproperty.data.model.Property
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.label_register
import smartproperty.composeapp.generated.resources.label_start_explore
import smartproperty.composeapp.generated.resources.search

@Serializable
object ExploreRoute

fun NavController.navigateExplore() {
    navigate(ExploreRoute)
}

fun NavGraphBuilder.homeScreen(
    modifier: Modifier = Modifier,
    navigateToSearch: () -> Unit
) {
    composable<ExploreRoute> {
        ExploreScreen(
            modifier = modifier,
            navigateToSearch = navigateToSearch
        )
    }
}

@Composable
fun ExploreScreen(
    modifier: Modifier = Modifier,
    navigateToSearch: () -> Unit
) {
    val viewModel: ExploreViewModel = koinViewModel<ExploreViewModel>()
    val properties = viewModel.properties.collectAsStateWithLifecycle()

    Screen(
        modifier = modifier,
        isLoading = viewModel.isLoading,
        properties = properties.value,
        onClick = {},
        onRefresh = viewModel::refresh,
        onFavoriteClick = viewModel::toggleFavorite,
        navigateToSearch = navigateToSearch
    )
}

@Composable
private fun Screen(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    properties: List<Property>,
    onRefresh: () -> Unit,
    onClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit,
    navigateToSearch: () -> Unit
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
                .padding(top = statusBarInset.calculateTopPadding())
                .fillMaxSize(),
            contentPadding = PaddingValues(
                bottom = 20.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            stickyHeader {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    shape = RoundedCornerShape(50),
                    onClick = { navigateToSearch() }
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
        isLoading = false,
        properties = listOf(),
        onClick = {},
        onRefresh = {},
        onFavoriteClick = {},
        navigateToSearch = {}
    )
}