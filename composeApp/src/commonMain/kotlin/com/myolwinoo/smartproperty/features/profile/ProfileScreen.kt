@file:OptIn(ExperimentalMaterial3Api::class)

package com.myolwinoo.smartproperty.features.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil3.compose.AsyncImage
import com.myolwinoo.smartproperty.common.EditProfileDialog
import com.myolwinoo.smartproperty.data.model.RequisitionStatus
import com.myolwinoo.smartproperty.data.model.User
import com.myolwinoo.smartproperty.data.model.UserRole
import com.myolwinoo.smartproperty.design.theme.AppDimens
import com.myolwinoo.smartproperty.design.theme.SPTheme
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.account_circle
import smartproperty.composeapp.generated.resources.arrow_right
import smartproperty.composeapp.generated.resources.label_address
import smartproperty.composeapp.generated.resources.label_email
import smartproperty.composeapp.generated.resources.label_feedback
import smartproperty.composeapp.generated.resources.label_help
import smartproperty.composeapp.generated.resources.label_language
import smartproperty.composeapp.generated.resources.label_logout
import smartproperty.composeapp.generated.resources.label_name
import smartproperty.composeapp.generated.resources.label_phone
import smartproperty.composeapp.generated.resources.label_privacy_policy
import smartproperty.composeapp.generated.resources.label_terms
import smartproperty.composeapp.generated.resources.title_legal
import smartproperty.composeapp.generated.resources.title_personal_info
import smartproperty.composeapp.generated.resources.title_support

@Serializable
object ProfileRoute

fun NavController.navigateProfile() {
    navigate(ProfileRoute)
}

fun NavGraphBuilder.profileScreen(
    onLogout: () -> Unit
) {
    composable<ProfileRoute> {
        ProfileScreen(
            onLogout = onLogout
        )
    }
}

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onLogout: () -> Unit
) {
    val viewModel: ProfileViewModel = koinViewModel<ProfileViewModel>()
    val profileState = viewModel.profile.collectAsStateWithLifecycle()
    var showEditProfileDialog by remember { mutableStateOf(false) }

    if (showEditProfileDialog) {
        EditProfileDialog(
            onDismissRequest = {
                showEditProfileDialog = false
                viewModel.cancelEdit()
            },
            onSubmit = {
                showEditProfileDialog = false
                viewModel.updateProfile()
            },
            name = viewModel.name,
            email = viewModel.email,
            phone = viewModel.phone,
            address = viewModel.address,
            onNameChange = {
                viewModel.name = it
            },
            onEmailChange = {
                viewModel.email = it
            },
            onPhoneChange = {
                viewModel.phone = it
            },
            onAddressChange = {
                viewModel.address = it
            },
            emailError = null,

            )
    }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                "logout" -> onLogout()
            }
        }
    }

    Screen(
        modifier = modifier,
        profile = profileState.value,
        isLoading = viewModel.isLoading,
        onRefresh = viewModel::refresh,
        becomeLandlord = viewModel::becomeLandlord,
        onLogout = viewModel::logout,
        onProfileEdit = { showEditProfileDialog = true }
    )
}

@Composable
private fun Screen(
    modifier: Modifier = Modifier,
    profile: User?,
    isLoading: Boolean,
    onRefresh: () -> Unit,
    becomeLandlord: () -> Unit,
    onLogout: () -> Unit,
    onProfileEdit: () -> Unit,
) {
    val statusBarInset = WindowInsets.statusBars.asPaddingValues()
    val pullToRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        state = pullToRefreshState,
        isRefreshing = isLoading,
        onRefresh = onRefresh
    ) {
        Column(
            modifier = modifier
                .padding(
                    top = statusBarInset.calculateTopPadding()
                )
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            profile?.let {
                userInformation(
                    profile = it,
                    becomeLandlord = becomeLandlord,
                    onProfileEdit = {
                        onProfileEdit()
                    }
                )
            }
            ProfileDetailsItem(
                title = stringResource(Res.string.label_language),
                data = "English",
            )
            Header(text = stringResource(Res.string.title_support))
            Item(
                text = stringResource(Res.string.label_help),
                onClick = {}
            )
            Item(
                text = stringResource(Res.string.label_feedback),
                onClick = {}
            )
            Header(text = stringResource(Res.string.title_legal))
            Item(
                text = stringResource(Res.string.label_terms),
                onClick = {}
            )
            Item(
                text = stringResource(Res.string.label_privacy_policy),
                onClick = {}
            )
            Spacer(
                modifier = Modifier
                    .height(AppDimens.Spacing.xl)
            )
            Button(
                modifier = Modifier
                    .padding(
                        start = AppDimens.Spacing.xl,
                        end = AppDimens.Spacing.xl,
                    )
                    .fillMaxWidth()
                    .widthIn(max = AppDimens.maxWidth),
                onClick = onLogout,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            ) {
                Text(
                    text = stringResource(Res.string.label_logout)
                )
            }
            Spacer(
                modifier = Modifier
                    .height(AppDimens.Spacing.xl)
            )
            Text(
                text = "Version 1.0.0",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(
                modifier = Modifier
                    .height(AppDimens.Spacing.xxl)
            )
        }
    }
}

