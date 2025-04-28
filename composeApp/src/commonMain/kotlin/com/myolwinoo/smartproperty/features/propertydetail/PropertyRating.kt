package com.myolwinoo.smartproperty.features.propertydetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.myolwinoo.smartproperty.data.model.Property
import com.myolwinoo.smartproperty.design.theme.AppDimens
import com.myolwinoo.smartproperty.design.theme.SPTheme
import com.myolwinoo.smartproperty.utils.PreviewData
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PropertyRating(
    modifier: Modifier = Modifier,
    property: Property,
    onRate: () -> Unit,
    onSeeAll: () -> Unit,
    onDelete: (String) -> Unit,
    onEdit: (String) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = ("Ratings and reviews"),
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.size(AppDimens.Spacing.m))
            Text(
                text = "(${property.avgRating})",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
            )
        }
        if (!property.hasReviewed) {
            Spacer(modifier = Modifier.size(AppDimens.Spacing.m))
            Button(
                onClick = { onRate() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Write your review")
            }
        }
        property.ownReview?.let { rating ->
            RatingItem(
                modifier = Modifier.padding(top = 8.dp),
                rating = rating,
                onDelete = { onDelete(rating.id) },
                onEdit = { onEdit(rating.id) }
            )
        }
        property.topReviews.forEach { rating ->
            RatingItem(
                modifier = Modifier.padding(top = 8.dp),
                rating = rating,
                onDelete = { },
                onEdit = {}
            )
        }
        if (property.reviews.size > 3) {
            TextButton(
                modifier = Modifier,
                onClick = { onSeeAll() },
            ) {
                Text(
                    text = "See all ${property.reviews.size} reviews",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SPTheme {
        PropertyRating(
            property = PreviewData.properties.first().copy(hasReviewed = true),
            onRate = {},
            onSeeAll = {},
            onDelete = {},
            onEdit = {}
        )
    }
}

@Preview
@Composable
private fun NotReviewedPreview() {
    SPTheme {
        PropertyRating(
            property = PreviewData.properties.first().copy(hasReviewed = false),
            onRate = {},
            onSeeAll = {},
            onDelete = {},
            onEdit = {}
        )
    }
}

@Preview
@Composable
private fun LessThanThreeReviewPreview() {
    SPTheme {
        PropertyRating(
            property = PreviewData.properties.get(1),
            onRate = {},
            onSeeAll = {},
            onDelete = {},
            onEdit = {}
        )
    }
}