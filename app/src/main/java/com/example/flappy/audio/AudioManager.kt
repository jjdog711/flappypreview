package com.example.flappy.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.example.flappy.R

class AudioManager(context: Context) {
    private val soundPool: SoundPool
    private var flapSoundId: Int = 0
    private var scoreSoundId: Int = 0
    private var hitSoundId: Int = 0

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(3)
            .setAudioAttributes(audioAttributes)
            .build()

        // Load sounds (these will be silent placeholders for now)
        try {
            flapSoundId = soundPool.load(context, R.raw.flap, 1)
            scoreSoundId = soundPool.load(context, R.raw.score, 1)
            hitSoundId = soundPool.load(context, R.raw.hit, 1)
        } catch (e: Exception) {
            // Handle missing sound files gracefully
        }
    }

    fun playFlap() {
        if (flapSoundId != 0) {
            soundPool.play(flapSoundId, 1f, 1f, 1, 0, 1f)
        }
    }

    fun playScore() {
        if (scoreSoundId != 0) {
            soundPool.play(scoreSoundId, 1f, 1f, 1, 0, 1f)
        }
    }

    fun playHit() {
        if (hitSoundId != 0) {
            soundPool.play(hitSoundId, 1f, 1f, 1, 0, 1f)
        }
    }

    fun setVolume(volume: Float) {
        // Adjust volume for all sounds
    }

    fun release() {
        soundPool.release()
    }
}