@Composable
fun ColumnScope.userInformation(
    profile: User,
    becomeLandlord: () -> Unit,
    onProfileEdit: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        AsyncImage(
            model = profile.profileImage,
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop,
            placeholder = painterResource(Res.drawable.account_circle),
            error = painterResource(Res.drawable.account_circle),
            colorFilter = ColorFilter.tint(LocalContentColor.current),
            modifier = Modifier
                .clip(CircleShape)
                .size(100.dp),
        )
        Spacer(modifier = Modifier.height(12.dp))
        LandlordStatus(
            modifier = Modifier
                .padding(
                    start = AppDimens.Spacing.xl,
                    end = AppDimens.Spacing.xl,
                ),
            profile = profile,
            onBecomeLandlord = becomeLandlord
        )
    }
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 32.dp,
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Header(
            modifier = Modifier
                .weight(1f),
            padding = PaddingValues(
                horizontal = 0.dp,
                vertical = 0.dp
            ),
            text = stringResource(Res.string.title_personal_info)
        )
        TextButton(
            onClick = { onProfileEdit() },
        ) {
            Text(
                text = "Edit",
                textDecoration = TextDecoration.Underline,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
            )
        }

    }
    ProfileDetailsItem(
        title = stringResource(Res.string.label_name),
        data = profile.name,
    )
    ProfileDetailsItem(
        title = stringResource(Res.string.label_phone),
        data = profile.phone.ifBlank { "Not specified" },
    )
    ProfileDetailsItem(
        title = stringResource(Res.string.label_email),
        data = profile.email.ifBlank { "Not specified" },
    )
    ProfileDetailsItem(
        title = stringResource(Res.string.label_address),
        data = profile.address.ifBlank { "Not specified" },
    )
}

@Composable
fun Header(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(
        start = 20.dp,
        end = 20.dp,
        top = 32.dp,
        bottom = 12.dp
    ),
    text: String
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier
            .padding(padding)
    )
}

@Composable
private fun ProfileDetailsItem(
    modifier: Modifier = Modifier,
    title: String,
    data: String
) {
    Row(
        modifier = modifier
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
            )
            Text(
                text = data,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
            )
        }
    }
    HorizontalDivider(
        color = MaterialTheme.colorScheme.onBackground,
        thickness = 0.5.dp,
        modifier = Modifier
            .padding(horizontal = 20.dp)
    )
}

@Composable
private fun Item(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .weight(1f)
        )
        Icon(
            painter = painterResource(Res.drawable.arrow_right),
            contentDescription = null
        )
    }
    HorizontalDivider(
        color = MaterialTheme.colorScheme.onBackground,
        thickness = 0.5.dp,
        modifier = Modifier
            .padding(horizontal = 20.dp)
    )
}

@Preview
@Composable
private fun Preview() {
    SPTheme {
        Screen(
            profile = User(
                id = "1",
                name = "John Doe",
                phone = "+1234567890",
                email = " ",
                address = "",
                profileImage = "",
                role = UserRole.LANDLORD,
                verified = true,
                createdAt = "",
                updatedAt = "",
                requisitionStatus = RequisitionStatus.REJECTED,
                username = ""
            ),
            isLoading = false,
            onLogout = {},
            onRefresh = {},
            becomeLandlord = {},
            onProfileEdit = {}
        )
    }
}