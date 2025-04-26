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
import com.myolwinoo.smartproperty.data.model.PropertyType
import com.myolwinoo.smartproperty.design.theme.AppDimens
import com.myolwinoo.smartproperty.design.theme.SPTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.label_amenities
import smartproperty.composeapp.generated.resources.label_hide
import smartproperty.composeapp.generated.resources.label_property_type
import smartproperty.composeapp.generated.resources.label_show_all

@Composable
fun PropertyTypeChooser(
    modifier: Modifier = Modifier,
    items: List<PropertyType>,
    onSelected: (PropertyType) -> Unit,
    selectedItem: PropertyType?
) {
    var showAll by remember { mutableStateOf(false) }
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(Res.string.label_property_type),
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
                    selected = it == selectedItem,
                    onClick = { onSelected(it) },
                    label = { Text(it.name) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun PropertyTypeChooserPreview() {
    SPTheme {
        val items = listOf(
            PropertyType("1", "Apartment"),
            PropertyType("2", "Condo"),
            PropertyType("3", "House"),
            PropertyType("4", "Room"),
        )
        val selectedItem: PropertyType = PropertyType("1", "Apartment")
        PropertyTypeChooser(
            modifier = Modifier.padding(AppDimens.Spacing.m),
            items = items,
            onSelected = { },
            selectedItem = selectedItem
        )
    }
}