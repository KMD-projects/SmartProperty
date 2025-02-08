package com.myolwinoo.smartproperty.features.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.myolwinoo.smartproperty.design.theme.SPTheme
import io.ktor.http.Headers
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.account_circle
import smartproperty.composeapp.generated.resources.arrow_right

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    val statusBarInset = WindowInsets.statusBars.asPaddingValues()
    Column(
        modifier = modifier
            .padding(
                top = statusBarInset.calculateTopPadding()
            )
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            AsyncImage(
                model = "",
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                placeholder = painterResource(Res.drawable.account_circle),
                error = painterResource(Res.drawable.account_circle),
                colorFilter = ColorFilter.tint(LocalContentColor.current),
                modifier = Modifier
                    .clip(CircleShape)
                    .size(100.dp),
            )
            Text(
                text = "Emrys",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
        Header(text = "Personal information")
        EditItem(
            title = "Name",
            data = "Emrys",
            onClick = {}
        )
        EditItem(
            title = "Phone Number",
            data = "09123456789",
            onClick = {}
        )
        EditItem(
            title = "Email",
            data = "abc@gmail.com",
            onClick = {}
        )
        EditItem(
            title = "Address",
            data = "Not specified",
            onClick = {}
        )
        Header(text = "Support")
        Item(
            text = "Help Center",
            onClick = {}
        )
        Item(
            text = "Feedback",
            onClick = {}
        )
        Header(text = "Legal")
        Item(
            text = "Terms of Service",
            onClick = {}
        )
        Item(
            text = "Privacy Policy",
            onClick = {}
        )
        Text(
            text = "Version 1.0.0",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
        )
        Spacer(
            modifier = Modifier
                .height(20.dp)
        )
    }
}

@Composable
fun Header(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 32.dp,
                bottom = 12.dp
            )
    )
}

@Composable
private fun EditItem(
    modifier: Modifier = Modifier,
    title: String,
    data: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clickable { onClick() }
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
        Text(
            text = "Edit",
            textDecoration = TextDecoration.Underline,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier
        )
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
        ProfileScreen()
    }
}