package com.liulanguguang.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.compose.ui.window.Dialog
import com.liulanguguang.data.UserDataStore
import com.liulanguguang.data.model.AdData
import com.liulanguguang.data.model.AdType
import com.liulanguguang.ui.theme.*
import kotlinx.coroutines.delay
import kotlin.math.roundToInt
import kotlin.random.Random

@Composable
fun AdExperienceScreen(
    adId: Int,
    userDataStore: UserDataStore,
    onBack: () -> Unit
) {
    val ad = remember { AdData.getById(adId) }
    var showAdContent by remember { mutableStateOf(true) }
    var showSarcasm by remember { mutableStateOf(false) }
    val coins by userDataStore.coins.collectAsState()
    val patience by userDataStore.patience.collectAsState()

    if (ad == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("广告不存在", fontSize = 20.sp)
        }
        return
    }

    LaunchedEffect(Unit) {
        userDataStore.addExperiencedAd(adId.toString())
        userDataStore.deductPatience(ad.patienceCost)
        userDataStore.addCoins(ad.coinReward)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (adId) {
            1 -> SplashAdExperience(ad, userDataStore, onBack)
            2 -> InterstitialAdExperience(ad, userDataStore, onBack)
            3 -> FullScreenVideoAdExperience(ad, userDataStore, onBack)
            4 -> FloatingWindowAdExperience(ad, userDataStore, onBack)
            5 -> PopupAdExperience(ad, userDataStore, onBack)
            6 -> BannerAdExperience(ad, userDataStore, onBack)
            7 -> InFeedAdExperience(ad, userDataStore, onBack)
            8 -> NativeAdExperience(ad, userDataStore, onBack)
            9 -> SearchResultAdExperience(ad, userDataStore, onBack)
            10 -> RecommendationAdExperience(ad, userDataStore, onBack)
            11 -> PreRollAdExperience(ad, userDataStore, onBack)
            12 -> RewardedVideoAdExperience(ad, userDataStore, onBack)
            13 -> OfferWallAdExperience(ad, userDataStore, onBack)
            14 -> PlayableAdExperience(ad, userDataStore, onBack)
            15 -> ContentMarketingAdExperience(ad, userDataStore, onBack)
            16 -> CommentAdExperience(ad, userDataStore, onBack)
            17 -> ProfileAdExperience(ad, userDataStore, onBack)
            18 -> NotificationAdExperience(ad, userDataStore, onBack)
            19 -> LockScreenAdExperience(ad, userDataStore, onBack)
            20 -> IconBadgeAdExperience(ad, userDataStore, onBack)
            21 -> FakeUpdateAdExperience(ad, userDataStore, onBack)
            22 -> FakeSystemAdExperience(ad, userDataStore, onBack)
            23 -> ShakeAdExperience(ad, userDataStore, onBack)
            24 -> SlideAdExperience(ad, userDataStore, onBack)
            25 -> VoiceAdExperience(ad, userDataStore, onBack)
            26 -> ARAdExperience(ad, userDataStore, onBack)
            27 -> DanmuAdExperience(ad, userDataStore, onBack)
            28 -> StickerAdExperience(ad, userDataStore, onBack)
            29 -> PaymentAdExperience(ad, userDataStore, onBack)
            30 -> SharePageAdExperience(ad, userDataStore, onBack)
            31 -> ExitIntentAdExperience(ad, userDataStore, onBack)
            32 -> EmptyStateAdExperience(ad, userDataStore, onBack)
            else -> DefaultAdExperience(ad, userDataStore, onBack)
        }
    }
}

@Composable
fun DefaultAdExperience(ad: AdType, userDataStore: UserDataStore, onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = ad.icon, fontSize = 80.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = ad.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = ad.sarcasm, fontSize = 18.sp, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = onBack) {
                Text("返回")
            }
        }
    }
}

@Composable
fun SplashAdExperience(ad: AdType, userDataStore: UserDataStore, onBack: () -> Unit) {
    var countdown by remember { mutableIntStateOf(5) }
    var showSkip by remember { mutableStateOf(false) }
    var triggered by remember { mutableStateOf(false) }
    val infiniteTransition = rememberInfiniteTransition(label = "shake")
    val rotation by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(100, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rotation"
    )

    LaunchedEffect(Unit) {
        for (i in 5 downTo 0) {
            countdown = i
            if (i == 0) showSkip = true
            delay(1000)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFFF6B6B), Color(0xFFFF8E53))
                )
            )
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, _ ->
                    if (!triggered) {
                        triggered = true
                    }
                }
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "📱", fontSize = 100.sp, modifier = Modifier.rotate(rotation))
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "正在加载中...",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "请稍候，您的体验即将开始",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = { countdown / 5f },
                    modifier = Modifier.size(80.dp),
                    color = Color.White,
                    strokeWidth = 6.dp
                )
                Text(
                    text = "$countdown",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (triggered) {
                Text(
                    text = "⚠️ 检测到摇晃！正在跳转...",
                    fontSize = 14.sp,
                    color = Color.Yellow
                )
            } else {
                Text(
                    text = "💡 提示：摇晃手机可以跳转哦~",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        }

        AnimatedVisibility(
            visible = showSkip,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.3f))
            ) {
                Text("跳过广告 ${ad.sarcasm}", color = Color.White)
            }
        }
    }
}

