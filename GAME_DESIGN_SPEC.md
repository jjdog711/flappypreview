# Game Design Spec

- **States**: Ready → Playing → GameOver.
- **Controls**: Tap to flap (impulse). No multi-touch requirements.
- **Scoring**: +1 for each pipe pair passed.
- **Difficulty**: Pipe gap narrows over time; pipe speed slightly increases.
- **Spawn Rules**: Min/Max gap; vertical jitter clamped; min distance between pairs.
- **Hitboxes**: Bird circle; pipes rectangles; ground/ceiling as bounds.
- **HUD**: Score (top-center), pause (top-right), best score (game over).
- **Pause/Resume**: Freeze simulation; dim overlay.
- **Accessibility**: Reduced Motion toggle, High Contrast mode, content descriptions.
