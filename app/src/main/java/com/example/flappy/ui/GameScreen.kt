package com.example.flappy.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.flappy.game.GameConfig
import com.example.flappy.game.Phase
import com.example.flappy.game.RenderModel
import kotlinx.coroutines.flow.StateFlow

@Composable
fun GameScreen(
    renderFlow: StateFlow<RenderModel>,
    highContrast: Boolean,
    bestScore: Int,
    onTap: () -> Unit
) {
    val render by renderFlow.collectAsState()

    val bgColor = if (highContrast) Color.Black else Color(0xFF101114)
    val fgColor = if (highContrast) Color.White else Color(0xFFEAEAEA)
    val pipeColor = if (highContrast) Color(0xFF4CAF50) else Color(0xFF2E7D32)
    val birdColor = if (highContrast) Color(0xFFFFEB3B) else Color(0xFFFFD54F)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onTap() }
                )
            }
            .semantics {
                contentDescription = when (render.phase) {
                    Phase.Ready -> "Flappy Bird game. Tap to start playing."
                    Phase.Playing -> "Game in progress. Score: ${render.score}. Tap to flap the bird."
                    Phase.GameOver -> "Game over. Score: ${render.score}, Best score: $bestScore. Tap to restart."
                }
            }
    ) {
        // Game canvas
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Draw pipes
            render.pipes.forEach { (x, gapY) ->
                val pipeWidth = GameConfig.PIPE_WIDTH
                val gap = GameConfig.PIPE_GAP_MIN + (GameConfig.PIPE_GAP_MAX - GameConfig.PIPE_GAP_MIN) * 0.5f

                // Top pipe
                drawRect(
                    color = pipeColor,
                    topLeft = Offset(x, 0f),
                    size = androidx.compose.ui.geometry.Size(pipeWidth, gapY - gap / 2f)
                )

                // Bottom pipe
                drawRect(
                    color = pipeColor,
                    topLeft = Offset(x, gapY + gap / 2f),
                    size = androidx.compose.ui.geometry.Size(pipeWidth, size.height - (gapY + gap / 2f))
                )
            }

            // Draw bird
            drawCircle(
                color = birdColor,
                center = Offset(render.birdX, render.birdY),
                radius = GameConfig.BIRD_RADIUS
            )
        }

        // HUD - Score
        Text(
            text = "${render.score}",
            color = fgColor,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 24.dp)
                .semantics {
                    contentDescription = "Current score: ${render.score}"
                }
        )

        // Settings button (top-right)
        IconButton(
            onClick = { /* TODO: Show settings */ },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .semantics {
                    contentDescription = "Open settings"
                }
        ) {
            Text("⚙", style = MaterialTheme.typography.headlineMedium)
        }

        // Overlays
        when (render.phase) {
            Phase.Ready -> OverlayText("Tap to start", fgColor)
            Phase.GameOver -> OverlayText("Game Over\nBest: $bestScore\nTap to restart", fgColor)
            else -> {}
        }
    }
}

@Composable
private fun OverlayText(msg: String, color: Color) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .semantics {
                contentDescription = msg.replace("\n", ". ")
            }
    ) {
        Text(
            text = msg,
            color = color,
            style = MaterialTheme.typography.titleLarge,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
