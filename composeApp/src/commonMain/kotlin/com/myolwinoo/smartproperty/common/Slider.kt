package com.myolwinoo.smartproperty.common

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil3.compose.AsyncImage
import com.myolwinoo.smartproperty.data.model.PropertyImage
import com.myolwinoo.smartproperty.design.theme.AppDimens
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import smartproperty.composeapp.generated.resources.Res
import smartproperty.composeapp.generated.resources.placeholder
import kotlin.math.absoluteValue

@Composable
fun Slider(
    modifier: Modifier = Modifier,
    images: List<PropertyImage>
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val pagerState = rememberPagerState(pageCount = { images.size })
        val pagerIsDragged by pagerState.interactionSource.collectIsDraggedAsState()

        val pageInteractionSource = remember { MutableInteractionSource() }
        val pageIsPressed by pageInteractionSource.collectIsPressedAsState()

        // Stop auto-advancing when pager is dragged or one of the pages is pressed
        val autoAdvance = !pagerIsDragged && !pageIsPressed

        if (autoAdvance) {
            LaunchedEffect(pagerState, pageInteractionSource) {
                while (true) {
                    delay(2000)
                    val nextPage = (pagerState.currentPage + 1) % images.size
                    pagerState.animateScrollToPage(nextPage)
                }
            }
        }

        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = AppDimens.Spacing.xl),
            pageSpacing = AppDimens.Spacing.m
        ) { page ->
            Card(
                Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
            ) {
                AsyncImage(
                    model = (images[page] as PropertyImage.Remote).url,
                    placeholder = painterResource(Res.drawable.placeholder),
                    error = painterResource(Res.drawable.placeholder),
                    contentDescription = "Property Image $page",
                    modifier = modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}