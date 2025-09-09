# Architecture (Target End-State)

- **Pattern**: MVVM + single-activity Compose.
- **Game Loop**: Fixed-step simulation (e.g., 60 Hz) via `withFrameNanos` and accumulated delta; render each frame.
- **State Ownership**: ViewModel holds `GameState` and exposes immutable render model via `StateFlow`.
- **Rendering**: Compose Canvas draws background, bird, pipes, HUD.
- **Physics**: Simple vertical velocity with gravity; AABB/circle hit checks.
- **Persistence**: Proto DataStore for settings and best score.
- **Audio**: `SoundPool` for SFX; no large libs.
- **Modules (future)**: `core`, `feature-game`, `feature-settings`. For preview we keep single module for speed.
