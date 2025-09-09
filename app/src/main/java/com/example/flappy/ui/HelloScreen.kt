package com.example.flappy.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HelloScreen(appVersion: String, toolchain: Map<String, String>) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Flappy Preview — Ready for Next Agent",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(12.dp))
            Text("Version " + appVersion, fontSize = 14.sp)
            Spacer(Modifier.height(24.dp))
            Text("Toolchain", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            toolchain.forEach { (k, v) ->
                Text(text = "$k: $v", fontSize = 13.sp)
            }
        }
    }
}
