@file:OptIn(ExperimentalMaterial3Api::class)

package com.myolwinoo.smartproperty.features.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.TextFieldValue
import com.myolwinoo.smartproperty.design.theme.AppDimens
import com.myolwinoo.smartproperty.design.theme.SPTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.label_apply
import smartproperty.composeapp.generated.resources.label_reset

@Composable
fun FilterSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    minPrice: TextFieldValue,
    onMinPriceChange: (TextFieldValue) -> Unit,
    maxPrice: TextFieldValue,
    onMaxPriceChange: (TextFieldValue) -> Unit,
    priceSorting: PriceSorting?,
    onPriceSortingChange: (PriceSorting) -> Unit,
    onDismissRequest: () -> Unit,
    onReset: () -> Unit
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .padding(
                    start = AppDimens.Spacing.xl,
                    end = AppDimens.Spacing.xl,
                    bottom = AppDimens.Spacing.xl,
                )
        ) {
            Text("Price")
            Row(
                horizontalArrangement = Arrangement.spacedBy(AppDimens.Spacing.m)
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f),
                    value = minPrice,
                    onValueChange = onMinPriceChange,
                    label = { Text("Min") }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f),
                    value = maxPrice,
                    onValueChange = onMaxPriceChange,
                    label = { Text("Max") }
                )
            }
            Spacer(Modifier.size(AppDimens.Spacing.xl))
            Text("Sort by Price")
            Row(
                horizontalArrangement = Arrangement.spacedBy(AppDimens.Spacing.m)
            ) {
                FilterChip(
                    selected = priceSorting == PriceSorting.ASC,
                    label = { Text("Ascending") },
                    onClick = { onPriceSortingChange(PriceSorting.ASC) }
                )
                FilterChip(
                    selected = priceSorting == PriceSorting.DESC,
                    label = { Text("Descending") },
                    onClick = { onPriceSortingChange(PriceSorting.DESC) }
                )
            }
            Spacer(Modifier.size(AppDimens.Spacing.l))
            Button(
                modifier = Modifier
                    .widthIn(max = AppDimens.maxWidth)
                    .fillMaxWidth(),
                onClick = { onReset() }
            ) {
                Text(
                    text = stringResource(Res.string.label_reset)
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SPTheme {
        val density = LocalDensity.current
        FilterSheet(
            sheetState = SheetState(
                initialValue = SheetValue.PartiallyExpanded,
                skipPartiallyExpanded = false,
                skipHiddenState = false,
                density = density
            ),
            minPrice = TextFieldValue(),
            onMinPriceChange = {},
            maxPrice = TextFieldValue(),
            onMaxPriceChange = {},
            priceSorting = PriceSorting.ASC,
            onPriceSortingChange = {},
            onDismissRequest = {},
            onReset = {}
        )
    }
}