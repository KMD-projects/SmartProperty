package com.myolwinoo.smartproperty.features.propertyform

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.myolwinoo.smartproperty.data.model.Amenity
import com.myolwinoo.smartproperty.design.theme.AppDimens
import com.myolwinoo.smartproperty.design.theme.SPTheme
import io.ktor.client.plugins.observer.ResponseObserver
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.label_amenities
import smartproperty.composeapp.generated.resources.label_hide
import smartproperty.composeapp.generated.resources.label_show_all

@Composable
fun AmenityChooser(
    modifier: Modifier = Modifier,
    items: List<Amenity>,
    onSelected: (Amenity) -> Unit,
    selectedItems: List<Amenity>
) {
    var showAll by remember { mutableStateOf(false) }
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(Res.string.label_amenities),
                style = MaterialTheme.typography.titleMedium
            )
            TextButton(
                onClick = { showAll = !showAll }
            ) {
                Text(
                    text = stringResource(
                        if (showAll) {
                            Res.string.label_hide
                        } else {
                            Res.string.label_show_all
                        }
                    ),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(AppDimens.Spacing.m),
            maxLines = if (showAll) Int.MAX_VALUE else 2
        ) {
            items.forEach {
                FilterChip(
                    shape = RoundedCornerShape(percent = 50),
                    selected = selectedItems.contains(it),
                    onClick = { onSelected(it) },
                    label = { Text(it.name) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun AmenityChooserPreview() {
    SPTheme {
        val amenities = listOf(
            Amenity("1", "Swimming Pool"),
            Amenity("2", "Gym"),
            Amenity("3", "Parking"),
            Amenity("4", "Security"),
            Amenity("5", "Laundry"),
            Amenity("6", "WiFi"),
            Amenity("7", "AC"),
        )
        val selectedAmenities: List<Amenity> = listOf(
            Amenity("1", "Swimming Pool"),
            Amenity("2", "Gym"),
        )
        AmenityChooser(
            modifier = Modifier.padding(AppDimens.Spacing.m),
            items = amenities,
            onSelected = { },
            selectedItems = selectedAmenities
        )
    }
}