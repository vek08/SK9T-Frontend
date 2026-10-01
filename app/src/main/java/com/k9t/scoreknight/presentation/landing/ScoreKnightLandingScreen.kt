package com.k9t.scoreknight.presentation.landing

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay

@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@androidx.compose.material3.ExperimentalMaterial3Api
@Composable
fun ScoreKnightLandingScreen(
    onNavigateToCricket: () -> Unit,
    onNavigateToFootball: () -> Unit
) {
    val darkOlive = Color(0xFF2C3E25)
    val deepForest = Color(0xFF192515)
    val limeAccent = Color(0xFFC0F050)
    val textLight = Color(0xFFE5EFE2)

    val pagerState = rememberPagerState(
        pageCount = { 2 }
    )

    // Auto-scroll logic
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            delay(2000L)
            val nextPage = if (pagerState.currentPage == 0) 1 else 0
            pagerState.animateScrollToPage(
                page = nextPage,
                animationSpec = tween(durationMillis = 800)
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(deepForest)
            .drawBehind {
                // Subtle curved abstract shapes in background
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(darkOlive, Color.Transparent),
                        center = Offset(size.width * 1.2f, size.height * -0.1f),
                        radius = size.width * 0.9f
                    )
                )
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(darkOlive.copy(alpha = 0.5f), Color.Transparent),
                        center = Offset(size.width * -0.2f, size.height * 0.7f),
                        radius = size.width * 0.8f
                    )
                )
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(limeAccent.copy(alpha = 0.05f), Color.Transparent),
                        center = Offset(size.width * 0.5f, size.height * 0.5f),
                        radius = size.width * 0.4f
                    )
                )
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 48.dp, bottom = 24.dp)
        ) {
            // Top Bar Area
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = "ScoreKnight",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "LIVE BEYOND THE GAME",
                        color = limeAccent,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 2.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .border(1.dp, Color.White.copy(alpha = 0.3f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Menu,
                        contentDescription = "Menu",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Welcome Text
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Column {
                    Text(
                        text = "Hey,\nWelcome!",
                        color = Color.White,
                        fontSize = 44.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 48.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Your home for live scores,\nnews and more.",
                        color = textLight.copy(alpha = 0.8f),
                        fontSize = 16.sp,
                        lineHeight = 22.sp
                    )
                }

                // Decorative text
                Text(
                    text = "More\nThan\nA Game",
                    color = limeAccent.copy(alpha = 0.25f),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Italic,
                    fontFamily = FontFamily.Cursive,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .rotate(-15f)
                        .padding(end = 16.dp, top = 8.dp),
                    lineHeight = 32.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Carousel
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 48.dp),
                pageSpacing = 16.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp)
            ) { page ->
                val isCricket = (page % 2) == 0
                
                // Calculate page offset for animations
                val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                val absoluteOffset = Math.abs(pageOffset)
                
                val scale by animateFloatAsState(
                    targetValue = 1f - (absoluteOffset * 0.15f).coerceIn(0f, 0.15f),
                    animationSpec = tween(300)
                )
                
                val alpha by animateFloatAsState(
                    targetValue = 1f - (absoluteOffset * 0.5f).coerceIn(0f, 0.5f),
                    animationSpec = tween(300)
                )

                SportCard(
                    isCricket = isCricket,
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            this.alpha = alpha
                        }
                        .clickable {
                            if (isCricket) onNavigateToCricket() else onNavigateToFootball()
                        }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Indicators
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val isCricketActive = (pagerState.currentPage % 2) == 0
                
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(if (isCricketActive) limeAccent else Color.White.copy(alpha = 0.3f))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(if (!isCricketActive) limeAccent else Color.White.copy(alpha = 0.3f))
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Bottom Footer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(1.dp)
                        .background(limeAccent.copy(alpha = 0.3f))
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "DIFFERENT GAMES",
                        color = textLight.copy(alpha = 0.6f),
                        fontSize = 10.sp,
                        letterSpacing = 2.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "SAME PASSION",
                        color = limeAccent.copy(alpha = 0.6f),
                        fontSize = 10.sp,
                        letterSpacing = 2.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(1.dp)
                        .background(limeAccent.copy(alpha = 0.3f))
                )
            }
        }
    }
}

@Composable
private fun SportCard(
    isCricket: Boolean,
    modifier: Modifier = Modifier
) {
    val title = if (isCricket) "CRICKET" else "FOOTBALL"
    // Note: using placeholder images matching the sport requirement
    val imageUrl = if (isCricket) 
        "https://res.cloudinary.com/dwmsjuuox/image/upload/v1790890458/ind_fnmklb.jpg"
    else 
        "https://res.cloudinary.com/dwmsjuuox/image/upload/v1790890459/football_f5uadf.avif"

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(32.dp))
            .border(2.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(32.dp))
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        
        // Gradient overlay for text readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.3f),
                            Color.Black.copy(alpha = 0.9f)
                        ),
                        startY = 300f
                    )
                )
        )
        
        // Content
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(24.dp)
        ) {
            Text(
                text = "EXPLORE",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 12.sp,
                letterSpacing = 2.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = title,
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Live Scores • News • Stats • More",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 10.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Small lime accent line
            Box(
                modifier = Modifier
                    .width(24.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color(0xFFC0F050))
            )
        }
    }
}
