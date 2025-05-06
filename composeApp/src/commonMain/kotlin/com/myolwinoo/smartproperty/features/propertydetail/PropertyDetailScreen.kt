@file:OptIn(ExperimentalMaterial3Api::class)

package com.myolwinoo.smartproperty.features.propertydetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import coil3.compose.AsyncImage
import com.myolwinoo.smartproperty.common.RatingDialog
import com.myolwinoo.smartproperty.common.Slider
import com.myolwinoo.smartproperty.data.model.Property
import com.myolwinoo.smartproperty.data.model.PropertyImage
import com.myolwinoo.smartproperty.data.model.UserRole
import com.myolwinoo.smartproperty.design.theme.AppDimens
import com.myolwinoo.smartproperty.design.theme.SPTheme
import com.myolwinoo.smartproperty.map.MapView
import com.myolwinoo.smartproperty.utils.PreviewData
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.currency
import smartproperty.composeapp.generated.resources.ic_back
import smartproperty.composeapp.generated.resources.label_amenities
import smartproperty.composeapp.generated.resources.label_cancel
import smartproperty.composeapp.generated.resources.label_confirm
import smartproperty.composeapp.generated.resources.label_ok
import smartproperty.composeapp.generated.resources.label_property_type
import smartproperty.composeapp.generated.resources.location_on
import smartproperty.composeapp.generated.resources.message_delete_review
import smartproperty.composeapp.generated.resources.message_login_error
import smartproperty.composeapp.generated.resources.month
import smartproperty.composeapp.generated.resources.title_delete_review
import smartproperty.composeapp.generated.resources.title_login_error
import kotlin.math.absoluteValue

@Serializable
private data class PropertyDetailRoute(val propertyId: String)

fun NavController.navigatePropertyDetailScreen(propertyId: String) {
    navigate(PropertyDetailRoute(propertyId))
}

fun NavGraphBuilder.propertyDetailScreen(
    onBack: () -> Unit,
    navigateToAppointmentForm: (propertyId: String) -> Unit
) {
    composable<PropertyDetailRoute> {
        val propertyId = it.toRoute<PropertyDetailRoute>().propertyId
        val viewModel: PropertyDetailViewModel = koinViewModel {
            parametersOf(propertyId)
        }
        val property = viewModel.property.collectAsStateWithLifecycle()
        val userRole = viewModel.userRole.collectAsStateWithLifecycle()

        var showRatingDialog by remember { mutableStateOf(false) }
        var showAllRatingDialog by remember { mutableStateOf(false) }
        var showDeleteRatingDialog by remember { mutableStateOf(false) }

        LifecycleResumeEffect(Unit) {
            viewModel.refresh()
            onPauseOrDispose {}
        }

        if (showDeleteRatingDialog) {
            AlertDialog(
                title = {
                    Text(stringResource(Res.string.title_delete_review))
                },
                text = {
                    Text(stringResource(Res.string.message_delete_review))
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.deleteReview()
                            showDeleteRatingDialog = false
                        }
                    ) {
                        Text(stringResource(Res.string.label_confirm))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDeleteRatingDialog = false
                        }
                    ) {
                        Text(stringResource(Res.string.label_cancel))
                    }
                },
                onDismissRequest = {
                    showDeleteRatingDialog = false
                }
            )
        }

        if (showAllRatingDialog){
            ReviewListDialog(
                onDismissRequest = {
                    showAllRatingDialog = false
                },
                onDelete = {
                    viewModel.deleteReview()
                },
                onEdit = {
                    showRatingDialog = true
                },
                ratings = property.value?.reviews.orEmpty()
            )
        }

        if (showRatingDialog) {
            RatingDialog(
                rating = viewModel.rating,
                onRatingChanged = viewModel::setRatingValue,
                review = viewModel.review.orEmpty(),
                onReviewChanged = { viewModel.review = it },
                onSubmit = {
                    viewModel.submitRating()
                    showRatingDialog = false
                },
                onDismissRequest = {
                    viewModel.resetRating()
                    showRatingDialog = false
                }
            )
        }

        Screen(
            property = property.value,
            userRole = userRole.value,
            onBack = onBack,
            onFavoriteClick = viewModel::toggleFavorite,
            navigateToAppointmentForm = {
                navigateToAppointmentForm(propertyId)
            },
            onRate = {
                showRatingDialog = true
            },
            onShowAllRating = {
                showAllRatingDialog = true
            },
            onDeleteRating = {
                showDeleteRatingDialog = true
            },
            onEditRating = {
                showRatingDialog = true
            }
        )
    }
}

