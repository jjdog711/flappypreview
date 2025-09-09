package com.example.flappy.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import com.example.flappy.game.Phase
import kotlinx.coroutines.launch

@Composable
fun GameRoot() {
    val viewModel: GameViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current

    val settings by viewModel.settings.collectAsState(initial = null)
    val bestScore by viewModel.bestScore.collectAsState(initial = 0)
    val render by viewModel.render.collectAsState()

    // Initialize game when we have dimensions
    LaunchedEffect(configuration) {
        val width = configuration.screenWidthDp * (configuration.densityDpi / 160f)
        val height = configuration.screenHeightDp * (configuration.densityDpi / 160f)
        viewModel.start(width.toInt(), height.toInt())
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Main game screen
        settings?.let { s ->
            GameScreen(
                renderFlow = viewModel.render,
                highContrast = s.highContrast,
                bestScore = bestScore,
                onTap = { viewModel.onTap() }
            )
        }

        // Settings overlay
        var showSettings by remember { mutableStateOf(false) }

        if (showSettings) {
            SettingsOverlay(
                settings = settings,
                onDismiss = { showSettings = false },
                onReducedMotionChanged = { viewModel.setReducedMotion(it) },
                onHighContrastChanged = { viewModel.setHighContrast(it) },
                onSfxChanged = { viewModel.setSfxEnabled(it) }
            )
        }

        // Settings button click handler
        if (render.phase != Phase.Playing) {
            // Only show settings button when not actively playing
            androidx.compose.material3.IconButton(
                onClick = { showSettings = true },
                modifier = Modifier
                    .padding(16.dp)
                    .size(48.dp)
            ) {
                Text("⚙", style = MaterialTheme.typography.headlineMedium)
            }
        }

        // Submit score when game ends
        LaunchedEffect(render.phase) {
            if (render.phase == Phase.GameOver) {
                viewModel.submitScore(render.score)
            }
        }
    }
}
