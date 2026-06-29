# Search, Rating, and List Polish Design

**Objective**

Unify rating presentation, media-type labeling, advanced search behavior, and supporting media-card layouts so the search, discover, detail, watchlist, calendar, and person flows feel consistent and correct.

**Scope**

- Fix missing or incorrectly rendered rating data where the app already receives rating information, with special attention to Metacritic user ratings in media detail.
- Replace inconsistent media-type labels with a single user-facing convention: `Movie` and `TV Series`.
- Upgrade advanced search so its filters and results match the capabilities and visual treatment of the main search flow.
- Extend rating visibility to the key list and card surfaces that currently omit it.
- Correct date formatting in film detail information and top spacing on the discover screen.

**Design Direction**

This pass should feel corrective and cohesive rather than flashy. The goal is to make the app read as one system: the same title should carry the same type label, rating treatment, and card language wherever it appears.

- Ratings should feel dependable, compact, and easy to compare across surfaces.
- Search and advanced search should look like siblings, not separate products.
- Shared media cards should follow one visual hierarchy, then adapt by density for each screen.
- Formatting should be localized where the app already reads like an Indonesian product, especially for full dates.

**Shared Rules**

- Standardize user-facing media-type copy to `Movie` and `TV Series`.
- Reuse one rating-display rule for percentage-like scores across search, discover, watchlist, calendar, home, recommendations, and known-for surfaces.
- Preserve existing navigation and data sources unless a small query-layer expansion is required for the new year-range behavior.
- Prefer extending current shared components over duplicating card implementations across screens.

**Ratings and Data Formatting**

- Audit the detail-rating mapping path from DTO to domain model to UI so `Metacritic User` ratings are not dropped or mis-normalized.
- Treat rating-source formatting as a single concern:
  - normalize score/value handling
  - preserve source-specific display formats in dialogs
  - keep compact chip/card displays on a normalized 0-100 scale where appropriate
- Ensure every screen that newly shows ratings has a graceful fallback when a score is missing.

**Advanced Search**

- Replace the single year input with a year range model:
  - optional start year
  - optional end year
  - support empty, lower-bound-only, upper-bound-only, and full-range cases
- Keep the existing filter model, but update the state and repository path so year range reaches the discover request layer.
- Replace the current advanced-search result item styling with the same shared visual language used by regular search results.
- Show rating on advanced-search result items.
- Fix result-type labels so TV results are not shown as movies.

**Shared Media Cards**

- Consolidate media-result presentation around reusable card building blocks instead of separate one-off layouts.
- Update the following surfaces to show rating when data exists:
  - home media cards
  - `More Like This`
  - `Known For`
  - discover list items
  - watchlist items
  - calendar items
- Keep each surface appropriately dense:
  - compact rows can use a small rating pill
  - poster-led cards can use a small footer/meta block

**Detail and Formatting Polish**

- In film detail `Information`, render dates in Indonesian long-date style such as `15 Maret 2026`.
- Apply the formatter consistently to theatrical and digital release dates when shown.
- Keep the raw stored/API value unchanged; the polish belongs in presentation formatting.

**Discover Layout**

- Add top inset behavior to the discover screen so the header no longer collides with the status bar.
- Match the spacing pattern already used by the search and watchlist screens to keep top-level navigation surfaces aligned.

**Implementation Notes**

- Start with shared utilities:
  - media-type label helper
  - date formatter
  - rating normalization/display helper adjustments
- Then update advanced-search state and repository contracts for year ranges.
- After the shared foundations are stable, migrate each affected surface to the unified media-card treatment.
- Keep refactoring targeted. This pass should reduce duplication where it directly supports consistency, but should not become a broad design-system rewrite.

**Validation**

- Metacritic user ratings should appear when present in the source payload.
- Advanced search should return the correct content type label and support year-range filtering without breaking existing filters.
- Advanced-search results should visually match the regular search result style and include ratings.
- Ratings should appear consistently on home, recommendations, known-for, discover items, watchlist, and calendar when available.
- Film-detail information dates should render in Indonesian month names.
- Discover should no longer place its header against the status bar.
- The app should continue to compile successfully after the pass.
