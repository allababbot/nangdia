# Green Black White Theme Design

**Objective**

Retheme the Android application so its visual identity matches the new launcher icon: dark, modern, and high-contrast with green as the primary accent, near-black backgrounds, and white foreground text.

**Scope**

- Replace the existing gold and red theme tokens with a green, black, and white palette.
- Preserve both dark and light theme support, but align both themes with the new brand identity.
- Disable dynamic Material color by default so the brand palette remains stable across devices.
- Audit and update key Compose screens and reusable UI elements that still use hardcoded colors.

**Palette**

The palette should stay close to the launcher icon colors so the app feels cohesive from icon to interface.

- Primary green: `#2E8B57`
- Dark green accent: `#1F5F3C`
- Near-black background: `#08110D`
- Elevated surface: `#101A15`
- Surface variant: `#17231D`
- Primary text on dark: `#FFFFFF`
- Secondary text on dark: `#C9D6CF`
- Divider and inactive state: `#31443A`
- Light background: `#F5FAF7`
- Light surface: `#FFFFFF`
- Primary text on light: `#0D1712`
- Secondary text on light: `#456154`

**Theme Rules**

- `primary` should use the launcher green and drive buttons, active tabs, focused fields, and prominent highlights.
- `background` should be a deep black-green instead of neutral gray or pure black.
- `surface` should be slightly lighter than the background so cards and sheets remain readable.
- `onBackground` and `onSurface` should be high-contrast white or near-black depending on theme.
- `secondary`, `outline`, `surfaceVariant`, and `onSurfaceVariant` should be defined explicitly to avoid fallback mismatches.
- Error colors can remain Material defaults unless current UI requires custom treatment.
- `dynamicColor` should default to `false`.

**Component Cleanup Targets**

The app likely mixes theme tokens and hardcoded colors. These areas should be brought back under the theme:

- Home screen branding text, scaffold, score badges, and media cards.
- Search and advanced search inputs, chips, and selected states.
- Detail screens, metadata pills, call-to-action buttons, and supporting labels.
- Lists, watchlist, calendar, and top-level navigation surfaces.
- Loading indicators, shimmer placeholders, and empty or error states where local colors are set directly.

**Score and Status Treatment**

- High scores should use a bright green that harmonizes with the primary palette.
- Mid scores should use an olive or muted green-yellow rather than a saturated amber that clashes with the new identity.
- Low scores can use a restrained warm red for readability, but it should appear only where semantic feedback is needed.

**Implementation Notes**

- Start with `ui/theme/Color.kt` and `ui/theme/Theme.kt` to establish new tokens and full Material 3 color roles.
- Then update screens with obvious hardcoded colors, beginning with the home screen because it reflects the app identity immediately.
- Favor `MaterialTheme.colorScheme.*` lookups over local `Color(...)` declarations in screen code.
- Keep typography and layout unchanged unless a color dependency makes a small adjustment necessary.

**Validation**

- Verify text contrast remains readable on all major surfaces in both dark and light themes.
- Confirm launcher icon and in-app colors feel visually connected.
- Ensure active states are clearly visible and inactive states are still legible.
- Build should at minimum pass Android resource and Compose compilation for touched theme files; existing unrelated Kotlin issues may still block full assembly.
