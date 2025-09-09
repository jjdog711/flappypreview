# Decision Log

- **Compose BOM 2024.02.00 + Compiler 1.5.10** to align with Kotlin 1.9.22. See `ASSUMPTIONS.md`.
- **Single module** for bootstrap speed; future split noted in ARCHITECTURE.md.
- **No DI** to keep binary small and complexity low for preview.
- **JDK 17** for AGP 8.7.2 compatibility.
- **Proto DataStore** for settings persistence with deterministic RNG seed (42) for reproducible tests.
- **Fixed timestep at 60Hz** with accumulated delta for smooth physics simulation.
- **Circle-rectangle collision** with clamp-and-early-out optimization for performance.
- **SoundPool with preloading** for low-latency audio feedback.
- **Compose Canvas** rendering with no per-frame allocations.
- **Accessibility-first design** with content descriptions and high contrast support.
(Updated 2025-09-09)
