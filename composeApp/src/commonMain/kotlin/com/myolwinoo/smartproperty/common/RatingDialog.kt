package com.myolwinoo.smartproperty.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.myolwinoo.smartproperty.design.theme.AppDimens
import com.myolwinoo.smartproperty.design.theme.SPTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.ic_star
import smartproperty.composeapp.generated.resources.ic_star_outlined
import smartproperty.composeapp.generated.resources.label_description
import smartproperty.composeapp.generated.resources.label_review

@Composable
fun RatingDialog(
    rating: Int,
    onRatingChanged: (Int) -> Unit,
    review: String,
    onReviewChanged: (String) -> Unit,
    onSubmit: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Rate this property",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    (1..5).forEach { star ->
                        val icon = if (star <= rating) {
                            Res.drawable.ic_star
                        } else {
                            Res.drawable.ic_star_outlined
                        }
                        Icon(
                            painter = painterResource(icon),
                            contentDescription = "Star $star",
                            tint = if (star <= rating) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.primary
                            },
                            modifier = Modifier
                                .size(40.dp)
                                .clickable { onRatingChanged(star) }
                        )
                    }
                }
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = review,
                    onValueChange = onReviewChanged,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    minLines = 4,
                    label = { Text(stringResource(Res.string.label_review)) }
                )
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = { onDismissRequest() }) {
                        Text("Cancel")
                    }
                    TextButton(onClick = { onSubmit() }) {
                        Text("Submit")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SPTheme {
        RatingDialog(
            rating = 3,
            onRatingChanged = {},
            onDismissRequest = {},
            onSubmit = {},
            review = "",
            onReviewChanged = {}
        )
    }
}