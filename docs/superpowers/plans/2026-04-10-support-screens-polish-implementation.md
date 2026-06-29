# Support Screens Polish Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Polish the support screens and supporting detail components so they visually match the premium styling of the rest of the app.

**Architecture:** Strengthen screen-level headers and grouped panels first, then align supporting components like calendar rows, season selectors, and episode cards to the same border, radius, and surface language. Keep all behavior unchanged and focus on presentation only.

**Tech Stack:** Kotlin, Jetpack Compose, Material 3

---

### Task 1: Polish Calendar Screen

**Files:**
- Modify: `app/src/main/java/com/arisman/nangdia/presentation/calendar/CalendarScreen.kt`
- Modify: `app/src/main/java/com/arisman/nangdia/presentation/calendar/components/CalendarItem.kt`

- [ ] Add a stronger header and month-navigation presentation.
- [ ] Refine the calendar panel, date-state styling, and release list context.
- [ ] Upgrade release rows and empty/loading states to match the premium card language.

### Task 2: Polish Advanced Search Screen

**Files:**
- Modify: `app/src/main/java/com/arisman/nangdia/presentation/search/AdvancedSearchScreen.kt`

- [ ] Group filters into clearer panels with stronger editorial headers.
- [ ] Improve filter controls, year input, slider, and CTA treatment.
- [ ] Refine results, loading, and empty/error states.

### Task 3: Polish Seasons and Episode Support Screens

**Files:**
- Modify: `app/src/main/java/com/arisman/nangdia/presentation/detail/MediaSeasonsScreen.kt`
- Modify: `app/src/main/java/com/arisman/nangdia/presentation/detail/components/TvSeriesComponents.kt`

- [ ] Improve the show header and season selector hierarchy.
- [ ] Upgrade episode cards and support component surfaces.
- [ ] Keep support components visually aligned with the detail screen polish.

### Task 4: Verify Build Health

**Files:**
- Verify: `app/src/main/java/com/arisman/nangdia/presentation/calendar/CalendarScreen.kt`
- Verify: `app/src/main/java/com/arisman/nangdia/presentation/calendar/components/CalendarItem.kt`
- Verify: `app/src/main/java/com/arisman/nangdia/presentation/search/AdvancedSearchScreen.kt`
- Verify: `app/src/main/java/com/arisman/nangdia/presentation/detail/MediaSeasonsScreen.kt`
- Verify: `app/src/main/java/com/arisman/nangdia/presentation/detail/components/TvSeriesComponents.kt`

- [ ] Run Kotlin compilation after the UI updates.
- [ ] Run a full debug assemble and confirm the app still builds successfully.
