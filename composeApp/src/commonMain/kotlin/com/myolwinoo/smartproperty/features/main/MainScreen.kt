package com.myolwinoo.smartproperty.features.main

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.myolwinoo.smartproperty.features.appointments.AppointmentsScreen
import com.myolwinoo.smartproperty.features.explore.ExploreScreen
import com.myolwinoo.smartproperty.features.profile.ProfileScreen
import com.myolwinoo.smartproperty.features.wishlists.WishlistsScreen
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Serializable
object MainRoute

fun NavController.navigateToMain() {
    navigate(MainRoute)
}

fun NavGraphBuilder.mainScreen(
    onLogout: () -> Unit,
    navigateToSearch: () -> Unit,
    navigateToPropertyDetail: (String) -> Unit,
    onCreateProperty: () -> Unit
) {
    composable<MainRoute> {
        Screen(
            onLogout = onLogout,
            navigateToSearch = navigateToSearch,
            navigateToPropertyDetail = navigateToPropertyDetail,
            onCreateProperty = onCreateProperty
        )
    }
}

@Composable
private fun Screen(
    onLogout: () -> Unit,
    navigateToSearch: () -> Unit,
    navigateToPropertyDetail: (String) -> Unit,
    onCreateProperty: () -> Unit
) {
    var currentDestination by rememberSaveable { mutableStateOf(MainDestinations.Explore) }
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            MainDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            painter = painterResource(it.icon),
                            contentDescription = stringResource(it.contentDescription)
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(it.label),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        }
    ) {
        when (currentDestination) {
            MainDestinations.Explore -> ExploreScreen(
                navigateToSearch = navigateToSearch,
                navigateToPropertyDetail = navigateToPropertyDetail,
                onCreateProperty = onCreateProperty
            )

            MainDestinations.WISHLISTS -> WishlistsScreen()
            MainDestinations.PROFILE -> ProfileScreen(
                onLogout = onLogout
            )

            MainDestinations.APPOINTMENTS -> AppointmentsScreen()
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Screen(
        onLogout = {},
        navigateToSearch = {},
        navigateToPropertyDetail = {},
        onCreateProperty = {}
    )
}