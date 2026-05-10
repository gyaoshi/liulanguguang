package com.liulanguguang.data.model

data class Achievement(
    val id: String,
    val name: String,
    val description: String,
    val icon: String,
    val coinReward: Int,
    val requirement: String
)

object AchievementData {
    val achievements = listOf(
        Achievement(
            id = "popup_master",
            name = "弹窗幸存者",
            description = "成功关闭5个弹窗广告",
            icon = "🎯",
            coinReward = 50,
            requirement = "close_popups_5"
        ),
        Achievement(
            id = "shake_resist",
            name = "摇一摇绝缘体",
            description = "在摇一摇广告中存活30秒",
            icon = "🚫",
            coinReward = 80,
            requirement = "shake_resist_30s"
        ),
        Achievement(
            id = "patience_king",
            name = "忍耐之王",
            description = "广告忍耐度归零前体验完所有广告",
            icon = "👑",
            coinReward = 200,
            requirement = "all_ads_no_zero_patience"
        ),
        Achievement(
            id = "quick_finger",
            name = "眼疾手快",
            description = "在3秒内点击跳过按钮",
            icon = "⚡",
            coinReward = 30,
            requirement = "skip_under_3s"
        ),
        Achievement(
            id = "trap_victim",
            name = "被坑专家",
            description = "误触诱导区域10次",
            icon = "😅",
            coinReward = 20,
            requirement = "trigger_traps_10"
        ),
        Achievement(
            id = "ad_collector",
            name = "广告收藏家",
            description = "体验过所有32种广告",
            icon = "📚",
            coinReward = 100,
            requirement = "all_ads_experienced"
        ),
        Achievement(
            id = "no_skip",
            name = "耐心的巨人",
            description = "看完一个完整视频广告（60秒）",
            icon = "🎬",
            coinReward = 60,
            requirement = "watch_full_video"
        ),
        Achievement(
            id = "tiny_clicker",
            name = "微距大师",
            description = "成功点击悬浮窗的关闭按钮",
            icon = "🔍",
            coinReward = 40,
            requirement = "click_tiny_close"
        ),
        Achievement(
            id = "fake_detective",
            name = "火眼金睛",
            description = "识别出10个伪装广告",
            icon = "👁️",
            coinReward = 50,
            requirement = "spot_fake_ads_10"
        ),
        Achievement(
            id = "ad_master",
            name = "广告大师",
            description = "解锁所有其他成就",
            icon = "🏆",
            coinReward = 500,
            requirement = "all_other_achievements"
        )
    )
}
