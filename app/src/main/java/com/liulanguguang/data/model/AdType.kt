package com.liulanguguang.data.model

enum class AdCategory(val displayName: String, val emoji: String) {
    STRONG("强干扰型", "🔴"),
    MEDIUM("中干扰型", "🟡"),
    WEAK("弱干扰型", "🟢"),
    SYSTEM("系统级", "⚫"),
    SPECIAL("特殊交互", "🔵"),
    OTHER("其他", "⚪")
}

data class AdType(
    val id: Int,
    val name: String,
    val category: AdCategory,
    val description: String,
    val sarcasm: String,
    val icon: String,
    val patienceCost: Int,
    val coinReward: Int
)

object AdData {
    val ads = listOf(
        AdType(1, "开屏广告", AdCategory.STRONG, "APP启动瞬间全屏展示的广告", "你以为倒计时结束就能跳过了？太天真了！", "📱", 15, 5),
        AdType(2, "插屏广告", AdCategory.STRONG, "在用户操作流程中突然弹出的广告", "正看得开心？不好意思，广告来了！", "🖼️", 12, 4),
        AdType(3, "全屏视频广告", AdCategory.STRONG, "占据整个屏幕的视频广告", "感谢您观看广告，请继续等待...", "🎬", 20, 6),
        AdType(4, "悬浮窗广告", AdCategory.STRONG, "悬浮在界面上方的可移动小窗口", "你永远关不掉我~", "🎈", 10, 3),
        AdType(5, "弹窗广告", AdCategory.STRONG, "突然弹出的对话框式广告", "恭喜你获得了...一个关闭按钮！", "📢", 18, 5),

        AdType(6, "横幅广告", AdCategory.MEDIUM, "位于屏幕顶部或底部的长条状广告", "我不影响你看内容，真的。", "📊", 5, 2),
        AdType(7, "信息流广告", AdCategory.MEDIUM, "嵌入在内容流中的广告", "这条不是广告，你信吗？", "📰", 8, 3),
        AdType(8, "原生广告", AdCategory.MEDIUM, "与APP风格完全融合的广告", "我是广告，但我假装不是~", "🎭", 8, 3),
        AdType(9, "搜索结果广告", AdCategory.MEDIUM, "搜索结果页面顶部或底部的广告", "你搜的是这个，我推的是那个~", "🔍", 6, 2),
        AdType(10, "推荐位广告", AdCategory.MEDIUM, "猜你喜欢板块中的广告", "我猜你喜欢这个广告！", "💡", 6, 2),
        AdType(11, "贴片广告", AdCategory.MEDIUM, "视频前、中、后插入的广告", "视频开始前，先看60秒广告吧~", "📺", 12, 4),

        AdType(12, "激励视频广告", AdCategory.WEAK, "主动观看可获得奖励的广告", "看广告还能赚钱？赚的是寂寞...", "🎁", 3, 5),
        AdType(13, "积分墙广告", AdCategory.WEAK, "下载APP、注册账号换取积分", "想要奖励？先把你的隐私交出来！", "🏆", 8, 3),
        AdType(14, "试玩广告", AdCategory.WEAK, "可直接体验的游戏广告", "试玩30秒后，你更想玩了！但要付费~", "🎮", 5, 4),
        AdType(15, "内容植入广告", AdCategory.WEAK, "软文、种草笔记形式的广告", "这篇测评真的很客观...才怪！", "✍️", 5, 2),
        AdType(16, "评论区广告", AdCategory.WEAK, "伪装成用户评论的广告", "这条评论是假的，但你信了~", "💬", 4, 2),
        AdType(17, "个人中心广告", AdCategory.WEAK, "设置页面中的广告", "你都在我的个人中心了，看个广告呗~", "👤", 3, 1),

        AdType(18, "通知栏广告", AdCategory.SYSTEM, "通过系统通知推送的广告", "就算你没打开APP，我也能骚扰你！", "🔔", 15, 5),
        AdType(19, "锁屏广告", AdCategory.SYSTEM, "锁屏界面展示的广告", "解锁前，先看个广告？", "🔒", 12, 4),
        AdType(20, "桌面图标广告", AdCategory.SYSTEM, "桌面图标上的红点或角标", "小红点强迫症的噩梦~", "🔴", 8, 3),
        AdType(21, "应用内更新广告", AdCategory.SYSTEM, "伪装成更新提示的广告", "点更新？恭喜你下载了另一个APP！", "⬆️", 10, 3),
        AdType(22, "系统功能伪装广告", AdCategory.SYSTEM, "伪装成系统功能提示的广告", "你的手机很危险！...但其实没有~", "⚠️", 10, 3),

        AdType(23, "摇一摇广告", AdCategory.SPECIAL, "晃动手机触发的广告", "你摇什么摇？摇也跳不过去~", "📳", 15, 5),
        AdType(24, "滑动解锁广告", AdCategory.SPECIAL, "滑动特定区域才能关闭的广告", "解锁广告的解锁方式~", "➡️", 8, 3),
        AdType(25, "语音广告", AdCategory.SPECIAL, "自动播放的语音广告", "想安静？不存在的！", "🔊", 12, 4),
        AdType(26, "AR广告", AdCategory.SPECIAL, "AR滤镜中的广告", "AR让你无处可逃~", "🕶️", 10, 4),
        AdType(27, "弹幕广告", AdCategory.SPECIAL, "视频弹幕中的广告", "弹幕广告，躲都躲不掉~", "💭", 6, 2),
        AdType(28, "表情包广告", AdCategory.SPECIAL, "表情包形式的广告", "可爱吗？可爱就对了！", "😊", 4, 2),

        AdType(29, "支付成功页广告", AdCategory.OTHER, "完成支付后展示的广告", "你都付款了，再看一眼广告呗~", "💰", 5, 2),
        AdType(30, "分享页广告", AdCategory.OTHER, "分享内容时展示的广告", "分享传播，广告先行~", "📤", 4, 2),
        AdType(31, "退弹广告", AdCategory.OTHER, "退出时弹出的广告", "别走！再看一个广告！", "👋", 15, 5),
        AdType(32, "空状态广告", AdCategory.OTHER, "搜索结果为空时展示的广告", "没找到内容？那就看广告吧！", "🔍", 3, 1)
    )

    fun getById(id: Int): AdType? = ads.find { it.id == id }

    fun getByCategory(category: AdCategory): List<AdType> = ads.filter { it.category == category }
}
