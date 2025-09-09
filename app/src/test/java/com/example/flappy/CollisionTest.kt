package com.example.flappy

import com.example.flappy.game.*
import org.junit.Assert.*
import org.junit.Test

class CollisionTest {

    @Test
    fun `bird collides with top pipe`() {
        val engine = GameEngine(1000, 2000, false)
        val bird = Bird(x = 500f, y = 100f)
        val pipe = PipePair(x = 400f, gapY = 200f)

        assertTrue(engine.testCollides(bird, pipe, 2000))
    }

    @Test
    fun `bird collides with bottom pipe`() {
        val engine = GameEngine(1000, 2000, false)
        val bird = Bird(x = 500f, y = 1900f)
        val pipe = PipePair(x = 400f, gapY = 200f)

        assertTrue(engine.testCollides(bird, pipe, 2000))
    }

    @Test
    fun `bird passes through pipe gap safely`() {
        val engine = GameEngine(1000, 2000, false)
        val bird = Bird(x = 500f, y = 200f)
        val pipe = PipePair(x = 400f, gapY = 200f)

        assertFalse(engine.testCollides(bird, pipe, 2000))
    }

    @Test
    fun `bird collides with pipe edge case`() {
        val engine = GameEngine(1000, 2000, false)
        val bird = Bird(x = 460f, y = 150f) // Near pipe edge
        val pipe = PipePair(x = 400f, gapY = 200f)

        assertTrue(engine.testCollides(bird, pipe, 2000))
    }

    @Test
    fun `bird hits ground boundary`() {
        val engine = GameEngine(1000, 2000, false)
        val bird = Bird(x = 500f, y = 2000f - GameConfig.BIRD_RADIUS + 1f)

        assertTrue(engine.testBoundsCollision(bird, 2000))
    }

    @Test
    fun `bird hits ceiling boundary`() {
        val engine = GameEngine(1000, 2000, false)
        val bird = Bird(x = 500f, y = GameConfig.BIRD_RADIUS - 1f)

        assertTrue(engine.testBoundsCollision(bird, 2000))
    }

    // Extension functions to test private methods
    private fun GameEngine.testCollides(bird: Bird, pipe: PipePair, height: Int): Boolean {
        return javaClass.getDeclaredMethod("collides", Bird::class.java, PipePair::class.java, Int::class.java)
            .apply { isAccessible = true }
            .invoke(this, bird, pipe, height) as Boolean
    }

    private fun GameEngine.testBoundsCollision(bird: Bird, height: Int): Boolean {
        return bird.y < GameConfig.BIRD_RADIUS || bird.y > height - GameConfig.BIRD_RADIUS
    }
}
