package com.myolwinoo.smartproperty.features.propertyform

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myolwinoo.smartproperty.data.model.Amenity
import com.myolwinoo.smartproperty.data.model.PropertyImage
import com.myolwinoo.smartproperty.data.model.PropertyType
import com.myolwinoo.smartproperty.data.network.SPApi
import com.myolwinoo.smartproperty.data.network.model.CreatePropertyRequest
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class PropertyFormViewModel(
    private val propertyId: String,
    private val spApi: SPApi
): ViewModel() {

    var title by mutableStateOf(TextFieldValue())
    var address by mutableStateOf(TextFieldValue())
    var price by mutableStateOf(TextFieldValue())
    var description by mutableStateOf(TextFieldValue())
    var latitude by mutableStateOf(TextFieldValue())
    var longitude by mutableStateOf(TextFieldValue())
    var images by mutableStateOf(listOf<PropertyImage>())
    var selectedAmenities by mutableStateOf(listOf<Amenity>())
    var selectedPropertyType by mutableStateOf<PropertyType?>(null)

    var propertyTypes by mutableStateOf(emptyList<PropertyType>())
    var amenities by mutableStateOf(emptyList<Amenity>())

    private val _events = MutableSharedFlow<String>()
    val events: SharedFlow<String> = _events

    init {
        loadAmenities()
        loadPropertyTypes()
    }

    fun addImages(images: List<ByteArray>) {
        this.images = this.images + images.mapIndexed { i, data ->
            PropertyImage.Local(
                id = (Clock.System.now().toEpochMilliseconds() + i).toString(),
                data = data
            )
        }
    }

    fun removeImage(imageId: String) {
        images = images.filter { it.imageId != imageId }
    }

    fun selectPropertyType(propertyType: PropertyType) {
        selectedPropertyType = propertyType
    }

    fun selectAmenity(amenity: Amenity) {
        selectedAmenities = if (selectedAmenities.contains(amenity)) {
            selectedAmenities.filter { it != amenity }
        } else {
            selectedAmenities + amenity
        }
    }

    fun create() {
        viewModelScope.launch {
            val images = images.filterIsInstance<PropertyImage.Local>()
            spApi.createProperty(
                request = CreatePropertyRequest(
                    title = title.text,
                    description = description.text,
                    price = price.text.toDoubleOrNull(),
                    latitude = latitude.text,
                    longitude = longitude.text,
                    address = address.text,
                    propertyTypeId = selectedPropertyType?.id,
                    amenityIds = selectedAmenities.map { it.id },
                    images = images
                )
            ).onFailure {
                Napier.e { it.message.orEmpty() }
            }.onSuccess {
                _events.emit("success")
            }
        }
    }

    private fun loadAmenities() {
        viewModelScope.launch {
            spApi.getAmenities().onFailure {
                Napier.e { it.message.orEmpty() }
                amenities = emptyList()
            }.onSuccess {
                amenities = it
            }
        }
    }

    private fun loadPropertyTypes() {
        viewModelScope.launch {
            spApi.getPropertyTypes().onFailure {
                Napier.e { it.message.orEmpty() }
                propertyTypes = emptyList()
            }.onSuccess {
                propertyTypes = it
            }
        }
    }
}