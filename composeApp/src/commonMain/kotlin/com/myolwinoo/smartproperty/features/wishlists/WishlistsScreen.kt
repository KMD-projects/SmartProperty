package com.myolwinoo.smartproperty.features.wishlists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.myolwinoo.smartproperty.common.LoadingOverlay
import com.myolwinoo.smartproperty.common.propertyList
import com.myolwinoo.smartproperty.data.model.Property
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WishlistsScreen(modifier: Modifier = Modifier) {
    val viewModel: WishlistsViewModel = koinViewModel<WishlistsViewModel>()
    val properties = viewModel.properties.collectAsStateWithLifecycle()

    Screen(
        modifier = modifier,
        isLoading = viewModel.isLoading,
        properties = properties.value
    )
}

@Composable
private fun Screen(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    properties: List<Property>
) {
    val statusBarInset = WindowInsets.statusBars.asPaddingValues()
    if (isLoading) {
        LoadingOverlay()
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(
                top = statusBarInset.calculateTopPadding(),
                bottom = 20.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            propertyList(
                properties = properties
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Screen(
        isLoading = true,
        properties = listOf()
    )
}