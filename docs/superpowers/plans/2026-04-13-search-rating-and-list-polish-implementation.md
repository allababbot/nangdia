# Search, Rating, and List Polish Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Implement the approved polish pass for advanced search, rating consistency, localized dates, discover spacing, and shared media-card presentation.

**Architecture:** Start by adding small presentation utilities and regression tests for rating normalization, media-type labeling, and Indonesian date formatting. Then extend the discover/advanced-search data path to support year ranges and correct media-type fallback, and finally apply the shared presentation rules across the affected Compose screens and watchlist persistence.

**Tech Stack:** Kotlin, Jetpack Compose, Room, Retrofit, JUnit4

---

### Task 1: Add shared formatting utilities and regression tests

**Files:**
- Create: `app/src/test/java/com/arisman/nangdia/util/RatingFormatUtilTest.kt`
- Create: `app/src/test/java/com/arisman/nangdia/util/MediaPresentationUtilTest.kt`
- Create: `app/src/main/java/com/arisman/nangdia/util/MediaPresentationUtil.kt`
- Modify: `app/src/main/java/com/arisman/nangdia/util/RatingFormatUtil.kt`

- [ ] Write failing tests for Metacritic user normalization, display labels, and Indonesian long-date formatting.
- [ ] Run the focused unit tests and confirm they fail for the expected reasons.
- [ ] Implement the minimal utility changes to make those tests pass.
- [ ] Re-run the focused unit tests until they pass.

### Task 2: Fix advanced-search data and state flow

**Files:**
- Modify: `app/src/main/java/com/arisman/nangdia/domain/repository/MdbListRepository.kt`
- Modify: `app/src/main/java/com/arisman/nangdia/data/repository/MdbListRepositoryImpl.kt`
- Modify: `app/src/main/java/com/arisman/nangdia/data/remote/MdbListRemoteDataSource.kt`
- Modify: `app/src/main/java/com/arisman/nangdia/data/remote/TmdbService.kt`
- Modify: `app/src/main/java/com/arisman/nangdia/data/mapper/MediaMapper.kt`
- Modify: `app/src/main/java/com/arisman/nangdia/presentation/search/AdvancedSearchViewModel.kt`

- [ ] Add failing tests or assertions where possible for fallback media type and year-range formatting helpers.
- [ ] Change discover contracts from single year to optional year start and year end.
- [ ] Pass movie/show fallback media type through TMDB discover mapping so advanced-search labels are correct.
- [ ] Re-run the focused unit tests and compile checks for the touched contracts.

### Task 3: Apply unified presentation across screens

**Files:**
- Modify: `app/src/main/java/com/arisman/nangdia/presentation/search/AdvancedSearchScreen.kt`
- Modify: `app/src/main/java/com/arisman/nangdia/presentation/search/components/MediaItem.kt`
- Modify: `app/src/main/java/com/arisman/nangdia/presentation/search/components/SearchResultItem.kt`
- Modify: `app/src/main/java/com/arisman/nangdia/presentation/home/HomeScreen.kt`
- Modify: `app/src/main/java/com/arisman/nangdia/presentation/detail/MediaDetailScreen.kt`
- Modify: `app/src/main/java/com/arisman/nangdia/presentation/detail/PersonDetailScreen.kt`
- Modify: `app/src/main/java/com/arisman/nangdia/presentation/lists/ListsScreen.kt`
- Modify: `app/src/main/java/com/arisman/nangdia/presentation/watchlist/WatchlistScreen.kt`
- Modify: `app/src/main/java/com/arisman/nangdia/presentation/calendar/components/CalendarItem.kt`

- [ ] Update advanced-search filters to use `from` and `to` year inputs.
- [ ] Replace advanced-search result styling with the regular search result style and include ratings.
- [ ] Apply the shared type label and rating treatment to home, recommendations, known-for, discover items, watchlist, and calendar.
- [ ] Format detail information dates with the Indonesian long-date utility.
- [ ] Add top status-bar spacing to the discover screen.

### Task 4: Persist watchlist score and finish verification

**Files:**
- Modify: `app/src/main/java/com/arisman/nangdia/domain/model/WatchlistMedia.kt`
- Modify: `app/src/main/java/com/arisman/nangdia/data/local/entity/WatchlistEntity.kt`
- Modify: `app/src/main/java/com/arisman/nangdia/data/local/PilemDatabase.kt`
- Modify: `app/src/main/java/com/arisman/nangdia/presentation/detail/MediaDetailViewModel.kt`
- Modify: `app/src/main/java/com/arisman/nangdia/presentation/calendar/CalendarViewModel.kt`

- [ ] Add score to watchlist persistence so watchlist and calendar can render ratings from local data.
- [ ] Update watchlist creation and calendar mapping to propagate the saved score.
- [ ] Run targeted unit tests.
- [ ] Run `./gradlew :app:compileDebugKotlin` and fix any remaining compile issues.
