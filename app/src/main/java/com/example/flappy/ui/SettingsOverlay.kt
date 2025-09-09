package com.example.flappy.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.flappy.data.Settings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsOverlay(
    settings: Settings?,
    onDismiss: () -> Unit,
    onReducedMotionChanged: (Boolean) -> Unit,
    onHighContrastChanged: (Boolean) -> Unit,
    onSfxChanged: (Boolean) -> Unit
) {
    val s = settings ?: return

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = Modifier.semantics {
            contentDescription = "Settings panel"
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Reduced Motion
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Reduced Motion",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.semantics {
                        contentDescription = "Reduced motion setting. When enabled, bird movement is less intense."
                    }
                )
                Switch(
                    checked = s.reducedMotion,
                    onCheckedChange = onReducedMotionChanged,
                    modifier = Modifier.semantics {
                        contentDescription = if (s.reducedMotion) "Reduced motion is enabled" else "Reduced motion is disabled"
                    }
                )
            }

            // High Contrast
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "High Contrast",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.semantics {
                        contentDescription = "High contrast mode. When enabled, uses black and white colors for better visibility."
                    }
                )
                Switch(
                    checked = s.highContrast,
                    onCheckedChange = onHighContrastChanged,
                    modifier = Modifier.semantics {
                        contentDescription = if (s.highContrast) "High contrast is enabled" else "High contrast is disabled"
                    }
                )
            }

            // SFX
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Sound Effects",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.semantics {
                        contentDescription = "Sound effects setting. When enabled, plays sounds for game actions."
                    }
                )
                Switch(
                    checked = s.sfxEnabled,
                    onCheckedChange = onSfxChanged,
                    modifier = Modifier.semantics {
                        contentDescription = if (s.sfxEnabled) "Sound effects are enabled" else "Sound effects are disabled"
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics {
                        contentDescription = "Close settings panel"
                    }
            ) {
                Text("Close")
            }
        }
    }
}
