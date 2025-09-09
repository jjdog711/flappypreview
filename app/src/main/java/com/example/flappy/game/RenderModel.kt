package com.example.flappy.game

data class RenderModel(
    val width: Int,
    val height: Int,
    val phase: Phase,
    val birdX: Float,
    val birdY: Float,
    val pipes: List<Pair<Float, Float>>, // x to gapY
    val score: Int,
    val bestScore: Int = 0
) {
    companion object {
        fun empty(width: Int, height: Int) = RenderModel(
            width = width,
            height = height,
            phase = Phase.Ready,
            birdX = width * GameConfig.BIRD_START_X_RATIO,
            birdY = height * GameConfig.BIRD_START_Y_RATIO,
            pipes = emptyList(),
            score = 0,
            bestScore = 0
        )

        fun fromWorld(world: World, width: Int, height: Int, bestScore: Int = 0) =
            RenderModel(
                width = width,
                height = height,
                phase = world.phase,
                birdX = world.bird.x,
                birdY = world.bird.y,
                pipes = world.pipes.map { it.x to it.gapY },
                score = world.score,
                bestScore = bestScore
            )
    }
}
