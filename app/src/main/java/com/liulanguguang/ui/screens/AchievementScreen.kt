package com.liulanguguang.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.liulanguguang.data.UserDataStore
import com.liulanguguang.data.model.AchievementData
import com.liulanguguang.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AchievementScreen(
    userDataStore: UserDataStore,
    onBack: () -> Unit
) {
    val unlockedAchievements by userDataStore.unlockedAchievements.collectAsState()
    val coins by userDataStore.coins.collectAsState()
    val experiencedAds by userDataStore.experiencedAds.collectAsState()
    val trapsTriggered by userDataStore.trapsTriggered.collectAsState()
    val popupsClosed by userDataStore.popupsClosed.collectAsState()
    val fakeAdsSpotted by userDataStore.fakeAdsSpotted.collectAsState()

    val achievements = AchievementData.achievements
    val unlockedCount = unlockedAchievements.size

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "🏆 成就中心",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "已解锁 $unlockedCount/${achievements.size}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "返回",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                StatsCard(
                    coins = coins,
                    experiencedAds = experiencedAds.size,
                    trapsTriggered = trapsTriggered,
                    popupsClosed = popupsClosed,
                    fakeAdsSpotted = fakeAdsSpotted
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                Text(
                    text = "成就列表",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            items(achievements) { achievement ->
                val isUnlocked = unlockedAchievements.contains(achievement.id)
                AchievementCard(
                    achievement = achievement,
                    isUnlocked = isUnlocked,
                    onUnlock = {
                        userDataStore.unlockAchievement(achievement.id)
                        userDataStore.addCoins(achievement.coinReward)
                    },
                    currentProgress = when (achievement.id) {
                        "ad_collector" -> experiencedAds.size
                        "trap_victim" -> trapsTriggered
                        "popup_master" -> popupsClosed
                        "fake_detective" -> fakeAdsSpotted
                        else -> 0
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
fun StatsCard(
    coins: Int,
    experiencedAds: Int,
    trapsTriggered: Int,
    popupsClosed: Int,
    fakeAdsSpotted: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "📊 统计",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(
                    icon = "💰",
                    value = coins.toString(),
                    label = "广告币"
                )
                StatItem(
                    icon = "📚",
                    value = "$experiencedAds/32",
                    label = "体验广告"
                )
                StatItem(
                    icon = "😅",
                    value = trapsTriggered.toString(),
                    label = "被坑次数"
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(
                    icon = "🎯",
                    value = popupsClosed.toString(),
                    label = "关闭弹窗"
                )
                StatItem(
                    icon = "👁️",
                    value = fakeAdsSpotted.toString(),
                    label = "识破伪装"
                )
                StatItem(
                    icon = "⚡",
                    value = "${fakeAdsSpotted * 10}%",
                    label = "防坑率"
                )
            }
        }
    }
}

@Composable
fun StatItem(
    icon: String,
    value: String,
    label: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = icon, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray
        )
    }
}

@Composable
fun AchievementCard(
    achievement: com.liulanguguang.data.model.Achievement,
    isUnlocked: Boolean,
    onUnlock: () -> Unit,
    currentProgress: Int
) {
    val targetProgress = when (achievement.id) {
        "ad_collector" -> 32
        "trap_victim" -> 10
        "popup_master" -> 5
        "fake_detective" -> 10
        else -> 1
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isUnlocked)
                DeceptiveGreen.copy(alpha = 0.1f)
            else
                MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(
                        if (isUnlocked) DeceptiveGreen else Color.Gray.copy(alpha = 0.3f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = achievement.icon,
                    fontSize = 28.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = achievement.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isUnlocked) DeceptiveGreen else MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = achievement.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "奖励: ",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                    Text(
                        text = "💰 ${achievement.coinReward}",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = DeceptiveGreen
                    )
                }

                if (!isUnlocked && currentProgress > 0) {
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { (currentProgress.toFloat() / targetProgress).coerceAtMost(1f) },
                        modifier = Modifier.fillMaxWidth(),
                        color = WarningOrange,
                    )
                    Text(
                        text = "进度: $currentProgress/$targetProgress",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            if (isUnlocked) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = "已解锁",
                    tint = DeceptiveGreen,
                    modifier = Modifier.size(32.dp)
                )
            } else {
                Icon(
                    Icons.Default.Lock,
                    contentDescription = "未解锁",
                    tint = Color.Gray,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}