@Composable
private fun Screen(
    property: Property?,
    userRole: UserRole?,
    onBack: () -> Unit,
    onFavoriteClick: (String) -> Unit,
    navigateToAppointmentForm: () -> Unit,
    onRate: () -> Unit,
    onShowAllRating: () -> Unit,
    onDeleteRating: (String) -> Unit,
    onEditRating: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
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
        property ?: return@Scaffold
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(1f)
            ) {
                Slider(images = property.images)

                Column(
                    modifier = Modifier
                        .padding(AppDimens.Spacing.xl)
                        .fillMaxSize()
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

                    if (property.description.isNotBlank()) {
                        Spacer(modifier = Modifier.height(AppDimens.Spacing.m))
                        Text(
                            text = "Description",
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Spacer(modifier = Modifier.height(AppDimens.Spacing.s))
                        Text(
                            text = property.description,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }

                    if (property.propertyType.isNotBlank()) {
                        Spacer(modifier = Modifier.height(AppDimens.Spacing.m))

                        Text(
                            text = stringResource(Res.string.label_property_type),
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(AppDimens.Spacing.s))
                        SuggestionChip(
                            shape = RoundedCornerShape(percent = 50),
                            onClick = {},
                            label = { Text(text = property.propertyType) },
                            modifier = Modifier
                        )
                    }

                    Spacer(modifier = Modifier.height(AppDimens.Spacing.m))
                    Text(
                        text = stringResource(Res.string.label_amenities),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(AppDimens.Spacing.s))
                    // Amenities List
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(AppDimens.Spacing.m)) {
                        property.amenities.forEach { amenity ->
                            SuggestionChip(
                                shape = RoundedCornerShape(percent = 50),
                                onClick = {},
                                label = { Text(text = amenity) },
                                modifier = Modifier
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(AppDimens.Spacing.l))
                    Text(
                        text = "Location",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = AppDimens.Spacing.m)
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
                    MapView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        latitude = property.latitude,
                        longitude = property.longitude,
                        markerTitle = property.title,
                        markerSnippet = property.location
                    )

                    Spacer(modifier = Modifier.height(AppDimens.Spacing.xl))
                    PropertyRating(
                        onRate = onRate,
                        property = property,
                        onSeeAll = {
                            onShowAllRating()
                        },
                        onDelete = onDeleteRating,
                        onEdit = onEditRating,
                    )
                    Spacer(modifier = Modifier.height(AppDimens.Spacing.l))
                }
            }
            PropertyDetailFooter(
                modifier = Modifier
                    .padding(
                        horizontal = AppDimens.Spacing.xl,
                        vertical = AppDimens.Spacing.l
                    ),
                property = property,
                onFavoriteClick = onFavoriteClick,
                navigateToAppointmentForm = navigateToAppointmentForm,
                userRole = userRole,
                cancelAppointment = {},
                viewAppointments = {}
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SPTheme {
        Screen(
            property = PreviewData.properties.first(),
            userRole = UserRole.LANDLORD,
            onBack = {},
            onFavoriteClick = {},
            navigateToAppointmentForm = {},
            onRate = {},
            onShowAllRating = {},
            onDeleteRating = {},
            onEditRating = {}
        )
    }
}