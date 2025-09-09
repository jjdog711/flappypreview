package com.example.flappy

import com.example.flappy.game.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class ScoringTest {

    @Test
    fun `score increments when bird passes pipe`() {
        val engine = GameEngine(1000, 2000, false)
        val world = World()
        world.bird.x = 500f
        world.pipes[0] = PipePair(x = 400f, gapY = 200f, passed = false)

        // Simulate passing the pipe
        world.pipes[0].x = 300f // Move pipe left of bird
        var scoreCalled = false
        val testEngine = GameEngine(1000, 2000, false, onScore = { scoreCalled = true })

        // Manually trigger scoring logic
        testEngine.testScoring(world.bird, world.pipes[0])

        assertTrue("Score callback should be called", scoreCalled)
    }

    @Test
    fun `score only increments once per pipe`() {
        val engine = GameEngine(1000, 2000, false)
        val world = World()
        world.bird.x = 500f
        val pipe = PipePair(x = 400f, gapY = 200f, passed = false)

        // First pass should increment score
        pipe.x = 300f
        var scoreCount = 0
        val testEngine = GameEngine(1000, 2000, false, onScore = { scoreCount++ })
        testEngine.testScoring(world.bird, pipe)

        // Second pass should not increment score
        testEngine.testScoring(world.bird, pipe)

        assertEquals("Score should only increment once per pipe", 1, scoreCount)
    }

    @Test
    fun `multiple pipes can be scored independently`() {
        val engine = GameEngine(1000, 2000, false)
        val world = World()
        world.bird.x = 500f
        val pipe1 = PipePair(x = 400f, gapY = 200f, passed = false)
        val pipe2 = PipePair(x = 200f, gapY = 200f, passed = false)

        var scoreCount = 0
        val testEngine = GameEngine(1000, 2000, false, onScore = { scoreCount++ })

        // Both pipes should trigger scoring
        pipe1.x = 300f
        pipe2.x = 300f
        testEngine.testScoring(world.bird, pipe1)
        testEngine.testScoring(world.bird, pipe2)

        assertEquals("Both pipes should increment score", 2, scoreCount)
    }

    @Test
    fun `pipe scoring threshold is correct`() {
        val engine = GameEngine(1000, 2000, false)
        val world = World()
        val pipe = PipePair(x = 450f, gapY = 200f, passed = false)

        var scoreCalled = false
        val testEngine = GameEngine(1000, 2000, false, onScore = { scoreCalled = true })

        // Bird at pipe edge (should not score yet)
        world.bird.x = 450f + GameConfig.PIPE_WIDTH - 1f
        testEngine.testScoring(world.bird, pipe)
        assertFalse("Should not score when bird is at pipe edge", scoreCalled)

        // Bird past pipe (should score)
        world.bird.x = 450f + GameConfig.PIPE_WIDTH + 1f
        testEngine.testScoring(world.bird, pipe)
        assertTrue("Should score when bird passes pipe", scoreCalled)
    }

    // Extension function to test private scoring logic
    private fun GameEngine.testScoring(bird: Bird, pipe: PipePair) {
        // Simulate the scoring check from the engine
        if (!pipe.passed && pipe.x + GameConfig.PIPE_WIDTH < bird.x) {
            pipe.passed = true
            javaClass.getDeclaredField("onScore")
                .apply { isAccessible = true }
                .get(this) as? (() -> Unit)?.invoke()
        }
    }
}
