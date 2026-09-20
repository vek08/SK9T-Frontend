package com.k9t.scoreknight.presentation.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> AutoScrollingCarousel(
    items: List<T>,
    itemContent: @Composable (T) -> Unit,
    modifier: Modifier = Modifier,
    autoScrollDelay: Long = 4000L,
    pageSpacing: androidx.compose.ui.unit.Dp = 16.dp,
    cardWidth: androidx.compose.ui.unit.Dp = 280.dp
) {
    if (items.isEmpty()) return

    val pagerState = rememberPagerState(pageCount = { items.size })
    var isUserInteracting by remember { mutableStateOf(false) }
    var scrollDirection by remember { mutableIntStateOf(1) }
    
    // Auto-scroll logic (Ping-Pong)
    if (items.size > 1) {
        LaunchedEffect(isUserInteracting, items.size) {
            if (!isUserInteracting) {
                while (true) {
                    delay(autoScrollDelay)
                    
                    // Pause if user is dragging or an animation is already running
                    if (!pagerState.isScrollInProgress) {
                        if (pagerState.currentPage == items.size - 1) {
                            scrollDirection = -1
                        } else if (pagerState.currentPage == 0) {
                            scrollDirection = 1
                        }
                        
                        val nextIndex = (pagerState.currentPage + scrollDirection).coerceIn(0, items.size - 1)
                        pagerState.animateScrollToPage(
                            page = nextIndex,
                            animationSpec = tween(durationMillis = 800)
                        )
                    }
                }
            }
        }
    }

    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        // Calculate exact padding needed to center the first and last cards
        val horizontalPadding = (maxWidth - cardWidth) / 2
        val safePadding = if (horizontalPadding > 0.dp) horizontalPadding else 0.dp

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(Unit) {
                    // Detect when user touches the carousel to pause auto-scrolling
                    while (true) {
                        awaitPointerEventScope {
                            awaitFirstDown(requireUnconsumed = false)
                            isUserInteracting = true
                            
                            // Wait for all pointers to go up
                            do {
                                val event = awaitPointerEvent()
                            } while (event.changes.any { it.pressed })
                            
                            isUserInteracting = false
                        }
                    }
                },
            contentPadding = PaddingValues(horizontal = safePadding),
            pageSize = PageSize.Fixed(cardWidth),
            pageSpacing = pageSpacing,
            verticalAlignment = Alignment.CenterVertically
        ) { page ->
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        // Calculate the page offset from the center
                        val pageOffset = (
                            (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                        ).absoluteValue

                        // Animate scale: center is 1.0f, completely off-center drops to ~0.85f
                        val scale = 1.0f - (pageOffset * 0.15f).coerceIn(0f, 0.15f)
                        
                        // Animate alpha: center is 1.0f, completely off-center drops to ~0.6f
                        val alphaValue = 1.0f - (pageOffset * 0.4f).coerceIn(0f, 0.4f)
                        
                        scaleX = scale
                        scaleY = scale
                        this.alpha = alphaValue
                    },
                contentAlignment = Alignment.Center
            ) {
                itemContent(items[page])
            }
        }
    }
}
