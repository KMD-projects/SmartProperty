package com.myolwinoo.smartproperty

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.myolwinoo.smartproperty.design.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    AppTheme {
        Scaffold { innerPadding ->
            Text(
                modifier = Modifier.padding(innerPadding),
                text = "ABCDDDDDDDDDD"
            )
        }
    }
}