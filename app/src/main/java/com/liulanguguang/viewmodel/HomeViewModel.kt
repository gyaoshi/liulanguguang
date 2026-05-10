package com.liulanguguang.viewmodel

import androidx.lifecycle.ViewModel
import com.liulanguguang.data.UserDataStore
import com.liulanguguang.data.model.AdData
import com.liulanguguang.data.model.AdType
import com.liulanguguang.data.model.AdCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val userDataStore: UserDataStore) : ViewModel() {
    val patience: StateFlow<Int> = userDataStore.patience
    val coins: StateFlow<Int> = userDataStore.coins
    val experiencedAds: StateFlow<Set<String>> = userDataStore.experiencedAds
    val darkMode: StateFlow<Boolean> = userDataStore.darkMode

    private val _selectedCategory = MutableStateFlow<AdCategory?>(null)
    val selectedCategory: StateFlow<AdCategory?> = _selectedCategory.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    fun getFilteredAds(): List<AdType> {
        return _selectedCategory.value?.let { category ->
            AdData.getByCategory(category)
        } ?: AdData.ads
    }

    fun selectCategory(category: AdCategory?) {
        _selectedCategory.value = category
    }

    fun getExperienceCount(adId: String): Int {
        return experiencedAds.value.count { it == adId }
    }

    fun refresh() {
        _isRefreshing.value = true
        kotlinx.coroutines.MainScope().launch {
            kotlinx.coroutines.delay(1500)
            _isRefreshing.value = false
        }
    }
}
