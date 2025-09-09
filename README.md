# Flappy Preview

Android-only Flappy-style game scaffold. This pass delivers a compiling **hello screen**, pinned toolchain, CI,
and a complete doc set so the next agent can one-shot the full game.

## Toolchain (Pinned)
- Kotlin: **1.9.22**
- Android Gradle Plugin (AGP): **8.7.2**
- Gradle Wrapper: **8.11.1**
- JDK: **17**
- Target SDK: **35**
- Min SDK: **24**
- Jetpack Compose: **BOM 2024.02.00**, Compiler **1.5.10** (compatible with Kotlin 1.9.22 — see docs)

> Rationale for Compose pinning is recorded in `ASSUMPTIONS.md` and `DECISION_LOG.md`.

## Quick Start
```bash
./gradlew clean assembleDebug
./gradlew :app:bundleRelease
adb install -r app/build/outputs/apk/debug/app-debug.apk
```
Artifacts:
- Debug APK: `app/build/outputs/apk/debug/app-debug.apk`
- Release AAB: `app/build/outputs/bundle/release/app-release.aab`

## CI
See `.github/workflows/android.yml` — builds `:app:bundleRelease` and uploads AAB artifact.

## Folder Map
- `/app` — Android app module (Compose)
- `/docs` — Guides (style, UI, performance, tests, versions)
- `/tasks` — Seed tasks for next agent
- Root docs: architecture, game design, playbook, plan, acceptance criteria, troubleshooting, roadmap

## Next Agent — Start Here (10-minute ramp)
1. Read `AGENT_PLAYBOOK.md` (DONE Gate at bottom).
2. Review `GAME_DESIGN_SPEC.md` and `ARCHITECTURE.md`.
3. Execute tasks in `/tasks` in order.
4. Keep stack locked. No new heavy deps.
5. Ship a one-shot pass that satisfies `ACCEPTANCE_CRITERIA.md` and passes CI.

Links: ARCHITECTURE.md • GAME_DESIGN_SPEC.md • AGENT_PLAYBOOK.md • IMPLEMENTATION_PLAN.md • ACCEPTANCE_CRITERIA.md • TROUBLESHOOTING.md • TEST_STRATEGY.md • PERFORMANCE_GUIDE.md • UI_GUIDE.md • ROADMAP.md • ASSUMPTIONS.md
