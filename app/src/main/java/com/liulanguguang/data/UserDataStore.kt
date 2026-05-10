package com.liulanguguang.data

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserDataStore(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("liulanguguang_prefs", Context.MODE_PRIVATE)

    private val _patience = MutableStateFlow(prefs.getInt("patience", 100))
    val patience: StateFlow<Int> = _patience.asStateFlow()

    private val _coins = MutableStateFlow(prefs.getInt("coins", 0))
    val coins: StateFlow<Int> = _coins.asStateFlow()

    private val _experiencedAds = MutableStateFlow(
        prefs.getStringSet("experienced_ads", emptySet()) ?: emptySet()
    )
    val experiencedAds: StateFlow<Set<String>> = _experiencedAds.asStateFlow()

    private val _unlockedAchievements = MutableStateFlow(
        prefs.getStringSet("unlocked_achievements", emptySet()) ?: emptySet()
    )
    val unlockedAchievements: StateFlow<Set<String>> = _unlockedAchievements.asStateFlow()

    private val _darkMode = MutableStateFlow(prefs.getBoolean("dark_mode", false))
    val darkMode: StateFlow<Boolean> = _darkMode.asStateFlow()

    private val _soundEnabled = MutableStateFlow(prefs.getBoolean("sound_enabled", true))
    val soundEnabled: StateFlow<Boolean> = _soundEnabled.asStateFlow()

    private val _trapsTriggered = MutableStateFlow(prefs.getInt("traps_triggered", 0))
    val trapsTriggered: StateFlow<Int> = _trapsTriggered.asStateFlow()

    private val _popupsClosed = MutableStateFlow(prefs.getInt("popups_closed", 0))
    val popupsClosed: StateFlow<Int> = _popupsClosed.asStateFlow()

    private val _fakeAdsSpotted = MutableStateFlow(prefs.getInt("fake_ads_spotted", 0))
    val fakeAdsSpotted: StateFlow<Int> = _fakeAdsSpotted.asStateFlow()

    private val _quickSkips = MutableStateFlow(prefs.getInt("quick_skips", 0))
    val quickSkips: StateFlow<Int> = _quickSkips.asStateFlow()

    fun updatePatience(value: Int) {
        val newValue = value.coerceIn(0, 100)
        _patience.value = newValue
        prefs.edit().putInt("patience", newValue).apply()
    }

    fun updateCoins(value: Int) {
        val newValue = (value).coerceAtLeast(0)
        _coins.value = newValue
        prefs.edit().putInt("coins", newValue).apply()
    }

    fun addCoins(amount: Int) {
        updateCoins(_coins.value + amount)
    }

    fun deductPatience(amount: Int) {
        updatePatience(_patience.value - amount)
    }

    fun addExperiencedAd(adId: String) {
        val newSet = _experiencedAds.value + adId
        _experiencedAds.value = newSet
        prefs.edit().putStringSet("experienced_ads", newSet).apply()
    }

    fun unlockAchievement(achievementId: String) {
        val newSet = _unlockedAchievements.value + achievementId
        _unlockedAchievements.value = newSet
        prefs.edit().putStringSet("unlocked_achievements", newSet).apply()
    }

    fun toggleDarkMode() {
        val newValue = !_darkMode.value
        _darkMode.value = newValue
        prefs.edit().putBoolean("dark_mode", newValue).apply()
    }

    fun toggleSound() {
        val newValue = !_soundEnabled.value
        _soundEnabled.value = newValue
        prefs.edit().putBoolean("sound_enabled", newValue).apply()
    }

    fun incrementTrapsTriggered() {
        val newValue = _trapsTriggered.value + 1
        _trapsTriggered.value = newValue
        prefs.edit().putInt("traps_triggered", newValue).apply()
    }

    fun incrementPopupsClosed() {
        val newValue = _popupsClosed.value + 1
        _popupsClosed.value = newValue
        prefs.edit().putInt("popups_closed", newValue).apply()
    }

    fun incrementFakeAdsSpotted() {
        val newValue = _fakeAdsSpotted.value + 1
        _fakeAdsSpotted.value = newValue
        prefs.edit().putInt("fake_ads_spotted", newValue).apply()
    }

    fun incrementQuickSkips() {
        val newValue = _quickSkips.value + 1
        _quickSkips.value = newValue
        prefs.edit().putInt("quick_skips", newValue).apply()
    }

    fun resetProgress() {
        updatePatience(100)
        updateCoins(0)
        _experiencedAds.value = emptySet()
        _unlockedAchievements.value = emptySet()
        _trapsTriggered.value = 0
        _popupsClosed.value = 0
        _fakeAdsSpotted.value = 0
        _quickSkips.value = 0

        prefs.edit()
            .putInt("patience", 100)
            .putInt("coins", 0)
            .putStringSet("experienced_ads", emptySet())
            .putStringSet("unlocked_achievements", emptySet())
            .putInt("traps_triggered", 0)
            .putInt("popups_closed", 0)
            .putInt("fake_ads_spotted", 0)
            .putInt("quick_skips", 0)
            .apply()
    }
}
