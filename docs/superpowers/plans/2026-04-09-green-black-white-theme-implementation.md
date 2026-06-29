# Green Black White Theme Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Retheme the Android app so the full UI uses a green, black, and white palette aligned with the new launcher icon.

**Architecture:** Update the Material 3 theme tokens first so all theme-aware screens inherit the new brand palette. Then clean up key UI files that still hardcode legacy gold, yellow, and red values so the visual identity is consistent across home, detail, search, shimmer, ratings, and navigation surfaces.

**Tech Stack:** Kotlin, Jetpack Compose, Material 3, Android resource drawables

---

### Task 1: Establish Brand Theme Tokens

**Files:**
- Modify: `app/src/main/java/com/arisman/nangdia/ui/theme/Color.kt`
- Modify: `app/src/main/java/com/arisman/nangdia/ui/theme/Theme.kt`

- [ ] Replace legacy gold and red palette with green, black, and white theme roles.
- [ ] Fill in full Material 3 dark and light color schemes, including background, surface, surfaceVariant, primaryContainer, and outline roles.
- [ ] Disable dynamic color by default so system colors do not override the brand palette.
- [ ] Keep status bar handling aligned with the active theme brightness.

### Task 2: Align Shared App Chrome

**Files:**
- Modify: `app/src/main/java/com/arisman/nangdia/MainActivity.kt`
- Modify: `app/src/main/java/com/arisman/nangdia/presentation/home/HomeShimmer.kt`

- [ ] Apply theme-backed colors to the bottom navigation bar, labels, and surrounding surfaces.
- [ ] Update shimmer placeholders to use dark green-tinted surfaces instead of neutral light gray.

### Task 3: Replace Hardcoded Rating and Badge Colors

**Files:**
- Modify: `app/src/main/java/com/arisman/nangdia/util/RatingFormatUtil.kt`
- Modify: `app/src/main/java/com/arisman/nangdia/presentation/home/HomeScreen.kt`
- Modify: `app/src/main/java/com/arisman/nangdia/presentation/search/components/MediaItem.kt`
- Modify: `app/src/main/java/com/arisman/nangdia/presentation/detail/MediaDetailScreen.kt`

- [ ] Move rating colors to a palette that matches the new theme.
- [ ] Update home and search score badges to use the shared brand-aligned thresholds.
- [ ] Replace legacy gold star, awards badge, and parental guide severity colors in the detail screen with the new palette.
- [ ] Ensure on-color text remains readable on filled rating surfaces.

### Task 4: Verify Theme Integration

**Files:**
- Verify: `app/src/main/java/com/arisman/nangdia/ui/theme/Color.kt`
- Verify: `app/src/main/java/com/arisman/nangdia/ui/theme/Theme.kt`
- Verify: `app/src/main/java/com/arisman/nangdia/MainActivity.kt`
- Verify: `app/src/main/java/com/arisman/nangdia/presentation/home/HomeScreen.kt`
- Verify: `app/src/main/java/com/arisman/nangdia/presentation/home/HomeShimmer.kt`
- Verify: `app/src/main/java/com/arisman/nangdia/presentation/search/components/MediaItem.kt`
- Verify: `app/src/main/java/com/arisman/nangdia/presentation/detail/MediaDetailScreen.kt`
- Verify: `app/src/main/java/com/arisman/nangdia/util/RatingFormatUtil.kt`

- [ ] Run a build command that exercises resources and Compose compilation for the touched files.
- [ ] Record whether failures come from the retheme work or from the existing unrelated Kotlin issues already present in the project.
