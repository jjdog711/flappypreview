package com.example.flappy.game

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

class GameEngine(
    widthPx: Int,
    heightPx: Int,
    private val reducedMotion: Boolean,
    private val onScore: (() -> Unit)? = null,
    private val onHit: (() -> Unit)? = null
) {
    private val rnd = Random(42) // deterministic for tests
    private val _render = MutableStateFlow(RenderModel.empty(widthPx, heightPx))
    val render: StateFlow<RenderModel> = _render
    private val world = World()

    fun reset(width: Int, height: Int) {
        world.phase = Phase.Ready
        world.score = 0
        world.timeNanos = 0L

        // Reset bird position
        world.bird.x = width * GameConfig.BIRD_START_X_RATIO
        world.bird.y = height * GameConfig.BIRD_START_Y_RATIO
        world.bird.vy = 0f

        // Reset pipes
        val spacing = GameConfig.PIPE_SPACING
        for (i in world.pipes.indices) {
            world.pipes[i].x = width + i * spacing
            world.pipes[i].gapY = height * 0.5f + rnd.nextFloat() * 200f - 100f
            world.pipes[i].passed = false
        }

        _render.value = RenderModel.fromWorld(world, width, height)
    }

    fun tap() {
        when (world.phase) {
            Phase.Ready -> {
                world.phase = Phase.Playing
                _render.update { it.copy(phase = Phase.Playing) }
            }
            Phase.Playing -> {
                world.bird.vy = GameConfig.FLAP_VELOCITY
            }
            Phase.GameOver -> {
                reset(_render.value.width, _render.value.height)
            }
        }
    }

    fun step(dt: Float) {
        if (world.phase != Phase.Playing) {
            return
        }

        // Update bird physics
        val gravity = if (reducedMotion) GameConfig.GRAVITY * 0.7f else GameConfig.GRAVITY
        world.bird.vy += gravity * dt
        world.bird.y += world.bird.vy * dt

        // Update pipes
        for (pipe in world.pipes) {
            pipe.x -= GameConfig.PIPE_SPEED * dt

            // Recycle pipe if off screen
            if (pipe.x < -GameConfig.PIPE_WIDTH) {
                val lastPipe = world.pipes.maxByOrNull { it.x } ?: pipe
                pipe.x = lastPipe.x + GameConfig.PIPE_SPACING
                pipe.gapY = _render.value.height * 0.5f + rnd.nextFloat() * 200f - 100f
                pipe.passed = false
            }

            // Check scoring
            if (!pipe.passed &&
                pipe.x + GameConfig.PIPE_WIDTH < world.bird.x) {
                pipe.passed = true
                world.score++
                onScore?.invoke()
            }

            // Check collision
            if (collides(world.bird, pipe, _render.value.height)) {
                world.phase = Phase.GameOver
                onHit?.invoke()
                _render.update { it.copy(phase = Phase.GameOver) }
                return
            }
        }

        // Check bounds collision
        if (world.bird.y < GameConfig.BIRD_RADIUS ||
            world.bird.y > _render.value.height - GameConfig.BIRD_RADIUS) {
            world.phase = Phase.GameOver
            _render.update { it.copy(phase = Phase.GameOver) }
        } else {
            _render.update { RenderModel.fromWorld(world, it.width, it.height, it.bestScore) }
        }
    }

    private fun collides(bird: Bird, pipe: PipePair, height: Int): Boolean {
        val radius = GameConfig.BIRD_RADIUS
        val pipeWidth = GameConfig.PIPE_WIDTH
        val gapMin = GameConfig.PIPE_GAP_MIN
        val gapMax = GameConfig.PIPE_GAP_MAX
        val gap = max(gapMin, min(gapMax, gapMin + (gapMax - gapMin) * 0.5f))

        val gapTop = pipe.gapY - gap / 2f
        val gapBottom = pipe.gapY + gap / 2f

        // Circle-rectangle collision for top pipe
        if (circleRect(bird.x, bird.y, pipe.x, 0f, pipeWidth, gapTop)) {
            return true
        }

        // Circle-rectangle collision for bottom pipe
        if (circleRect(bird.x, bird.y, pipe.x, gapBottom, pipeWidth, height - gapBottom)) {
            return true
        }

        return false
    }

    private fun circleRect(cx: Float, cy: Float, rx: Float, ry: Float, rw: Float, rh: Float): Boolean {
        val nearestX = max(rx, min(cx, rx + rw))
        val nearestY = max(ry, min(cy, ry + rh))
        val dx = cx - nearestX
        val dy = cy - nearestY
        return dx * dx + dy * dy <= GameConfig.BIRD_RADIUS * GameConfig.BIRD_RADIUS
    }
}
