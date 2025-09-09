package com.example.flappy

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import android.graphics.Bitmap
import androidx.compose.ui.graphics.asAndroidBitmap

@RunWith(AndroidJUnit4::class)
@LargeTest
class ScreenshotTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun captureScreenshots() {
        val outputDir = File("app/build/outputs/preview_screens").apply { mkdirs() }

        // Wait for app to load
        runBlocking { delay(2000) }

        // Capture Ready state
        captureScreenshot("shot_ready.png", outputDir)

        // Start game
        composeTestRule.onRoot().performClick()
        runBlocking { delay(1000) }

        // Capture Playing state
        captureScreenshot("shot_playing.png", outputDir)

        // Wait a bit more to potentially hit a pipe
        runBlocking { delay(2000) }

        // Capture GameOver state (or whatever state we're in)
        captureScreenshot("shot_gameover.png", outputDir)
    }

    private fun captureScreenshot(filename: String, outputDir: File) {
        try {
            val bitmap = composeTestRule.onRoot()
                .captureToImage()
                .asAndroidBitmap()

            val outputFile = File(outputDir, filename)
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputFile.outputStream())
            println("Screenshot saved: ${outputFile.absolutePath}")
        } catch (e: Exception) {
            println("Failed to capture screenshot $filename: ${e.message}")
        }
    }
}
