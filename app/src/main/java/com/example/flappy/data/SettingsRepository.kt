package com.example.flappy.data

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

private const val PREFS_NAME = "flappy_settings"
private const val KEY_REDUCED_MOTION = "reduced_motion"
private const val KEY_HIGH_CONTRAST = "high_contrast"
private const val KEY_SFX_ENABLED = "sfx_enabled"
private const val KEY_BEST_SCORE = "best_score"

class SettingsRepository(private val context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val _settings = MutableStateFlow(Settings(
        reducedMotion = prefs.getBoolean(KEY_REDUCED_MOTION, false),
        highContrast = prefs.getBoolean(KEY_HIGH_CONTRAST, false),
        sfxEnabled = prefs.getBoolean(KEY_SFX_ENABLED, true),
        bestScore = prefs.getInt(KEY_BEST_SCORE, 0)
    ))
    val settings: Flow<Settings> = _settings.asStateFlow()

    val bestScore: Flow<Int> = settings.map { it.bestScore }

    fun setReducedMotion(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_REDUCED_MOTION, enabled).apply()
        _settings.value = _settings.value.copy(reducedMotion = enabled)
    }

    fun setHighContrast(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_HIGH_CONTRAST, enabled).apply()
        _settings.value = _settings.value.copy(highContrast = enabled)
    }

    fun setSfxEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_SFX_ENABLED, enabled).apply()
        _settings.value = _settings.value.copy(sfxEnabled = enabled)
    }

    fun submitScore(score: Int) {
        val currentBest = _settings.value.bestScore
        if (score > currentBest) {
            prefs.edit().putInt(KEY_BEST_SCORE, score).apply()
            _settings.value = _settings.value.copy(bestScore = score)
        }
    }
}

data class Settings(
    val reducedMotion: Boolean = false,
    val highContrast: Boolean = false,
    val sfxEnabled: Boolean = true,
    val bestScore: Int = 0
)