@Composable
fun InterstitialAdExperience(ad: AdType, userDataStore: UserDataStore, onBack: () -> Unit) {
    var clicks by remember { mutableIntStateOf(0) }
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.7f))
            .clickable {
                clicks++
                if (clicks >= 3) {
                    userDataStore.incrementTrapsTriggered()
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .size((200 * scale).dp)
                .clickable { clicks++ },
            colors = CardDefaults.cardColors(containerColor = WarningOrange)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "🖼️", fontSize = 60.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "点击领取大奖！",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "（骗你的）",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        }

        Text(
            text = "你点击了 $clicks 次",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 100.dp),
            color = Color.White,
            fontSize = 14.sp
        )

        AnimatedVisibility(
            visible = clicks >= 3,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "恭喜你成功避开了陷阱！",
                    color = DeceptiveGreen,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onBack) {
                    Text("返回")
                }
            }
        }
    }
}

@Composable
fun FullScreenVideoAdExperience(ad: AdType, userDataStore: UserDataStore, onBack: () -> Unit) {
    var countdown by remember { mutableIntStateOf(15) }
    var showSkip by remember { mutableStateOf(false) }
    var watched by remember { mutableStateOf(false) }
    var startTime by remember { mutableLongStateOf(0L) }

    LaunchedEffect(Unit) {
        startTime = System.currentTimeMillis()
        for (i in 15 downTo 0) {
            countdown = i
            if (i == 0) {
                showSkip = true
                watched = true
                val elapsed = (System.currentTimeMillis() - startTime) / 1000
                if (elapsed <= 3) {
                    userDataStore.incrementQuickSkips()
                }
            }
            delay(1000)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "🎬", fontSize = 80.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "正在播放广告视频...",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "剩余时间: ${countdown}s",
                fontSize = 18.sp,
                color = Color.Gray
            )
            if (watched) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "👍 感谢观看！",
                    fontSize = 16.sp,
                    color = DeceptiveGreen
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .size(60.dp, 30.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.Red),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "广告", color = Color.White, fontSize = 12.sp)
        }

        AnimatedVisibility(
            visible = showSkip,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Button(onClick = onBack) {
                Text("跳过广告 ▶")
            }
        }
    }
}

