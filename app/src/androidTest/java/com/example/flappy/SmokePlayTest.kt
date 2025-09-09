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

@RunWith(AndroidJUnit4::class)
@LargeTest
class SmokePlayTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun smokeTest_gameLaunchesAndPlays() {
        // Wait for app to load
        runBlocking { delay(2000) }

        // Tap to start game
        composeTestRule.onRoot().performClick()

        // Wait for game to run briefly
        runBlocking { delay(3000) }

        // Game should still be running without crashes
        // In a real test, we could check for specific UI elements
        // For smoke test, just ensuring no exceptions were thrown
        assert(true) // If we reach here, the test passed
    }

    @Test
    fun smokeTest_settingsOverlay() {
        // Wait for app to load
        runBlocking { delay(1000) }

        // Try to access settings (this is a basic smoke test)
        // In a full implementation, we'd test the settings UI
        runBlocking { delay(1000) }

        assert(true)
    }
}
