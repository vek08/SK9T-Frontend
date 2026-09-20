package com.k9t.scoreknight.presentation.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.k9t.scoreknight.domain.model.CricketBanner
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CricketerBannerSlider(
    banners: List<CricketBanner>,
    onBannerClick: (CricketBanner) -> Unit,
    modifier: Modifier = Modifier
) {
    if (banners.isEmpty()) return

    // Using a sufficiently large number to simulate infinite looping without crashing Semantics
    val pageCount = 10000
    val initialPage = (pageCount / 2) - ((pageCount / 2) % banners.size)
    val pagerState = rememberPagerState(initialPage = initialPage, pageCount = { pageCount })
    
    var isUserInteracting by remember { mutableStateOf(false) }

    // Auto-scroll logic
    if (banners.size > 1) {
        LaunchedEffect(isUserInteracting) {
            if (!isUserInteracting) {
                while (true) {
                    delay(3500L)
                    if (!pagerState.isScrollInProgress) {
                        pagerState.animateScrollToPage(
                            page = pagerState.currentPage + 1,
                            animationSpec = tween(durationMillis = 800)
                        )
                    }
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    while (true) {
                        awaitPointerEventScope {
                            awaitFirstDown(requireUnconsumed = false)
                            isUserInteracting = true
                            do {
                                val event = awaitPointerEvent()
                            } while (event.changes.any { it.pressed })
                            isUserInteracting = false
                        }
                    }
                },
            contentPadding = PaddingValues(horizontal = 16.dp),
            pageSpacing = 16.dp
        ) { page ->
            val actualIndex = page % banners.size
            val banner = banners[actualIndex]
            
            BannerCard(
                banner = banner,
                onClick = { onBannerClick(banner) }
            )
        }

        // Indicators
        if (banners.size > 1) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                val currentIndex = pagerState.currentPage % banners.size
                banners.forEachIndexed { index, _ ->
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(
                                if (index == currentIndex) Color.White else Color.White.copy(alpha = 0.4f)
                            )
                    )
                }
            }
        }
    }
}

@Composable
private fun BannerCard(
    banner: CricketBanner,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF040B16)), // Deep Navy
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            
            // Subtle action background (Left)
            AsyncImage(
                model = banner.backgroundUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.6f)
                    .align(Alignment.CenterStart)
            )

            // Gradient to fade the action background into the dark navy
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF040B16).copy(alpha = 0.2f),
                                Color(0xFF040B16).copy(alpha = 0.8f),
                                Color(0xFF040B16)
                            ),
                            startX = 0f,
                            endX = Float.POSITIVE_INFINITY
                        )
                    )
            )

            // Enlarged subtle portrait behind the main player (Right)
            AsyncImage(
                model = banner.playerImageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alpha = 0.15f,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.6f)
                    .align(Alignment.CenterEnd)
                    .offset(x = 24.dp, y = 16.dp)
            )

            // Dark cinematic overlay over the subtle portrait to blend it
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.6f)
                    .align(Alignment.CenterEnd)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF040B16),
                                Color.Transparent
                            )
                        )
                    )
            )

            // Content Row (Text Left/Center, Main Image Right)
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Text content
                Column(
                    modifier = Modifier
                        .weight(1.3f)
                        .padding(start = 24.dp, end = 8.dp, top = 16.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    val parts = banner.playerName.split(" ")
                    val firstName = parts.firstOrNull() ?: ""
                    val lastName = parts.drop(1).joinToString(" ")
                    
                    if (firstName.isNotEmpty() && lastName.isNotEmpty()) {
                        Text(
                            text = firstName.uppercase(),
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White.copy(alpha = 0.9f),
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = lastName.uppercase(),
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.5.sp
                        )
                    } else if (firstName.isNotEmpty() && lastName.isEmpty()){
                         Text(
                            text = firstName.uppercase(),
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.5.sp
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = banner.tagline.uppercase(),
                        style = MaterialTheme.typography.labelLarge,
                        color = Color(0xFF38BDF8),
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }
                
                // Main Player Portrait (Foreground Right)
                AsyncImage(
                    model = banner.playerImageUrl,
                    contentDescription = banner.playerName,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(end = 16.dp, top = 16.dp)
                )
            }
            
            // Premium edge glow / border
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = 1.dp,
                        color = Color(0xFF38BDF8).copy(alpha = 0.25f),
                        shape = RoundedCornerShape(16.dp)
                    )
            )
        }
    }
}

