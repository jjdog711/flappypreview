# Style Guide (Kotlin + Compose)

- Use `val` and immutability by default. Pure render functions.
- Keep Composables small; pass render models not mutable state.
- Name: `ThingScreen`, `ThingUi`, `ThingState`.
- `remember` only for long-lived UI state; prefer ViewModel.
- No per-frame allocations in `Canvas` draw scope.
