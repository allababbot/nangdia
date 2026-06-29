# Premium Polish Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Apply a balanced premium polish pass to the app's major screens so the new green-black-white theme feels more intentional and refined.

**Architecture:** Improve screen-level composition first by strengthening headers, panel surfaces, and empty states on the search, lists, and watchlist screens. Then align shared row and card components to the same depth, border, and spacing language so the polish feels cohesive across the app.

**Tech Stack:** Kotlin, Jetpack Compose, Material 3

---

### Task 1: Polish Search Screen Composition

**Files:**
- Modify: `app/src/main/java/com/arisman/nangdia/presentation/search/SearchScreen.kt`

- [ ] Add a stronger search header with supporting copy.
- [ ] Wrap the query field and filter trigger in more intentional panel treatment.
- [ ] Replace plain empty states with branded surfaces and calmer messaging.

### Task 2: Polish Discover Screen Composition

**Files:**
- Modify: `app/src/main/java/com/arisman/nangdia/presentation/lists/ListsScreen.kt`

- [ ] Refine the top header with clearer title/subtitle hierarchy and supporting panel treatment.
- [ ] Add more deliberate spacing and metadata grouping in list cards.
- [ ] Improve loading and empty/error treatment with stronger surface structure.

### Task 3: Polish Watchlist Screen Composition

**Files:**
- Modify: `app/src/main/java/com/arisman/nangdia/presentation/watchlist/WatchlistScreen.kt`

- [ ] Add a proper page header and subtitle.
- [ ] Replace the plain empty state with a panel-style branded message.
- [ ] Upgrade watchlist rows to use richer card treatment and clearer destructive action framing.

### Task 4: Align Shared Card Language

**Files:**
- Modify: `app/src/main/java/com/arisman/nangdia/presentation/search/components/MediaItem.kt`
- Modify: `app/src/main/java/com/arisman/nangdia/presentation/search/components/SearchResultItem.kt`

- [ ] Add subtle outline and stronger container treatment to shared media rows.
- [ ] Refine badge, metadata, and spacing treatment to match the premium pass.

### Task 5: Verify UI Polish Build Health

**Files:**
- Verify: `app/src/main/java/com/arisman/nangdia/presentation/search/SearchScreen.kt`
- Verify: `app/src/main/java/com/arisman/nangdia/presentation/lists/ListsScreen.kt`
- Verify: `app/src/main/java/com/arisman/nangdia/presentation/watchlist/WatchlistScreen.kt`
- Verify: `app/src/main/java/com/arisman/nangdia/presentation/search/components/MediaItem.kt`
- Verify: `app/src/main/java/com/arisman/nangdia/presentation/search/components/SearchResultItem.kt`

- [ ] Run a full debug build and confirm the polish changes compile cleanly.
