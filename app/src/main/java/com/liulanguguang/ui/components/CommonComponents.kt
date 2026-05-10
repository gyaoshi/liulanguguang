package com.liulanguguang.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.liulanguguang.data.model.AdType
import com.liulanguguang.data.model.AdCategory
import com.liulanguguang.ui.theme.*

@Composable
fun PatienceBar(
    patience: Int,
    modifier: Modifier = Modifier
) {
    val animatedPatience by animateFloatAsState(
        targetValue = patience.toFloat(),
        animationSpec = tween(durationMillis = 500),
        label = "patience"
    )

    val progressColor = when {
        animatedPatience > 66 -> CategoryWeak
        animatedPatience > 33 -> CategoryMedium
        else -> CategoryStrong
    }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "广告忍耐度",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Text(
                text = "${patience}%",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = progressColor
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.Gray.copy(alpha = 0.3f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(animatedPatience / 100f)
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(progressColor, progressColor.copy(alpha = 0.7f))
                        )
                    )
            )
        }
    }
}

@Composable
fun CoinDisplay(
    coins: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(DeceptiveGreen.copy(alpha = 0.2f))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "💰", fontSize = 16.sp)
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "$coins",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = DeceptiveGreen
        )
    }
}

@Composable
fun AdCard(
    ad: AdType,
    experienceCount: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val categoryColor = when (ad.category) {
        AdCategory.STRONG -> CategoryStrong
        AdCategory.MEDIUM -> CategoryMedium
        AdCategory.WEAK -> CategoryWeak
        AdCategory.SYSTEM -> CategorySystem
        AdCategory.SPECIAL -> CategorySpecial
        AdCategory.OTHER -> CategoryOther
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(categoryColor.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = ad.icon, fontSize = 24.sp)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = ad.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(categoryColor)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = ad.category.emoji,
                            fontSize = 10.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = ad.sarcasm,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(
                        text = "-${ad.patienceCost}% 💪",
                        style = MaterialTheme.typography.labelSmall,
                        color = CategoryStrong.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "+${ad.coinReward} 💰",
                        style = MaterialTheme.typography.labelSmall,
                        color = DeceptiveGreen.copy(alpha = 0.8f)
                    )
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "已体验", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                Text(text = "$experienceCount", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = categoryColor)
            }
        }
    }
}

@Composable
fun CategoryChip(
    category: AdCategory?,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val text = category?.let { "${it.emoji} ${it.displayName}" } ?: "全部"
    val backgroundColor = if (isSelected) WarningOrange else MaterialTheme.colorScheme.surfaceVariant
    val textColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface

    Surface(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelMedium,
            color = textColor
        )
    }
}
