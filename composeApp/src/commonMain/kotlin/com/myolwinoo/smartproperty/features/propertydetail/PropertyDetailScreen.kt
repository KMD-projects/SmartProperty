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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.myolwinoo.smartproperty.data.model.Property
import com.myolwinoo.smartproperty.data.model.UserRole
import com.myolwinoo.smartproperty.design.theme.AppDimens
import com.myolwinoo.smartproperty.design.theme.SPTheme
import com.myolwinoo.smartproperty.utils.PreviewData
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.ic_back
import smartproperty.composeapp.generated.resources.ic_star
import smartproperty.composeapp.generated.resources.label_rate
import smartproperty.composeapp.generated.resources.location_on
import smartproperty.composeapp.generated.resources.title_rating
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

        LifecycleResumeEffect(Unit) {

            viewModel.refresh()

            onPauseOrDispose {}
        }

        if (showRatingDialog) {
            RatingDialog(
                rating = viewModel.rating,
                onRatingChanged = viewModel::setRatingValue,
                onSubmit = {
                    viewModel.submitRating()
                    showRatingDialog = false
                },
                onDismissRequest = {
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
    onRate: () -> Unit
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
                Pager(images = property.images)

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
                            text = "${property.price} MMK",
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

                    Spacer(modifier = Modifier.height(AppDimens.Spacing.l))

                    PropertyRating(
                        avgRating = property.avgRating,
                        hasReviewed = property.hasReviewed,
                        onRate = onRate
                    )

                    Spacer(modifier = Modifier.height(AppDimens.Spacing.l))

                    Text(
                        text = property.description,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
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

@Composable
private fun Pager(images: List<String>) {
    val pagerState = rememberPagerState(pageCount = {
        images.size
    })
    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = AppDimens.Spacing.xl),
        pageSpacing = AppDimens.Spacing.m
    ) { page ->
        Card(
            Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
        ) {
            AsyncImage(
                model = images[page],
                contentDescription = "Property Image $page",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
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
            onRate = {}
        )
    }
}