@Composable
fun FloatingWindowAdExperience(ad: AdType, userDataStore: UserDataStore, onBack: () -> Unit) {
    var offsetX by remember { mutableFloatStateOf(100f) }
    var offsetY by remember { mutableFloatStateOf(300f) }
    val density = LocalDensity.current
    var attempts by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "📱 主界面内容", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "这是一段正常的内容...\n看起来没有任何问题...\n等等，那个悬浮窗是什么？！",
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "💡 尝试把悬浮窗拖到关闭区域", fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "点击次数: $attempts", fontSize = 14.sp, color = Color.Gray)
        }

        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { change, dragAmount ->
                        change.consume()
                        offsetX = (offsetX + dragAmount).coerceIn(0f, size.width - 80f)
                        offsetY = (offsetY).coerceIn(0f, size.height - 80f)
                    }
                }
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(TemptingBlue)
                    .clickable { attempts++ },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "🎈", fontSize = 24.sp)
                    Box(
                        modifier = Modifier
                            .size(12.dp, 12.dp)
                            .offset(x = 20.dp, y = (-10).dp)
                            .clip(CircleShape)
                            .background(PopupRed)
                            .clickable {
                                userDataStore.incrementTrapsTriggered()
                                attempts++
                            }
                    ) {}
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .size(100.dp, 50.dp)
                .clip(RoundedCornerShape(25.dp))
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(DeceptiveGreen.copy(alpha = 0.3f), DeceptiveGreen.copy(alpha = 0.8f))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "关闭区域",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }

        if (attempts >= 5) {
            Dialog(onDismissRequest = {}) {
                Card {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "🎉", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "恭喜你成功关掉了\n（其实并没有）",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onBack) {
                            Text("返回")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PopupAdExperience(ad: AdType, userDataStore: UserDataStore, onBack: () -> Unit) {
    var selectedButton by remember { mutableStateOf<String?>(null) }
    val infiniteTransition = rememberInfiniteTransition(label = "shake")
    val offsetX by infiniteTransition.animateFloat(
        initialValue = -3f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(50),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offset"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
    ) {
        Dialog(onDismissRequest = {}) {
            Card(
                modifier = Modifier
                    .offset(x = offsetX.dp)
                    .width(300.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "🎊", fontSize = 48.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "恭喜你被选中！",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = WarningOrange
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "点击下方按钮领取神秘大奖",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            selectedButton = "claim"
                            userDataStore.incrementTrapsTriggered()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedButton == "claim") PopupRed else DeceptiveGreen
                        )
                    ) {
                        Text(if (selectedButton == "claim") "已领取！" else "立即领取")
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    TextButton(
                        onClick = {
                            selectedButton = "close"
                            userDataStore.incrementPopupsClosed()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "残忍拒绝",
                            color = if (selectedButton == "close") DeceptiveGreen else Color.Gray
                        )
                    }

                    if (selectedButton == "close") {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "💡 找到了关闭按钮！",
                            color = DeceptiveGreen,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = onBack) {
                            Text("返回")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BannerAdExperience(ad: AdType, userDataStore: UserDataStore, onBack: () -> Unit) {
    var showBanner by remember { mutableStateOf(true) }
    var attempts by remember { mutableIntStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Text(text = "📰 新闻标题", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "这是一条非常重要的新闻内容...\n绝对不是在凑字数...",
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "🔴 这条新闻刚刚更新！", fontSize = 14.sp, color = PopupRed)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "你以为这是新闻？\n其实这是一条广告！",
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        AnimatedVisibility(
            visible = showBanner,
            modifier = Modifier.align(Alignment.BottomCenter),
            enter = slideInVertically { it } + fadeIn(),
            exit = slideOutVertically { it } + fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(WarningOrange, TemptingBlue)
                        )
                    )
                    .clickable {
                        attempts++
                        userDataStore.incrementTrapsTriggered()
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "🔥 限时优惠！点击领取优惠券！",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "广告",
                        fontSize = 10.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            if (showBanner) {
                TextButton(onClick = {
                    showBanner = false
                    userDataStore.incrementFakeAdsSpotted()
                }) {
                    Text("假装没看到")
                }
            }
            Button(onClick = onBack) {
                Text("返回")
            }
        }
    }
}

@Composable
fun InFeedAdExperience(ad: AdType, userDataStore: UserDataStore, onBack: () -> Unit) {
    var identifiedFake by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = "📱 推荐流", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            listOf(
                Triple("这是真实新闻1", "刚刚", false),
                Triple("震惊！这个秘密你一定不知道！", "5分钟前", true),
                Triple("这是真实新闻2", "10分钟前", false),
                Triple("必看！错过后悔一辈子！", "15分钟前", true),
                Triple("这是真实新闻3", "20分钟前", false),
                Triple("紧急通知！所有人都在转！", "25分钟前", true)
            ).forEach { (title, time, isAd) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            if (isAd) {
                                identifiedFake++
                                userDataStore.incrementFakeAdsSpotted()
                            }
                        }
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = title, fontWeight = FontWeight.Bold)
                            if (isAd) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "广告",
                                    fontSize = 10.sp,
                                    color = Color.Gray,
                                    modifier = Modifier
                                        .background(Color.Gray.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                                        .padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(text = time, fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "识别广告: $identifiedFake", fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onBack) {
                Text("返回")
            }
        }
    }
}

@Composable
fun NativeAdExperience(ad: AdType, userDataStore: UserDataStore, onBack: () -> Unit) {
    var showAd by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(2000)
        showAd = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "个人中心", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(Color.Gray),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "👤", fontSize = 30.sp)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(text = "用户名", fontWeight = FontWeight.Bold)
                            Text(text = "编辑个人资料", color = Color.Gray, fontSize = 14.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "设置", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            listOf("账号安全", "隐私设置", "通知设置").forEach {
                Text(
                    text = it,
                    modifier = Modifier.padding(vertical = 12.dp),
                    fontSize = 16.sp
                )
            }

            AnimatedVisibility(
                visible = showAd,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .clickable {
                            userDataStore.incrementFakeAdsSpotted()
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "💡", fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "开通VIP会员", fontWeight = FontWeight.Bold)
                            Text(
                                text = "解锁全部功能",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                        Text(
                            text = "广告",
                            fontSize = 10.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Button(onClick = onBack) {
                Text("返回")
            }
        }
    }
}

@Composable
fun SearchResultAdExperience(ad: AdType, userDataStore: UserDataStore, onBack: () -> Unit) {
    var selectedAd by remember { mutableIntStateOf(-1) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "搜索结果", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "\"手机\" 约 100,000,000 条结果", color = Color.Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(16.dp))

            listOf(
                Pair("官方正品手机旗舰店", true),
                Pair("全网最低价手机特卖", true),
                Pair("XX手机官网", false),
                Pair("手机维修回收服务", false),
                Pair("最新款智能手机推荐", true)
            ).forEachIndexed { index, (title, isAd) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            if (isAd) {
                                selectedAd = index
                                userDataStore.incrementFakeAdsSpotted()
                            }
                        }
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = title, fontWeight = FontWeight.Bold)
                            if (isAd) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "广告",
                                    fontSize = 10.sp,
                                    color = PopupRed,
                                    modifier = Modifier
                                        .background(PopupRed.copy(alpha = 0.1f), RoundedCornerShape(4.dp))
                                        .padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                            }
                        }
                        if (isAd) {
                            Text(text = "广告商的描述...", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            if (selectedAd >= 0) {
                Text(
                    text = "✓ 你识别出了广告！",
                    color = DeceptiveGreen,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Button(onClick = onBack) {
                Text("返回")
            }
        }
    }
}

@Composable
fun RecommendationAdExperience(ad: AdType, userDataStore: UserDataStore, onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = "💡 为你推荐", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { userDataStore.incrementTrapsTriggered() }
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .background(Color.Gray.copy(alpha = 0.3f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "🖼️", fontSize = 40.sp)
                        }
                        Text(text = "推荐商品1", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text(text = "¥99", fontSize = 12.sp, color = PopupRed)
                        Text(text = "广告", fontSize = 10.sp, color = Color.Gray)
                    }
                }

                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { userDataStore.incrementTrapsTriggered() }
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .background(Color.Gray.copy(alpha = 0.3f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "🖼️", fontSize = 40.sp)
                        }
                        Text(text = "推荐商品2", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text(text = "¥199", fontSize = 12.sp, color = PopupRed)
                        Text(text = "广告", fontSize = 10.sp, color = Color.Gray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "🔍 提示：这些推荐大多是广告！", fontSize = 14.sp, color = Color.Gray)
        }

        Button(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text("返回")
        }
    }
}

@Composable
fun PreRollAdExperience(ad: AdType, userDataStore: UserDataStore, onBack: () -> Unit) {
    var countdown by remember { mutableIntStateOf(60) }
    var showSkip by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        for (i in 60 downTo 0) {
            countdown = i
            if (i == 0) showSkip = true
            delay(1000)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "📺", fontSize = 80.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "视频将在广告后开始",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "VIP会员可跳过广告",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "${countdown / 60}:${String.format("%02d", countdown % 60)}",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .size(60.dp, 30.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.Red),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "广告", color = Color.White, fontSize = 12.sp)
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 60.dp)
        ) {
            LinearProgressIndicator(
                progress = { countdown / 60f },
                modifier = Modifier.width(200.dp),
                color = Color.White,
                trackColor = Color.Gray
            )
        }

        AnimatedVisibility(
            visible = showSkip,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Button(onClick = onBack) {
                Text("跳过广告")
            }
        }
    }
}

