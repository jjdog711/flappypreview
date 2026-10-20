# Agent Playbook — One-Shot Implementation

## Guardrails
- Stack is locked: Kotlin 1.9.22 / AGP 8.7.2 / Gradle 8.11.1 / JDK 17 / Compose BOM pinned.
- No engines, no DI frameworks, minimal deps.
- Fixed-timestep simulation; avoid per-frame allocations; use `remember {}` wisely.
- Accessibility and DataStore persistence must be implemented.

## Steps
1. **Game Loop**: Implement fixed-step loop in a `GameEngine` with pause/resume.
2. **Entities**: Bird, Pipes, Background parallax; preallocate arrays/buffers.
3. **Collision**: Circle vs rect; clamp and early-out.
4. **Spawning & Scoring**: RNG with deterministic seed for tests; scoring event channel.
5. **UI Overlays**: Ready, Pause, GameOver; HUD typography; buttons with content descriptions.
6. **Settings**: Reduced Motion, High Contrast, SFX on/off; persist with Proto DataStore.
7. **Audio**: SoundPool for flap/score/hit; preload on start.
8. **Perf**: Avoid allocations; benchmark frame time; throttle logs.
9. **Tests**: Unit collision/scoring; instrumented smoke; perf sanity target ≥ 55 FPS emulator.
10. **Release Prep**: Feature graphic and screenshots; ensure CI artifact green.

## Historical implementation checklist
The following undated checklist records previously reported implementation status against `ACCEPTANCE_CRITERIA.md`. It does not establish current build, device, or hosted CI results; revalidate the relevant checks for the commit under review.
- Builds succeed: `assembleDebug` and `:app:bundleRelease` working
- App launches to game screen with immersive applied
- Game implementation: playable with tap-to-flap, pipes, collision, scoring, best score persistence
- Settings toggles visible and persist (DataStore + Proto)
- Accessibility labels present throughout UI
- CI uploads AAB artifact successfully
- Feature graphic and 3 screenshots generated via automated tests
