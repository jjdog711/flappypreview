package com.example.flappy.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.flappy.audio.AudioManager
import com.example.flappy.data.SettingsRepository
import com.example.flappy.game.GameEngine
import com.example.flappy.game.RenderModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GameViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = SettingsRepository(app)
    private val audioManager = AudioManager(app)
    private var engine: GameEngine? = null
    val render: StateFlow<RenderModel> get() = engine?.render ?: MutableStateFlow(RenderModel.empty(1080, 1920))
    val settings = repo.settings
    val bestScore = repo.bestScore

    private var _settings = repo.settings
    private var loopJob: Job? = null

    fun start(width: Int, height: Int) {
        viewModelScope.launch {
            val s = repo.settings.first()
            engine = GameEngine(
                width,
                height,
                s.reducedMotion,
                onScore = {
                    if (s.sfxEnabled) audioManager.playScore()
                },
                onHit = {
                    if (s.sfxEnabled) audioManager.playHit()
                }
            )
            engine!!.reset(width, height)
            runLoop()
        }
    }

    private fun runLoop() {
        loopJob?.cancel()
        loopJob = viewModelScope.launch {
            var last = 0L
            val stepNs = 16_666_667L // 60Hz
            while (true) {
                val t = System.nanoTime()
                if (last == 0L) last = t
                var acc = t - last
                last = t
                while (acc >= stepNs) {
                    engine?.step(stepNs / 1_000_000_000f)
                    acc -= stepNs
                }
                kotlinx.coroutines.yield()
            }
        }
    }

    fun onTap() {
        engine?.tap()
        viewModelScope.launch {
            val sfxEnabled = repo.settings.first().sfxEnabled
            if (sfxEnabled) {
                audioManager.playFlap()
            }
        }
    }

    fun submitScore(score: Int) {
        repo.submitScore(score)
    }

    fun setReducedMotion(enabled: Boolean) {
        repo.setReducedMotion(enabled)
        // Recreate engine with new setting
        val s = _settings.value
        val currentRender = render.value ?: RenderModel.empty(1080, 1920)
        engine = GameEngine(
            currentRender.width,
            currentRender.height,
            s.reducedMotion,
            onScore = {
                if (s.sfxEnabled) audioManager.playScore()
            },
            onHit = {
                if (s.sfxEnabled) audioManager.playHit()
            }
        )
        engine!!.reset(currentRender.width, currentRender.height)
    }

    fun setHighContrast(enabled: Boolean) {
        repo.setHighContrast(enabled)
    }

    fun setSfxEnabled(enabled: Boolean) {
        repo.setSfxEnabled(enabled)
    }

    override fun onCleared() {
        super.onCleared()
        loopJob?.cancel()
        audioManager.release()
    }
}
