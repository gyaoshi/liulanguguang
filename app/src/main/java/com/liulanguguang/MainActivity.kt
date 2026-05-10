package com.liulanguguang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.liulanguguang.data.UserDataStore
import com.liulanguguang.ui.MainNavigation
import com.liulanguguang.ui.theme.流氓广告Theme

class MainActivity : ComponentActivity() {
    private lateinit var userDataStore: UserDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userDataStore = UserDataStore(this)

        setContent {
            var darkMode by remember { mutableStateOf(userDataStore.darkMode.value) }

            流氓广告Theme(darkTheme = darkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNavigation(
                        userDataStore = userDataStore,
                        onDarkModeChange = { darkMode = it }
                    )
                }
            }
        }
    }
}