@Composable
fun RewardedVideoAdExperience(ad: AdType, userDataStore: UserDataStore, onBack: () -> Unit) {
    var isWatching by remember { mutableStateOf(false) }
    var countdown by remember { mutableIntStateOf(30) }
    var reward by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "🎁", fontSize = 80.sp)
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "观看视频获得奖励",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "奖励: +10 游戏金币",
                fontSize = 18.sp,
                color = DeceptiveGreen
            )
            Spacer(modifier = Modifier.height(32.dp))

            if (!isWatching && !reward) {
                Button(
                    onClick = {
                        isWatching = true
                        kotlinx.coroutines.MainScope().launch {
                            for (i in 30 downTo 0) {
                                countdown = i
                                delay(1000)
                            }
                            isWatching = false
                            reward = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("观看视频广告")
                }
            }

            if (isWatching) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "剩余 ${countdown}s", fontSize = 18.sp)
                    Text(
                        text = "坚持就是胜利！",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            if (reward) {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = DeceptiveGreen.copy(alpha = 0.2f))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "🎉", fontSize = 40.sp)
                        Text(
                            text = "获得奖励！",
                            fontWeight = FontWeight.Bold,
                            color = DeceptiveGreen
                        )
                        Text(
                            text = "（其实只是虚拟金币）",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }

        Button(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text("返回")
        }
    }
}

@Composable
fun OfferWallAdExperience(ad: AdType, userDataStore: UserDataStore, onBack: () -> Unit) {
    var completedTasks by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "🏆 积分墙", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "完成任务获得积分", fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))

            listOf(
                Triple("下载并注册APP", "+50积分", 1),
                Triple("关注公众号", "+30积分", 2),
                Triple("观看视频3分钟", "+20积分", 3),
                Triple("分享到朋友圈", "+10积分", 4),
                Triple("填写问卷调查", "+40积分", 5)
            ).forEach { (task, reward, id) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = task, fontWeight = FontWeight.Bold)
                            Text(text = reward, fontSize = 12.sp, color = DeceptiveGreen)
                        }
                        Button(
                            onClick = {
                                completedTasks++
                                userDataStore.addCoins(when (id) {
                                    1 -> 50; 2 -> 30; 3 -> 20; 4 -> 10; 5 -> 40
                                    else -> 0
                                })
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (completedTasks >= id) DeceptiveGreen else WarningOrange
                            )
                        ) {
                            Text(if (completedTasks >= id) "已完成" else "去做任务")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "⚠️ 警告：这些任务通常需要授权个人信息",
                fontSize = 14.sp,
                color = PopupRed
            )
        }

        Button(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text("返回")
        }
    }
}

