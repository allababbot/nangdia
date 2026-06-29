# Premium Polish Design

**Objective**

Give the app a second-pass visual polish so the new green, black, and white theme feels more premium, more deliberate, and more cohesive across the major user-facing screens.

**Scope**

- Keep the current screen structure, navigation model, and information hierarchy.
- Improve perceived quality through stronger headers, cleaner panel treatment, clearer depth, and more intentional empty/loading states.
- Focus on the highest-visibility screens and reusable components first: search, discover lists, watchlist, shared media cards, and app chrome.

**Design Direction**

This pass is a balanced premium treatment rather than a redesign. The app should feel sharper and more modern without changing how users move through it.

- Headers should feel editorial and confident, with stronger title-subtitle hierarchy.
- Search and filter controls should feel like crafted input surfaces instead of default fields.
- Cards should have clearer layering with subtle outlines, consistent corner radii, and stronger surface separation.
- Empty and loading states should feel intentional and branded, not like fallback placeholders.
- Navigation should stay compact but look more integrated with the dark green visual system.

**Visual Rules**

- Use `surface`, `surfaceVariant`, and `outline` to create layered depth instead of flat blocks.
- Favor soft borders and tonal contrast over heavy shadows.
- Use the brand green as a highlight, not as a flood color.
- Maintain generous spacing around headers and section openings so screens breathe more.
- Keep rounded shapes consistent across fields, cards, badges, and state panels.

**Screen Targets**

**Search**

- Add a stronger header block with title and short supporting copy.
- Turn the query field and filter trigger into intentional panels with clearer border and container treatment.
- Improve empty and no-result states with branded surface treatment and calmer messaging.

**Lists / Discover**

- Make the top header feel more curated and less utilitarian.
- Refine list cards with slightly richer layering, cleaner metadata grouping, and more polished chip treatment.
- Keep list exploration readable while improving visual rhythm between cards.

**Watchlist**

- Add a proper page header and make the empty state feel purposeful.
- Upgrade watchlist cards to match the richer search/list card language.
- Keep destructive actions obvious but visually contained.

**Shared Components**

- Reuse a consistent card language for media rows where possible.
- Align radius, border, and tonal treatment across reusable cards and panels.
- Keep loading indicators centered within more intentional surfaces when practical.

**Implementation Notes**

- Start by polishing the screen-level composition of search, lists, and watchlist.
- Then update reusable item surfaces so screen polish does not fragment into multiple visual languages.
- Prefer token-based theme usage and lightweight local composition changes over new custom design systems.
- Avoid functional changes, layout rewrites, or new navigation patterns in this pass.

**Validation**

- Major screens should feel visually related and more premium at a glance.
- Search, discover, and watchlist should each have stronger first-screen identity.
- Cards and panels should read distinctly from the background without looking busy.
- The build should continue to pass after the polish changes.
