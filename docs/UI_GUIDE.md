# UI Guide

- Portrait only. Safe-area padding respected.
- HUD: Score (top-center), Pause (top-right), Settings (from GameOver/Ready).
- Overlays: Ready (tap to start), Paused, GameOver (score + best + buttons).
- Typography: Large numeric for score; high contrast mode increases weight.
- Immersive Sticky: Use `WindowCompat.setDecorFitsSystemWindows(window, false)` and insets padding.