@Composable
fun PlayableAdExperience(ad: AdType, userDataStore: UserDataStore, onBack: () -> Unit) {
    var score by remember { mutableIntStateOf(0) }
    var gameOver by remember { mutableStateOf(false) }
    var tapCount by remember { mutableIntStateOf(0) }
    val infiniteTransition = rememberInfiniteTransition(label = "tap")
    val targetAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF667eea), Color(0xFF764ba2))
                )
            )
            .clickable {
                if (!gameOver && tapCount < 10) {
                    score += Random.nextInt(10, 50)
                    tapCount++
                    if (tapCount >= 10) {
                        gameOver = true
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "🎮", fontSize = 60.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "试玩广告游戏",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "点击屏幕获得分数！",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "分数: $score",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Yellow
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "剩余次数: ${10 - tapCount}",
                fontSize = 16.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = targetAlpha * 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "点击!", fontSize = 16.sp, color = Color.White)
            }

            if (gameOver) {
                Spacer(modifier = Modifier.height(24.dp))
                Card {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "🎉", fontSize = 40.sp)
                        Text(text = "游戏结束!", fontWeight = FontWeight.Bold)
                        Text(
                            text = "想要继续玩？\n请下载完整版！",
                            textAlign = TextAlign.Center,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onBack) {
                            Text("残忍拒绝")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ContentMarketingAdExperience(ad: AdType, userDataStore: UserDataStore, onBack: () -> Unit) {
    var identifiedSoftAd by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = "📝 测评文章", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "客观评测：这款产品真的好用吗？",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "作为一名专业的测评博主，我最近收到了这款产品...\n" +
                                "说实话，第一眼看到的时候就被它的外观吸引了...\n" +
                                "使用了一个月后，我发现它真的改变了我对这类产品的认知...\n" +
                                "特别是它的XXX功能，真的是业界顶尖...\n" +
                                "如果你问我推不推荐，我会说：强烈推荐！",
                        lineHeight = 24.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "作者：XX测评", color = Color.Gray, fontSize = 12.sp)
                        Text(text = "阅读量：10万+", color = Color.Gray, fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    identifiedSoftAd = true
                    userDataStore.incrementFakeAdsSpotted()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("识别这是软文广告")
            }

            if (identifiedSoftAd) {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = PopupRed.copy(alpha = 0.1f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "🔍 识别成功!",
                            fontWeight = FontWeight.Bold,
                            color = PopupRed
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "软文广告的特征：\n" +
                                    "• 使用\"客观\"、\"真实\"等词汇\n" +
                                    "• 过度夸赞产品优点\n" +
                                    "• 缺少缺点描述\n" +
                                    "• 带有购买链接",
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        Button(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text("返回")
        }
    }
}

@Composable
fun CommentAdExperience(ad: AdType, userDataStore: UserDataStore, onBack: () -> Unit) {
    var identifiedComments by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = "💬 评论区", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            listOf(
                Pair("这个产品真的很好用！", false),
                Pair("已购买，效果不错~", true),
                Pair("质量很好，价格实惠", true),
                Pair("一般般吧", false),
                Pair("强烈推荐！点击购买>>", true),
                Pair("用了三个月了，非常满意", true)
            ).forEach { (comment, isAd) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            if (isAd) {
                                identifiedComments++
                                userDataStore.incrementFakeAdsSpotted()
                            }
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "👤", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = comment)
                            if (isAd) {
                                Text(
                                    text = "这条是广告",
                                    fontSize = 10.sp,
                                    color = PopupRed
                                )
                            }
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "识别广告评论: $identifiedComments", fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onBack) {
                Text("返回")
            }
        }
    }
}

@Composable
fun ProfileAdExperience(ad: AdType, userDataStore: UserDataStore, onBack: () -> Unit) {
    var showAd by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1500)
        showAd = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color.Gray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "👤", fontSize = 40.sp)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "用户名", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Text(text = "Lv.10 会员", color = DeceptiveGreen, fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "设置", fontWeight = FontWeight.Bold)
            listOf("🔔 消息通知", "🔒 隐私设置", "🎨 主题设置").forEach {
                Text(modifier = Modifier.padding(vertical = 12.dp), text = it)
            }

            AnimatedVisibility(
                visible = showAd,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .clickable { userDataStore.incrementTrapsTriggered() },
                    colors = CardDefaults.cardColors(containerColor = WarningOrange.copy(alpha = 0.1f))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "🎁", fontSize = 32.sp)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(text = "限时福利", fontWeight = FontWeight.Bold)
                            Text(text = "开通会员享更多权益", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Button(onClick = onBack) {
                Text("返回")
            }
        }
    }
}

@Composable
fun NotificationAdExperience(ad: AdType, userDataStore: UserDataStore, onBack: () -> Unit) {
    var notifications by remember { mutableStateOf(listOf(
        "🔔 您的账户有新的安全提醒",
        "📢 恭喜！您获得了一张优惠券",
        "💰 您的账户余额已变动"
    )) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE5E5E5))
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(text = "📱 通知栏", fontWeight = FontWeight.Bold)
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                notifications.forEachIndexed { index, notification ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                notifications = notifications.toMutableList().also {
                                    it[index] = notification + " ✓"
                                }
                                userDataStore.incrementTrapsTriggered()
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = notification, modifier = Modifier.weight(1f))
                            Icon(
                                Icons.Default.Circle,
                                contentDescription = null,
                                modifier = Modifier.size(8.dp),
                                tint = PopupRed
                            )
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text(
                text = "⚠️ 通知栏广告无孔不入！",
                fontSize = 14.sp,
                color = PopupRed
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onBack) {
                Text("返回")
            }
        }
    }
}

