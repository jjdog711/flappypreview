package com.example.flappy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.example.flappy.ui.HelloScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Immersive (documented in UI_GUIDE.md)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            HelloScreen(
                appVersion = BuildConfig.VERSION_NAME,
                toolchain = mapOf(
                    "Kotlin" to BuildConfig.KOTLIN_VERSION,
                    "AGP" to BuildConfig.AGP_VERSION,
                    "Gradle" to BuildConfig.GRADLE_VERSION,
                    "Compose BOM" to BuildConfig.COMPOSE_BOM,
                    "Compose Compiler" to BuildConfig.COMPOSE_COMPILER,
                    "JDK" to BuildConfig.JDK_VERSION,
                )
            )
        }
    }
}
