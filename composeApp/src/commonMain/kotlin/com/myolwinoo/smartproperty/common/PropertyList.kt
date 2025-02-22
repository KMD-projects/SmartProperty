@file:OptIn(ExperimentalLayoutApi::class)

package com.myolwinoo.smartproperty.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.myolwinoo.smartproperty.utils.PreviewData
import com.myolwinoo.smartproperty.data.model.Property
import com.myolwinoo.smartproperty.design.theme.SPTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.favorite
import smartproperty.composeapp.generated.resources.favorite_filled
import smartproperty.composeapp.generated.resources.location_on

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
            .padding(horizontal = 20.dp)
            .clickable { onClick(property.id) },
        shape = RoundedCornerShape(12.dp),
    ) {
        Column {
            // Property Image
            AsyncImage(
                model = property.images.firstOrNull(),
                contentDescription = "Property Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )

            // Property Details
            Column(
                modifier = Modifier
                    .padding(
                        vertical = 8.dp,
                        horizontal = 12.dp
                    )
            ) {
                Row {
                    Text(
                        text = property.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .weight(1f)
                            .alignByBaseline()
                    )
                    IconButton(
                        onClick = { onFavoriteClick(property.id) },
                        modifier = Modifier
                            .size(32.dp)
                            .alignByBaseline()
                    ) {
                        Icon(
                            painter = painterResource(
                                if (property.isFavorite) Res.drawable.favorite_filled else Res.drawable.favorite
                            ),
                            contentDescription = "Favorite",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(
                        text = "${property.price.toInt()} MMK",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier
                            .alignByBaseline()
                    )
                    Text(
                        text = "/month",
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