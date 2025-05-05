package com.myolwinoo.smartproperty.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.myolwinoo.smartproperty.design.theme.AppDimens
import com.myolwinoo.smartproperty.design.theme.SPTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EmptyView(
    modifier: Modifier = Modifier,
    text: String = "Currently no data."
) {
    Box(
        modifier = modifier
            .padding(horizontal = AppDimens.Spacing.xl)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun Preview() {
    SPTheme {
        EmptyView()
    }
}