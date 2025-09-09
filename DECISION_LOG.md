# Decision Log

- **Compose BOM 2024.02.00 + Compiler 1.5.10** to align with Kotlin 1.9.22. See `ASSUMPTIONS.md`.
- **Single module** for bootstrap speed; future split noted in ARCHITECTURE.md.
- **No DI** to keep binary small and complexity low for preview.
- **JDK 17** for AGP 8.7.2 compatibility.
(Updated 2025-09-09)
