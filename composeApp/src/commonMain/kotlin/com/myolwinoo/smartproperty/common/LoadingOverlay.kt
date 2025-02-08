package com.myolwinoo.smartproperty.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.myolwinoo.smartproperty.design.theme.loadingOverlay
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun LoadingOverlay(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clickable {  }
//            .background(loadingOverlay)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            strokeWidth = 8.dp
        )
    }
}

@Preview
@Composable
private fun Preview() {
    LoadingOverlay()
}
