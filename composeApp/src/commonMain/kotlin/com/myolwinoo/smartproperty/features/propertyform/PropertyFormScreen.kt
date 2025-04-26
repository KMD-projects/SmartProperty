@file:OptIn(ExperimentalMaterial3Api::class)

package com.myolwinoo.smartproperty.features.propertyform

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.myolwinoo.smartproperty.data.model.Amenity
import com.myolwinoo.smartproperty.data.model.PropertyImage
import com.myolwinoo.smartproperty.data.model.PropertyType
import com.myolwinoo.smartproperty.design.theme.AppDimens
import com.myolwinoo.smartproperty.design.theme.SPTheme
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.ic_back
import smartproperty.composeapp.generated.resources.label_address
import smartproperty.composeapp.generated.resources.label_create
import smartproperty.composeapp.generated.resources.label_description
import smartproperty.composeapp.generated.resources.label_latitude
import smartproperty.composeapp.generated.resources.label_longitude
import smartproperty.composeapp.generated.resources.label_price
import smartproperty.composeapp.generated.resources.label_title
import smartproperty.composeapp.generated.resources.title_create_property

@Serializable
private data class PropertyFormRoute(val propertyId: String)

fun NavController.navigatePropertyForm(
    propertyId: String
) {
    navigate(PropertyFormRoute(propertyId))
}

fun NavGraphBuilder.propertyForm(
    onBack: () -> Unit,
) {
    composable<PropertyFormRoute> {
        val propertyId = it.toRoute<PropertyFormRoute>().propertyId
        val viewModel: PropertyFormViewModel = koinViewModel {
            parametersOf(propertyId)
        }

        LaunchedEffect(Unit) {
            viewModel.events.collect { event ->
                when (event) {
                    "success" -> onBack()
                }
            }
        }

        Screen(
            onBack = onBack,
            title = viewModel.title,
            onTitleChanged = { viewModel.title = it },
            address = viewModel.address,
            onAddressChange = { viewModel.address = it },
            price = viewModel.price,
            onPriceChange = { viewModel.price = it },
            description = viewModel.description,
            onDescriptionChange = { viewModel.description = it },
            latitude = viewModel.latitude,
            onLatitudeChange = { viewModel.latitude = it },
            longitude = viewModel.longitude,
            onLongitudeChange = { viewModel.longitude = it },
            images = viewModel.images,
            onAddImages = viewModel::addImages,
            onRemoveImage = viewModel::removeImage,
            propertyTypes = viewModel.propertyTypes,
            selectedPropertyType = viewModel.selectedPropertyType,
            onPropertyTypeSelected = viewModel::selectPropertyType,
            amenities = viewModel.amenities,
            selectedAmenities = viewModel.selectedAmenities,
            onAmenitySelected = viewModel::selectAmenity,
            onCreate = viewModel::create
        )
    }
}

@Composable
private fun Screen(
    onBack: () -> Unit,
    title: TextFieldValue,
    onTitleChanged: (TextFieldValue) -> Unit,
    address: TextFieldValue,
    onAddressChange: (TextFieldValue) -> Unit,
    price: TextFieldValue,
    onPriceChange: (TextFieldValue) -> Unit,
    description: TextFieldValue,
    onDescriptionChange: (TextFieldValue) -> Unit,
    latitude: TextFieldValue,
    onLatitudeChange: (TextFieldValue) -> Unit,
    longitude: TextFieldValue,
    onLongitudeChange: (TextFieldValue) -> Unit,
    images: List<PropertyImage>,
    onAddImages: (List<ByteArray>) -> Unit,
    onRemoveImage: (String) -> Unit,
    propertyTypes: List<PropertyType>,
    selectedPropertyType: PropertyType?,
    onPropertyTypeSelected: (PropertyType) -> Unit,
    amenities: List<Amenity>,
    selectedAmenities: List<Amenity>,
    onAmenitySelected: (Amenity) -> Unit,
    onCreate: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(Res.string.title_create_property),
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_back),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(AppDimens.Spacing.l)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = AppDimens.Spacing.xl)
                    .fillMaxWidth(),
                value = title,
                onValueChange = onTitleChanged,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                label = { Text(stringResource(Res.string.label_title)) }
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = AppDimens.Spacing.xl)
                    .fillMaxWidth(),
                value = description,
                onValueChange = onDescriptionChange,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                minLines = 4,
                label = { Text(stringResource(Res.string.label_description)) }
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = AppDimens.Spacing.xl)
                    .fillMaxWidth(),
                value = price,
                onValueChange = onPriceChange,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                maxLines = 1,
                label = { Text(stringResource(Res.string.label_price)) }
            )
            Row(
                modifier = Modifier
                    .padding(horizontal = AppDimens.Spacing.xl)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(AppDimens.Spacing.l)
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f),
                    value = latitude,
                    onValueChange = onLatitudeChange,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ),
                    maxLines = 1,
                    label = { Text(stringResource(Res.string.label_latitude)) }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f),
                    value = longitude,
                    onValueChange = onLongitudeChange,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ),
                    maxLines = 1,
                    label = { Text(stringResource(Res.string.label_longitude)) }
                )
            }

            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = AppDimens.Spacing.xl)
                    .fillMaxWidth(),
                value = address,
                onValueChange = onAddressChange,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                minLines = 4,
                label = { Text(stringResource(Res.string.label_address)) }
            )
            PropertyTypeChooser(
                modifier = Modifier
                    .padding(horizontal = AppDimens.Spacing.xl)
                    .fillMaxWidth(),
                items = propertyTypes,
                onSelected = onPropertyTypeSelected,
                selectedItem = selectedPropertyType
            )
            AmenityChooser(
                modifier = Modifier
                    .padding(horizontal = AppDimens.Spacing.xl)
                    .fillMaxWidth(),
                items = amenities,
                onSelected = onAmenitySelected,
                selectedItems = selectedAmenities
            )
            ImageChooser(
                modifier = Modifier
                    .fillMaxWidth(),
                sideSpacing = AppDimens.Spacing.xl,
                images = images,
                onAddImages = onAddImages,
                onRemoveImage = onRemoveImage
            )
            Spacer(modifier = Modifier.height(AppDimens.Spacing.l))
            Button(
                modifier = Modifier
                    .padding(
                        start = AppDimens.Spacing.xl,
                        end = AppDimens.Spacing.xl,
                        bottom = AppDimens.Spacing.xxl
                    )
                    .widthIn(max = AppDimens.maxWidth)
                    .fillMaxWidth(),
                onClick = { onCreate() }
            ) {
                Text(
                    text = stringResource(Res.string.label_create)
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SPTheme {
        Screen(
            onBack = {},
            title = TextFieldValue(),
            onTitleChanged = {},
            address = TextFieldValue(),
            onAddressChange = {},
            price = TextFieldValue(),
            onPriceChange = {},
            description = TextFieldValue(),
            onDescriptionChange = {},
            latitude = TextFieldValue(),
            onLatitudeChange = {},
            longitude = TextFieldValue(),
            onLongitudeChange = {},
            images = listOf(),
            onAddImages = {},
            onRemoveImage = {},
            propertyTypes = listOf(),
            selectedPropertyType = null,
            onPropertyTypeSelected = {},
            amenities = listOf(),
            selectedAmenities = listOf(),
            onAmenitySelected = {},
            onCreate = {}
        )
    }
}