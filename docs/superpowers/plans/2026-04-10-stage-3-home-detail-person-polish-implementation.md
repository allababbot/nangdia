# Stage 3 Home Detail Person Polish Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Apply a cinematic-editorial polish pass to the home screen, media detail screen, and person detail screen.

**Architecture:** Strengthen hero/header treatment first so each screen has a clearer first impression, then refine section panels and supporting cards so the stage-2 premium language extends consistently into the image-led screens. Keep all navigation, data flow, and feature behavior unchanged.

**Tech Stack:** Kotlin, Jetpack Compose, Material 3, Coil

---

### Task 1: Polish Home Hero and Rail Rhythm

**Files:**
- Modify: `app/src/main/java/com/arisman/nangdia/presentation/home/HomeScreen.kt`

- [ ] Replace the simple top text block with a stronger hero panel.
- [ ] Improve spacing and section rhythm around the media rails.
- [ ] Upgrade home cards so they sit more intentionally under the hero treatment.

### Task 2: Polish Media Detail Composition

**Files:**
- Modify: `app/src/main/java/com/arisman/nangdia/presentation/detail/MediaDetailScreen.kt`

- [ ] Refine the backdrop hero, poster block, and metadata stack.
- [ ] Group storyline, ratings, info, parental guide, and recommendation areas with cleaner section cadence.
- [ ] Improve supporting surfaces and reusable cards without changing feature behavior.

### Task 3: Polish Person Detail Composition

**Files:**
- Modify: `app/src/main/java/com/arisman/nangdia/presentation/detail/PersonDetailScreen.kt`

- [ ] Upgrade the profile hero treatment and identity panel.
- [ ] Improve biography and known-for section surfaces and spacing.
- [ ] Align known-for cards with the premium card language used elsewhere.

### Task 4: Verify Stage 3 Build Health

**Files:**
- Verify: `app/src/main/java/com/arisman/nangdia/presentation/home/HomeScreen.kt`
- Verify: `app/src/main/java/com/arisman/nangdia/presentation/detail/MediaDetailScreen.kt`
- Verify: `app/src/main/java/com/arisman/nangdia/presentation/detail/PersonDetailScreen.kt`

- [ ] Run Kotlin compilation for touched files.
- [ ] Run a full debug assemble and confirm the polish changes build successfully.