@Composable
fun LockScreenAdExperience(ad: AdType, userDataStore: UserDataStore, onBack: () -> Unit) {
    var unlocked by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1a1a2e), Color(0xFF16213e))
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Text(text = "12:30", fontSize = 72.sp, fontWeight = FontWeight.Light, color = Color.White)
            Text(text = "2024年5月10日", fontSize = 16.sp, color = Color.White.copy(alpha = 0.7f))

            Spacer(modifier = Modifier.height(80.dp))

            Card(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .clickable {
                        unlocked = true
                    }
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "🔒", fontSize = 24.sp)
                    Text(text = "向上滑动解锁", color = Color.Gray, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .clickable { userDataStore.incrementTrapsTriggered() },
                colors = CardDefaults.cardColors(containerColor = WarningOrange)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "🎁", fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(text = "您有一个未领取的红包！", color = Color.White, fontWeight = FontWeight.Bold)
                        Text(text = "点击领取", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("📷", "📞", "💬").forEach {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = it, fontSize = 24.sp)
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = unlocked,
            modifier = Modifier.align(Alignment.BottomCenter),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "解锁成功！", color = DeceptiveGreen, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onBack) {
                    Text("返回")
                }
            }
        }
    }
}

@Composable
fun IconBadgeAdExperience(ad: AdType, userDataStore: UserDataStore, onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEEEEEE))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = "📱 桌面", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf(
                    Triple("微信", "💬", 99),
                    Triple("QQ", "📱", 0),
                    Triple("淘宝", "🛒", 1)
                ).forEach { (name, icon, badge) ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable {
                            if (badge > 0) userDataStore.incrementTrapsTriggered()
                        }
                    ) {
                        Box {
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color.White),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = icon, fontSize = 30.sp)
                            }
                            if (badge > 0) {
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .offset(x = 4.dp, y = (-4).dp)
                                        .size(if (badge > 9) 20.dp else 16.dp)
                                        .clip(CircleShape)
                                        .background(PopupRed),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = if (badge > 99) "99+" else badge.toString(),
                                        color = Color.White,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = name, fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "🔴 红点综合症", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "未读消息: 99+\n" +
                                "系统通知: 5\n" +
                                "广告推送: ???",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "消除红点是不可能的！",
                        color = PopupRed,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Button(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text("返回")
        }
    }
}

@Composable
fun FakeUpdateAdExperience(ad: AdType, userDataStore: UserDataStore, onBack: () -> Unit) {
    var selectedOption by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "⬆️", fontSize = 48.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "发现新版本！",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(text = "版本 99.99.9999.999", fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "• 新增更多广告\n• 优化用户体验\n• 修复了一些bug",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        selectedOption = "update"
                        userDataStore.incrementTrapsTriggered()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = TemptingBlue)
                ) {
                    Text("立即更新")
                }
                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = {
                        selectedOption = "later"
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("稍后再说")
                }

                if (selectedOption == "update") {
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        colors = CardDefaults.cardColors(containerColor = PopupRed.copy(alpha = 0.1f))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "⚠️ 警告!", color = PopupRed, fontWeight = FontWeight.Bold)
                            Text(
                                text = "这可能是伪装成更新的广告!\n点击后会下载其他APP!",
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                if (selectedOption == "later") {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "✓ 明智的选择!",
                        color = DeceptiveGreen,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = onBack) {
                        Text("返回")
                    }
                }
            }
        }
    }
}

@Composable
fun FakeSystemAdExperience(ad: AdType, userDataStore: UserDataStore, onBack: () -> Unit) {
    var selectedWarning by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A1A))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "📱 设置", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))

            listOf(
                "⚠️ 您的手机存在安全风险！",
                "🔋 电量不足，请立即充电！",
                "📶 检测到未知WiFi网络",
                "🗑️ 内存不足，请清理！"
            ).forEach { warning ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            selectedWarning = warning
                            userDataStore.incrementTrapsTriggered()
                        },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF333333))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = warning, color = Color.White)
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "💡 以上都是伪装成系统提示的广告!",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        AnimatedVisibility(
            visible = selectedWarning != null,
            modifier = Modifier.align(Alignment.BottomCenter),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "🤖 系统提示", fontWeight = FontWeight.Bold)
                        Text(
                            text = "这只是广告，别担心！",
                            color = Color.Gray
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onBack) {
                    Text("返回")
                }
            }
        }
    }
}

