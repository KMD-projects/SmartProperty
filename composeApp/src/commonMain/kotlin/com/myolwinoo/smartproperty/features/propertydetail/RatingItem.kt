package com.myolwinoo.smartproperty.features.propertydetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.myolwinoo.smartproperty.data.model.Rating
import com.myolwinoo.smartproperty.design.theme.AppDimens
import com.myolwinoo.smartproperty.design.theme.SPTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.account_circle
import smartproperty.composeapp.generated.resources.favorite
import smartproperty.composeapp.generated.resources.favorite_filled
import smartproperty.composeapp.generated.resources.ic_delete
import smartproperty.composeapp.generated.resources.ic_edit
import smartproperty.composeapp.generated.resources.ic_star
import smartproperty.composeapp.generated.resources.ic_star_outlined

@Composable
fun RatingItem(
    modifier: Modifier = Modifier,
    rating: Rating,
    onDelete: (String) -> Unit,
    onEdit: (String) -> Unit,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = if (rating.isUserComment) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            },
            contentColor = if (rating.isUserComment) {
                MaterialTheme.colorScheme.onPrimaryContainer
            } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        },
        )
    ) {
        Column(
            modifier = Modifier.padding(AppDimens.Spacing.m),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = rating.profilePic,
                    contentDescription = "",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(48.dp),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(Res.drawable.account_circle),
                    error = painterResource(Res.drawable.account_circle),
                    colorFilter = ColorFilter.tint(LocalContentColor.current),
                )
                Text(
                    text = if (rating.isUserComment){
                        "${rating.username} (You)"
                    }else{
                        rating.username
                    },
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f)
                )
                if (rating.isUserComment) {
                    IconButton(
                        onClick = { onDelete(rating.id) },
                        modifier = Modifier
                            .padding(AppDimens.Spacing.m)
                            .size(32.dp)
                    ) {
                        Icon(
                            painter = painterResource(
                                Res.drawable.ic_delete
                            ),
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                    IconButton(
                        onClick = { onEdit(rating.id) },
                        modifier = Modifier
                            .padding(AppDimens.Spacing.m)
                            .size(32.dp)
                    ) {
                        Icon(
                            painter = painterResource(
                                Res.drawable.ic_edit
                            ),
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                (1..5).forEach { star ->
                    val icon = if (star <= rating.rating) {
                        Res.drawable.ic_star
                    } else {
                        Res.drawable.ic_star_outlined
                    }
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = "Star $star",
                        tint = if (star <= rating.rating) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.primary
                        },
                        modifier = Modifier
                            .size(32.dp)
                    )
                }
            }
            if (rating.comment.isNotBlank()) {
                Text(
                    text = rating.comment,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun UserRatingItemPreview() {
    SPTheme {
        RatingItem(
            rating = Rating(
                id = "1",
                rating = 4.5f,
                comment = "Great property!",
                username = "John Doe",
                profilePic = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png",
                isUserComment = true
            ),
            onDelete = {},
            onEdit = {}
        )
    }
}

@Preview
@Composable
fun RatingItemPreview() {
    SPTheme() {
        RatingItem(
            rating = Rating(
                id = "1",
                rating = 4.5f,
                comment = "Great property!",
                username = "John Doe",
                profilePic = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png",
                isUserComment = false
            ),
            onDelete = {},
            onEdit = {}
        )
    }
}