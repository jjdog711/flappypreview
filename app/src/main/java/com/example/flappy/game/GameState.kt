package com.example.flappy.game

enum class Phase { Ready, Playing, GameOver }

data class Bird(
    var x: Float = 0f,
    var y: Float = 0f,
    var vy: Float = 0f
)

data class PipePair(
    var x: Float = 0f,
    var gapY: Float = 0f,
    var passed: Boolean = false
)

data class World(
    var phase: Phase = Phase.Ready,
    var score: Int = 0,
    var timeNanos: Long = 0L,
    val bird: Bird = Bird(),
    val pipes: Array<PipePair> = Array(4) { PipePair() }
)
