# Performance Guide

- Fixed-step simulation (e.g., 16_666_667 ns). Accumulate and step in while-loop inside frame.
- Use `withFrameNanos` to get timestamps for render pacing.
- Preallocate `Path`, `Rect`, arrays for pipes; reuse.
- Avoid allocations in draw lambdas; avoid boxing; prefer primitive arrays.
- Measure frame time and log percentile on debug builds only.
