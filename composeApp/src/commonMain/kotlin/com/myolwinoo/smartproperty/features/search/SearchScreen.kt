@file:OptIn(ExperimentalMaterial3Api::class)

package com.myolwinoo.smartproperty.features.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil3.compose.AsyncImage
import com.myolwinoo.smartproperty.common.propertyList
import com.myolwinoo.smartproperty.data.model.Property
import com.myolwinoo.smartproperty.design.theme.AppDimens
import com.myolwinoo.smartproperty.design.theme.SPTheme
import com.myolwinoo.smartproperty.utils.PreviewData
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.currency
import smartproperty.composeapp.generated.resources.ic_back
import smartproperty.composeapp.generated.resources.ic_filter
import smartproperty.composeapp.generated.resources.ic_filter_filled
import smartproperty.composeapp.generated.resources.label_search
import smartproperty.composeapp.generated.resources.month

@Serializable
object SearchRoute

fun NavController.navigateSearch() {
    navigate(SearchRoute)
}

fun NavGraphBuilder.searchScreen(
    onBack: () -> Unit,
    navigateToPropertyDetail: (String) -> Unit
) {
    composable<SearchRoute> {
        val viewModel: SearchViewModel = koinViewModel()
        val searchResult = viewModel.searchResult.collectAsStateWithLifecycle()
        val hasFiltered = viewModel.hasFiltered.collectAsStateWithLifecycle()
        var showFilterSheet by remember { mutableStateOf(false) }

        if (showFilterSheet) {
            val sheetState = rememberModalBottomSheetState()
            FilterSheet(
                sheetState = sheetState,
                minPrice = viewModel.minPrice,
                onMinPriceChange = viewModel::onMinPriceChange,
                maxPrice = viewModel.maxPrice,
                onMaxPriceChange = viewModel::onMaxPriceChange,
                priceSorting = viewModel.priceSorting,
                onPriceSortingChange = viewModel::onPriceSortingChange,
                onDismissRequest = { showFilterSheet = false },
                onReset = {
                    showFilterSheet = false
                    viewModel.resetFilter()
                }
            )
        }

        SearchScreen(
            onBack = onBack,
            searchResult = searchResult.value,
            query = viewModel.query,
            onQueryChange = viewModel::onQueryChange,
            filtered = hasFiltered.value,
            onFilterClick = {
                showFilterSheet = true
            },
            navigateToPropertyDetail = navigateToPropertyDetail
        )
    }
}

@Composable
private fun SearchScreen(
    onBack: () -> Unit,
    searchResult: List<Property>,
    query: TextFieldValue,
    onQueryChange: (TextFieldValue) -> Unit,
    filtered: Boolean,
    onFilterClick: () -> Unit,
    navigateToPropertyDetail: (String) -> Unit
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(AppDimens.Spacing.m),
                modifier = Modifier
                    .padding(
                        start = AppDimens.Spacing.m,
                        end = AppDimens.Spacing.xl
                    )
            ) {
                IconButton(
                    modifier = Modifier.size(36.dp),
                    onClick = onBack
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_back),
                        contentDescription = null
                    )
                }
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = AppDimens.maxWidth),
                    value = query,
                    onValueChange = onQueryChange,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    maxLines = 1,
                    label = { Text(stringResource(Res.string.label_search)) },
                    trailingIcon = {
                        IconButton(onClick = {
                            onFilterClick()
                        }) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(
                                    if (filtered) {
                                        Res.drawable.ic_filter_filled
                                    } else {
                                        Res.drawable.ic_filter
                                    }
                                ),
                                contentDescription = null,
                                tint = if (filtered) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    LocalContentColor.current
                                }
                            )
                        }
                    }
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(AppDimens.Spacing.m),
                contentPadding = PaddingValues(
                    vertical = AppDimens.Spacing.l
                )
            ) {
                items(
                    items = searchResult,
                    key = { it.id }
                ) {
                    SearchResultItem(
                        property = it,
                        onClick = navigateToPropertyDetail
                    )
                }
            }
        }
    }
}

@Composable
fun SearchResultItem(
    property: Property,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clickable { onClick(property.id) },
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
        ) {
            // Property Image
            AsyncImage(
                model = property.images.firstOrNull(),
                contentDescription = "Property Image",
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.CenterVertically),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Property Details
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = property.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${property.price} ${stringResource(Res.string.currency)} /${stringResource(Res.string.month)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = property.location,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SPTheme {
        SearchScreen(
            onBack = {},
            searchResult = PreviewData.properties,
            query = TextFieldValue(),
            onQueryChange = {},
            filtered = true,
            onFilterClick = {},
            navigateToPropertyDetail = {}
        )
    }
}