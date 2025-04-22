package com.myolwinoo.smartproperty.features.propertydetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.ic_star
import smartproperty.composeapp.generated.resources.label_rate
import smartproperty.composeapp.generated.resources.title_rating

@Composable
fun PropertyRating(
    modifier: Modifier = Modifier,
    avgRating: Float,
    hasReviewed: Boolean,
    onRate: () -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(Res.string.title_rating),
            style = MaterialTheme.typography.titleMedium,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(36.dp),
                    painter = painterResource(Res.drawable.ic_star),
                    contentDescription = "Rating",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = avgRating.toString(),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(start = 2.dp)
                )
            }

            if (!hasReviewed) {
                TextButton(
                    onClick = { onRate() }
                ) {
                    Text(text = stringResource(Res.string.label_rate))
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    PropertyRating(
        avgRating = 4.5f,
        hasReviewed = true,
        onRate = {}
    )
}

@Preview
@Composable
private fun NotReviewedPreview() {
    PropertyRating(
        avgRating = 4.5f,
        hasReviewed = false,
        onRate = {}
    )
}