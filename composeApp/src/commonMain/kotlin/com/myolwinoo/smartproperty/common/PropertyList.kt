@file:OptIn(ExperimentalLayoutApi::class)

package com.myolwinoo.smartproperty.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.myolwinoo.smartproperty.data.model.Property
import com.myolwinoo.smartproperty.design.theme.AppDimens
import com.myolwinoo.smartproperty.design.theme.SPTheme
import com.myolwinoo.smartproperty.utils.PreviewData
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.currency
import smartproperty.composeapp.generated.resources.favorite
import smartproperty.composeapp.generated.resources.favorite_filled
import smartproperty.composeapp.generated.resources.ic_star
import smartproperty.composeapp.generated.resources.location_on
import smartproperty.composeapp.generated.resources.month
import smartproperty.composeapp.generated.resources.placeholder

@Preview
@Composable
private fun PropertyListOneColPreview() {
    SPTheme {
        PropertyList(
            column = 1,
            properties = PreviewData.properties,
            onClick = {},
            onFavoriteClick = {}
        )
    }
}

@Preview
@Composable
private fun PropertyListTwoColPreview() {
    SPTheme {
        PropertyList(
            column = 2,
            properties = PreviewData.properties,
            onClick = {},
            onFavoriteClick = {}
        )
    }
}

@Composable
fun PropertyList(
    modifier: Modifier = Modifier,
    column: Int,
    properties: List<Property>,
    onClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(
            start = AppDimens.Spacing.xl,
            end = AppDimens.Spacing.xl,
            bottom = 20.dp,
        ),
        verticalItemSpacing = 12.dp,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        columns = StaggeredGridCells.Fixed(column),
    ) {
        propertyList(
            properties = properties,
            onClick = onClick,
            onFavoriteClick = onFavoriteClick
        )
    }
}

fun LazyStaggeredGridScope.propertyList(
    properties: List<Property>,
    onClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit
) {
    items(
        items = properties,
        key = { it.id }
    ) {
        PropertyItem(
            property = it,
            onClick = onClick,
            onFavoriteClick = onFavoriteClick
        )
    }
}

fun LazyListScope.propertyList(
    properties: List<Property>,
    onClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit
) {
    items(
        items = properties,
        key = { it.id }
    ) {
        PropertyItem(
            property = it,
            onClick = onClick,
            onFavoriteClick = onFavoriteClick
        )
    }
}

@Composable
fun PropertyItem(
    property: Property,
    onClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(property.id) },
        shape = RoundedCornerShape(12.dp),
    ) {
        Column {

            Box {
                // Property Image
                AsyncImage(
                    model = property.firstImage,
                    placeholder = painterResource(Res.drawable.placeholder),
                    error = painterResource(Res.drawable.placeholder),
                    contentDescription = "Property Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(AppDimens.Spacing.m)
                ) {
                    IconButton(
                        onClick = { onFavoriteClick(property.id) },
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background, CircleShape)
                            .size(36.dp)
                    ) {
                        Icon(
                            painter = painterResource(
                                if (property.isFavorite) {
                                    Res.drawable.favorite_filled
                                } else {
                                    Res.drawable.favorite
                                }
                            ),
                            contentDescription = "Favorite",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(AppDimens.Spacing.m)
                        .background(MaterialTheme.colorScheme.background, RoundedCornerShape(50))
                        .padding(start = 8.dp, end = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier,
                        painter = painterResource(Res.drawable.ic_star),
                        contentDescription = "Rating",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = property.avgRating.toString(),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(start = 2.dp)
                    )
                }
            }

            // Property Details
            Column(
                modifier = Modifier
                    .padding(
                        vertical = 8.dp,
                        horizontal = 12.dp
                    )
            ) {
                Text(
                    text = property.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(
                        text = "${property.price} ${stringResource(Res.string.currency)}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier
                            .alignByBaseline()
                    )
                    Text(
                        text = "/${stringResource(Res.string.month)}",
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier
                            .alignByBaseline()
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.location_on),
                        contentDescription = "Location"
                    )
                    Text(
                        text = property.location,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Amenities List (Show first 3)
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    property.amenities.take(3).forEach { amenity ->
                        SuggestionChip(
                            shape = RoundedCornerShape(percent = 50),
                            onClick = {},
                            label = { Text(text = amenity) },
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PropertyItemPreview() {
    SPTheme {
        PropertyItem(
            property = PreviewData.properties.first(),
            onClick = {},
            onFavoriteClick = {}
        )
    }
}