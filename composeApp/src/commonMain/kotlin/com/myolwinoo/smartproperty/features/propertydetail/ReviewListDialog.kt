package com.myolwinoo.smartproperty.features.propertydetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.myolwinoo.smartproperty.data.model.Rating
import com.myolwinoo.smartproperty.design.theme.AppDimens
import com.myolwinoo.smartproperty.design.theme.SPTheme
import com.myolwinoo.smartproperty.utils.PreviewData
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.ic_back

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewListDialog(
    onDismissRequest: () -> Unit,
    onDelete: (String) -> Unit,
    onEdit: (String) -> Unit,
    ratings: List<Rating>
) {

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Reviews",
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onDismissRequest) {
                            Icon(
                                painter = painterResource(Res.drawable.ic_back),
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            ReviewList(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                onDelete = onDelete,
                onEdit = onEdit,
                ratings = ratings
            )
        }
    }
}

@Composable
fun ReviewList(
    modifier: Modifier,
    onDelete: (String) -> Unit,
    onEdit: (String) -> Unit,
    ratings: List<Rating>
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            horizontal = AppDimens.Spacing.xl,
            vertical = AppDimens.Spacing.m
        ),
        verticalArrangement = Arrangement.spacedBy(AppDimens.Spacing.m)
    ) {
        items(ratings, key = { it.id }) { rating ->
            RatingItem(
                modifier = Modifier,
                rating = rating,
                onDelete = onDelete,
                onEdit = onEdit
            )
        }
    }
}

@Preview
@Composable
fun ReviewListPreview() {
    SPTheme {
        ReviewList(
            modifier = Modifier,
            onDelete = {},
            onEdit = {},
            ratings = PreviewData.properties.first().reviews
        )
    }
}