package com.example.flappy

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SmokeTest {
    @Rule @JvmField
    val rule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun launches() {
        // If we reached here, activity launched without crash.
    }
}
