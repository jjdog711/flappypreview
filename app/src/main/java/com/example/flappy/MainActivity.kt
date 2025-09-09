package com.example.flappy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.example.flappy.ui.GameRoot

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Immersive (documented in UI_GUIDE.md)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            GameRoot()
        }
    }
}
