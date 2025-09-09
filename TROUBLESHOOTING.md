# Troubleshooting

- **Compose/Kotlin mismatch**: Use Compose Compiler 1.5.10 with Kotlin 1.9.22.
- **CI cache**: Invalidate gradle cache by bumping cache key if toolchain changes.
- **Emulator perf**: Disable snapshots, enable hardware acceleration, use x86_64 images.
- **Recomposition jank**: Hoist state, avoid `mutableStateOf` in draw lambdas, precompute paths.
- **Android 15**: Verify full-screen flags and cutout behavior.
