package com.example.flappy.game

object GameConfig {
    const val STEP_NS = 16_666_667L // 60Hz
    const val GRAVITY = 1200f
    const val FLAP_VELOCITY = -380f
    const val PIPE_SPEED = 140f
    const val PIPE_GAP_MIN = 220f
    const val PIPE_GAP_MAX = 300f
    const val PIPE_SPACING = 260f
    const val BIRD_RADIUS = 18f
    const val PIPE_WIDTH = 60f
    const val BIRD_START_X_RATIO = 0.3f
    const val BIRD_START_Y_RATIO = 0.5f
}