@Composable
fun ShakeAdExperience(ad: AdType, userDataStore: UserDataStore, onBack: () -> Unit) {
    var shakeCount by remember { mutableIntStateOf(0) }
    var escaped by remember { mutableStateOf(false) }

    LaunchedEffect(shakeCount) {
        if (shakeCount >= 10 && !escaped) {
            escaped = true
            kotlinx.coroutines.delay(2000)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(WarningOrange, Color(0xFFFF8E53))
                )
            )
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, _ ->
                    if (!escaped) {
                        shakeCount++
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "📳", fontSize = 100.sp)
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "摇一摇，跳转到购物网站！",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "检测到摇晃: $shakeCount/10",
                fontSize = 18.sp,
                color = Color.White.copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "💡 尝试快速摇动手机",
                fontSize = 16.sp,
                color = Color.Yellow
            )
            Text(
                text = "或者...不要摇",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.6f)
            )

            if (escaped) {
                Spacer(modifier = Modifier.height(24.dp))
                Card {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "🎉", fontSize = 40.sp)
                        Text(text = "你被迫跳转了!", fontWeight = FontWeight.Bold)
                        Text(text = "(好讨厌)", color = Color.Gray)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onBack) {
                            Text("返回")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SlideAdExperience(ad: AdType, userDataStore: UserDataStore, onBack: () -> Unit) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    var completed by remember { mutableStateOf(false) }
    val targetX = with(LocalDensity.current) { 200.dp.toPx() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    change.consume()
                    if (!completed) {
                        offsetX = (offsetX + dragAmount).coerceIn(0f, targetX)
                        if (offsetX >= targetX) {
                            completed = true
                        }
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "➡️", fontSize = 48.sp, color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "滑动解锁关闭广告",
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .width(260.dp)
                    .height(60.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color.DarkGray),
                contentAlignment = Alignment.CenterStart
            ) {
                Box(
                    modifier = Modifier
                        .offset(x = offsetX.dp)
                        .padding(4.dp)
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(DeceptiveGreen),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = if (completed) "✓ 关闭成功" else "→ → →",
                        color = Color.White.copy(alpha = 0.5f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            LinearProgressIndicator(
                progress = { offsetX / targetX },
                modifier = Modifier.width(260.dp),
                color = DeceptiveGreen
            )

            if (completed) {
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = onBack) {
                    Text("返回")
                }
            }
        }
    }
}

@Composable
fun VoiceAdExperience(ad: AdType, userDataStore: UserDataStore, onBack: () -> Unit) {
    var isPlaying by remember { mutableStateOf(true) }
    var countdown by remember { mutableIntStateOf(30) }

    LaunchedEffect(Unit) {
        for (i in 30 downTo 0) {
            countdown = i
            delay(1000)
        }
        isPlaying = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(WarningOrange.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(WarningOrange.copy(alpha = 0.4f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "🔊", fontSize = 48.sp)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = if (isPlaying) "正在播放语音广告..." else "播放完成",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "\"限时优惠！全场五折！\n点击下方链接立即购买！\"",
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "剩余: ${countdown}s",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = if (countdown > 10) PopupRed else DeceptiveGreen
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedButton(
                    onClick = { /* 静音 */ },
                    enabled = false
                ) {
                    Icon(Icons.Default.VolumeUp, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("静音")
                }

                Button(
                    onClick = {
                        isPlaying = false
                        countdown = 0
                    },
                    enabled = isPlaying
                ) {
                    Icon(Icons.Default.Close, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("关闭")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "⚠️ 语音广告无法永久关闭",
                fontSize = 14.sp,
                color = Color.Gray
            )

            if (!isPlaying) {
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = onBack) {
                    Text("返回")
                }
            }
        }
    }
}

@Composable
fun ARAdExperience(ad: AdType, userDataStore: UserDataStore, onBack: () -> Unit) {
    var rotation by remember { mutableFloatStateOf(0f) }
    val infiniteTransition = rememberInfiniteTransition(label = "rotate")
    val rotationAnim by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .rotate(rotationAnim)
            ) {
                Text(
                    text = "🕶️",
                    fontSize = 100.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(TemptingBlue)
                        .align(Alignment.TopStart)
                        .clickable { rotation += 90f }
                )
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(DeceptiveGreen)
                        .align(Alignment.TopEnd)
                        .clickable { userDataStore.incrementTrapsTriggered() }
                )
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(WarningOrange)
                        .align(Alignment.BottomStart)
                )
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(PopupRed)
                        .align(Alignment.BottomEnd)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "📱 AR广告体验", color = Color.White, fontWeight = FontWeight.Bold)
            Text(text = "点击屏幕上的物品!", color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
        }

        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "🎮 AR广告无处不在!", fontWeight = FontWeight.Bold)
                Text(text = "通过AR技术，广告更加难以躲避", fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onBack) {
                    Text("退出AR体验")
                }
            }
        }
    }
}

@Composable
fun DanmuAdExperience(ad: AdType, userDataStore: UserDataStore, onBack: () -> Unit) {
    var danmus by remember { mutableStateOf(listOf(
        Pair("第一条弹幕", 0f),
        Pair("哈哈哈", 100f),
        Pair("666", 200f)
    )) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(500)
            val newDanmu = "广告弹幕${Random.nextInt(1000)}"
            val newY = Random.nextFloat() * 400f
            danmus = danmus.map { Pair(it.first, it.second - 5f) }.filter { it.second > -50f } +
                    listOf(Pair(newDanmu, 400f))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "📺", fontSize = 100.sp)
        }

        danmus.forEach { (text, y) ->
            Text(
                text = text,
                color = if (text.contains("广告")) PopupRed else Color.White,
                fontSize = 18.sp,
                modifier = Modifier.offset(y = y.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(16.dp)
        ) {
            Text(
                text = "📺 弹幕广告",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "注意红色的广告弹幕!",
                color = Color.Gray,
                fontSize = 12.sp
            )
        }

        Button(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text("返回")
        }
    }
}

