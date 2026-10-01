package com.k9t.scoreknight.presentation.landing

import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScoreKnightLandingScreen(
    onNavigateToCricket: () -> Unit,
    onNavigateToFootball: () -> Unit
) {
    val backgroundColor = Color(0xFF2C3E2D)
    val accentColor = Color(0xFFC7F465)

    // Infinite pager
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { 2 }
    )

    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    // Auto-scroll logic
    LaunchedEffect(isDragged) {
        if (!isDragged) {
            while (true) {
                delay(2000)
                if (!pagerState.isScrollInProgress) {
                    pagerState.animateScrollToPage(
                        page = if (pagerState.currentPage == 0) 1 else 0,
                        animationSpec = tween(durationMillis = 800)
                    )
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        // Decorative background text
        Text(
            text = "More\nThan\nA Game",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Normal,
                fontSize = 48.sp,
                lineHeight = 48.sp
            ),
            color = accentColor.copy(alpha = 0.15f),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 160.dp, end = 24.dp)
                .rotate(-15f)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 48.dp)
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Score",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White
                        )
                        Text(
                            text = "Knight",
                            style = MaterialTheme.typography.titleLarge,
                            color = accentColor
                        )
                    }
                    Text(
                        text = "LIVE BEYOND THE GAME",
                        style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 2.sp),
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }

                Surface(
                    shape = CircleShape,
                    color = Color.Transparent,
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f)),
                    modifier = Modifier.size(48.dp)
                ) {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Welcome Text
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Text(
                    text = "Hey,\nWelcome!",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = 42.sp,
                        lineHeight = 48.sp
                    ),
                    color = accentColor
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Your home for live scores,\nnews and more.",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                    color = Color.White.copy(alpha = 0.9f)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Carousel
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 64.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(360.dp)
            ) { page ->
                val actualPage = page % 2
                
                val pageOffset = (
                    (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                ).absoluteValue

                val scale = 1f - (pageOffset.coerceIn(0f, 1f) * 0.15f)
                val alpha = 1f - (pageOffset.coerceIn(0f, 1f) * 0.4f)
                val isCenter = pageOffset < 0.5f

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            this.alpha = alpha
                        }
                ) {
                    if (actualPage == 0) {
                        SportCard(
                            sportName = "CRICKET",
                            imageUrl = "https://res.cloudinary.com/dwmsjuuox/image/upload/v1789896012/virat_xxjatv.jpg",
                            isCenter = isCenter,
                            accentColor = accentColor,
                            onClick = onNavigateToCricket
                        )
                    } else {
                        SportCard(
                            sportName = "FOOTBALL",
                            imageUrl = "https://images.unsplash.com/photo-1579952363873-27f3bade9f55?q=80&w=600&auto=format&fit=crop",
                            isCenter = isCenter,
                            accentColor = accentColor,
                            onClick = onNavigateToFootball
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Indicators
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                val actualPage = pagerState.currentPage % 2
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(if (actualPage == 0) accentColor else Color.Gray.copy(alpha = 0.5f))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(if (actualPage == 1) accentColor else Color.Gray.copy(alpha = 0.5f))
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Bottom Footer
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 48.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(modifier = Modifier.weight(1f).height(1.dp).background(Color.White.copy(alpha = 0.2f)))
                Text(
                    text = "DIFFERENT GAMES\nSAME PASSION",
                    style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 2.sp),
                    color = Color.White.copy(alpha = 0.5f),
                    modifier = Modifier.padding(horizontal = 16.dp),
                    textAlign = TextAlign.Center
                )
                Box(modifier = Modifier.weight(1f).height(1.dp).background(Color.White.copy(alpha = 0.2f)))
            }
        }
    }
}

@Composable
fun SportCard(
    sportName: String,
    imageUrl: String,
    isCenter: Boolean,
    accentColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isCenter) 16.dp else 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = sportName,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.8f)
                            ),
                            startY = 200f
                        )
                    )
            )

            // Border for center card
            if (isCenter) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                        .border(
                            width = 2.dp,
                            color = Color.White.copy(alpha = 0.8f),
                            shape = RoundedCornerShape(24.dp)
                        )
                )
            }

            // Text content
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(24.dp)
            ) {
                Text(
                    text = "EXPLORE",
                    style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 3.sp),
                    color = Color.White.copy(alpha = 0.8f)
                )
                Text(
                    text = sportName,
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Live Scores • News • Stats • More",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .width(32.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(accentColor)
                )
            }
        }
    }
}
