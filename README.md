# 📢 流氓广告

> 让你体验被广告支配的恐惧！

[![Platform](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com/)
[![Language](https://img.shields.io/badge/Language-Kotlin-purple.svg)](https://kotlinlang.org/)
[![UI Framework](https://img.shields.io/badge/UI-Jetpack%20Compose-blue.svg)](https://developer.android.com/compose)

## 📱 项目简介

**流氓广告** 是一款极具讽刺意味的 Android 应用，旨在以夸张、幽默的方式模拟和展示国内手机应用中常见的 32 种广告形式。通过沉浸式的体验，让用户在笑声中释放对广告的不满情绪。

### 核心特点

- 🎭 **32 种广告形式**：涵盖强干扰型、中干扰型、弱干扰型、系统级、特殊交互型等多种广告类别
- 🎮 **游戏化体验**：广告忍耐度系统、广告币系统、成就系统
- 😂 **讽刺幽默**：每种广告都配有搞笑的讽刺文案
- 🎨 **精美界面**：采用 Jetpack Compose 构建现代化 UI

## ✨ 功能亮点

### 广告类型分类

| 类别 | 数量 | 代表广告 |
|------|------|----------|
| 🔴 强干扰型 | 5 种 | 开屏广告、插屏广告、全屏视频广告、悬浮窗广告、弹窗广告 |
| 🟡 中干扰型 | 6 种 | 横幅广告、信息流广告、原生广告、搜索结果广告、推荐位广告、贴片广告 |
| 🟢 弱干扰型 | 6 种 | 激励视频广告、积分墙广告、试玩广告、内容植入广告、评论区广告、个人中心广告 |
| ⚫ 系统级 | 5 种 | 通知栏广告、锁屏广告、桌面图标广告、应用内更新广告、系统功能伪装广告 |
| 🔵 特殊交互型 | 6 种 | 摇一摇广告、滑动解锁广告、语音广告、AR 广告、弹幕广告、表情包广告 |
| ⚪ 其他 | 4 种 | 支付成功页广告、分享页广告、退弹广告、空状态广告 |

### 游戏化系统

- 💔 **广告忍耐度**：初始 100%，体验广告后会下降
- 💰 **广告币**：通过体验广告获得，可用于解锁成就
- 🏆 **成就系统**：10 个可解锁成就，每个成就都有搞笑描述

## 🛠️ 技术栈

### 核心技术

- **语言**：Kotlin 1.9+
- **UI 框架**：Jetpack Compose
- **最低 SDK**：API 24 (Android 7.0)
- **目标 SDK**：API 34 (Android 14)
- **架构模式**：MVVM
- **状态管理**：Compose State + ViewModel
- **导航**：Jetpack Navigation Compose

### 主要依赖

```gradle
// Jetpack Compose
implementation platform('androidx.compose:compose-bom:2024.01.00')
implementation 'androidx.compose.ui:ui'
implementation 'androidx.compose.material3:material3'

// Navigation
implementation 'androidx.navigation:navigation-compose:2.7.6'

// Lifecycle
implementation 'androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2'
```

## 📂 项目结构

```
com.liulanguguang
├── MainActivity.kt                 # 主活动入口
├── ui/
│   ├── theme/
│   │   ├── Color.kt               # 颜色定义
│   │   ├── Theme.kt                # 主题配置
│   │   └── Type.kt                 # 字体样式
│   ├── screens/
│   │   ├── HomeScreen.kt           # 首页 - 广告博物馆
│   │   ├── AdExperienceScreen.kt    # 广告体验页面
│   │   ├── AchievementScreen.kt     # 成就中心
│   │   └── SettingsScreen.kt        # 设置页面
│   ├── components/
│   │   └── CommonComponents.kt      # 通用组件
│   └── MainNavigation.kt            # 导航配置
├── data/
│   ├── UserDataStore.kt             # 用户数据持久化
│   └── model/
│       ├── AdType.kt               # 广告类型数据模型
│       └── Achievement.kt           # 成就数据模型
└── viewmodel/
    └── HomeViewModel.kt            # 首页 ViewModel
```

## 🚀 快速开始

### 环境要求

- Android Studio Hedgehog (2023.1.1) 或更高版本
- JDK 17
- Android SDK API 34
- Gradle 8.2 或更高版本

### 构建步骤

1. **克隆项目**

```bash
git clone <repository-url>
cd 流氓广告
```

2. **配置 Android SDK**

确保 Android SDK 已安装并配置环境变量：

```bash
export ANDROID_HOME=/path/to/android/sdk
export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools
```

3. **同步 Gradle**

```bash
./gradlew clean
./gradlew build
```

4. **运行应用**

在 Android Studio 中，选择 **app** 模块，点击运行按钮，或使用命令行：

```bash
./gradlew installDebug
```

### 构建 APK

```bash
# Debug 版本
./gradlew assembleDebug

# Release 版本（需要签名配置）
./gradlew assembleRelease
```

APK 文件位置：`app/build/outputs/apk/debug/app-debug.apk`

## 📖 使用指南

### 首页 - 广告博物馆

1. 顶部显示 **广告忍耐度进度条** 和 **广告币数量**
2. 可通过分类标签筛选广告类型
3. 点击任意广告卡片进入体验页面
4. 每种广告都有独特的讽刺文案

### 广告体验

每种广告都有独立的体验页面，包含：

- 高度还原的广告展示效果
- 可交互的关闭/跳过机制
- 倒计时或等待机制
- 体验完成后的讽刺文案

### 成就系统

- 点击顶部 🏆 图标进入成就中心
- 查看统计数据：体验广告数、被坑次数、识破伪装数等
- 解锁成就获得广告币奖励

### 设置页面

- **深色模式**：切换应用主题
- **音效**：开关音效反馈
- **重置进度**：清除所有数据重新开始

## 🎨 界面预览

### 首页

- 顶部状态栏：广告忍耐度 + 广告币
- 分类标签：6 个类别快速筛选
- 广告卡片：图标 + 名称 + 讽刺文案 + 体验次数

### 广告体验

- 全屏沉浸式广告展示
- 倒计时进度环
- 跳过/关闭按钮（模拟真实广告）
- 讽刺文案弹窗

### 成就中心

- 统计卡片：广告币、体验数、被坑次数等
- 成就列表：已解锁/未解锁状态
- 进度条：显示当前进度

## 💡 讽刺文案示例

| 广告类型 | 讽刺文案 |
|----------|----------|
| 开屏广告 | 你以为倒计时结束就能跳过了？太天真了！ |
| 插屏广告 | 正看得开心？不好意思，广告来了！ |
| 悬浮窗广告 | 你永远关不掉我~ |
| 弹窗广告 | 恭喜你获得了...一个关闭按钮！ |
| 摇一摇广告 | 你摇什么摇？摇也跳不过去~ |
| 语音广告 | 想安静？不存在的！ |

## ⚠️ 警告

> 本应用纯属娱乐讽刺，如有雷同，那是巧合。
>
> 看完这个 APP，你会觉得其他 APP 的广告也没那么讨厌了...

本应用可能会导致：

- 对广告产生心理阴影
- 看到广告就想吐槽
- 社交能力下降（因为一直在体验广告）
- 对现实中的广告产生应激反应

## 🤝 贡献

欢迎提交 Issue 和 Pull Request！

## 📄 许可证

本项目仅供学习交流使用，请勿用于商业用途。

---

**Made with 💔 by 被广告折磨的开发者**

**版本 1.0.0**