@Composable
fun StickerAdExperience(ad: AdType, userDataStore: UserDataStore, onBack: () -> Unit) {
    var selectedStickers by remember { mutableStateOf(listOf<String>()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "😊 表情包广告", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "点击表情包添加到列表", fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("😀", "😂", "😍", "🥰").forEach { sticker ->
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .clickable {
                                    if (selectedStickers.contains(sticker)) {
                                        selectedStickers = selectedStickers - sticker
                                    } else {
                                        selectedStickers = selectedStickers + sticker
                                    }
                                }
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = sticker, fontSize = 40.sp)
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("🎁", "💰", "🔥", "⭐").forEach { sticker ->
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .clickable {
                                    selectedStickers = selectedStickers + sticker
                                    userDataStore.incrementTrapsTriggered()
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = if (sticker == "🎁" || sticker == "💰")
                                    WarningOrange.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = sticker, fontSize = 40.sp)
                            }
                        }
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "已选择: ", fontSize = 14.sp, color = Color.Gray)
                        selectedStickers.take(6).forEach {
                            Text(text = it, fontSize = 24.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "⚠️ 表情包广告传播性强!",
                fontSize = 14.sp,
                color = PopupRed
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                Text("返回")
            }
        }
    }
}

@Composable
fun PaymentAdExperience(ad: AdType, userDataStore: UserDataStore, onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DeceptiveGreen.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(DeceptiveGreen),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "✓", fontSize = 60.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "支付成功!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = "¥199.00", fontSize = 24.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(48.dp))

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "🎁 为您推荐", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "🛒", fontSize = 32.sp)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "相关商品推荐", fontWeight = FontWeight.Bold)
                            Text(text = "查看更多优惠", fontSize = 12.sp, color = Color.Gray)
                        }
                        Text(text = "广告", fontSize = 10.sp, color = Color.Gray)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { userDataStore.incrementTrapsTriggered() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("点击查看")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "💡 支付完成后也要看广告哦~",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        Button(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text("残忍拒绝")
        }
    }
}

@Composable
fun SharePageAdExperience(ad: AdType, userDataStore: UserDataStore, onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "📤 分享", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "📱 应用名称", fontWeight = FontWeight.Bold)
                    Text(
                        text = "这个应用太棒了！你一定要看看！\n" +
                                "我已经用了3个月了，真的很好用！\n" +
                                "下载地址: www.fake-ad.com",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "分享到", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("💬", "📱", "📧", "🔗").forEach { icon ->
                    Card(
                        modifier = Modifier
                            .size(70.dp)
                            .clickable { userDataStore.incrementTrapsTriggered() },
                        colors = CardDefaults.cardColors(containerColor = Color.LightGray.copy(alpha = 0.3f))
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = icon, fontSize = 32.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = WarningOrange.copy(alpha = 0.1f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "🎁 分享奖励", fontWeight = FontWeight.Bold, color = WarningOrange)
                    Text(
                        text = "分享给好友，双方各获得优惠券！",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "⚠️ 分享也是一种广告传播方式",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                Text("返回")
            }
        }
    }
}

@Composable
fun ExitIntentAdExperience(ad: AdType, userDataStore: UserDataStore, onBack: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    var attempts by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(1000)
        showDialog = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Text(text = "📄 应用内容", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "这是一段很有价值的内容...\n" +
                        "你确定要离开吗？\n" +
                        "后面的内容更精彩哦！",
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "⏳ 停留时间: ${attempts}s", fontSize = 14.sp, color = Color.Gray)

            LaunchedEffect(Unit) {
                while (true) {
                    delay(1000)
                    attempts++
                }
            }
        }

        AnimatedVisibility(
            visible = showDialog,
            modifier = Modifier.align(Alignment.Center),
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut()
        ) {
            Dialog(onDismissRequest = {}) {
                Card {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "👋", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "等等！别走！",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "我们准备了特别优惠给你！\n现在离开太可惜了！",
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = { /* 继续浏览 */ },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("继续浏览")
                        }
                        Spacer(modifier = Modifier.height(8.dp))

                        TextButton(
                            onClick = {
                                userDataStore.incrementPopupsClosed()
                                showDialog = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("残忍离开")
                        }
                    }
                }
            }
        }

        if (!showDialog) {
            Button(
                onClick = onBack,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                Text("返回")
            }
        }
    }
}

@Composable
fun EmptyStateAdExperience(ad: AdType, userDataStore: UserDataStore, onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "🔍", fontSize = 80.sp)
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "未找到结果",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "抱歉，没有找到相关结果",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(48.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = WarningOrange.copy(alpha = 0.1f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "🎁 为您推荐", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "虽然没有找到结果，\n但我们可以推荐一些广告给你！",
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { userDataStore.incrementTrapsTriggered() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("查看推荐")
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "💡 没有内容？那就看广告吧！",
                fontSize = 14.sp,
                color = WarningOrange
            )
        }

        Button(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text("返回")
        }
    }
}

@Composable
private fun rememberScrollState() = androidx.compose.foundation.rememberScrollState()

@Composable
private fun Modifier.verticalScroll(state: androidx.compose.foundation.ScrollState) =
    this.verticalScroll(state)
