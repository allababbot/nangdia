# Support Screens Polish Design

**Objective**

Bring the calendar, advanced search, seasons/episodes, and supporting detail components up to the same visual quality as the earlier premium passes, while keeping these screens efficient and easy to use.

**Scope**

- Preserve current workflows, interaction model, and data presentation.
- Improve headers, panel grouping, selected states, empty/loading states, and reusable support components.
- Focus on `CalendarScreen`, `AdvancedSearchScreen`, `MediaSeasonsScreen`, `CalendarItem`, and TV-series support components.

**Design Direction**

This pass should feel balanced: clearer and more premium than before, but still practical for filter-heavy and utility-style screens.

- Headers should feel deliberate and connected to the rest of the app.
- Filters, selectors, and support cards should read as intentional panels, not bare controls.
- Empty and loading states should feel branded instead of fallback.
- Component treatment should stay consistent with the card/panel language established in stage 2 and stage 3.

**Calendar**

- Add a stronger header treatment and improve month navigation presentation.
- Make the calendar grid and selected date context feel more structured.
- Improve selected state, today state, and release-item presentation without hurting readability.

**Advanced Search**

- Present filters in clearer grouped panels with stronger titles and supporting context.
- Make chips, year input, slider, and apply/reset actions feel more intentional.
- Improve results and empty/error states so the screen still feels polished after filters are applied.

**Seasons / Episodes**

- Improve hierarchy between the mini show header, season selector, and episode list.
- Make episode cards feel more refined and easier to scan.
- Ensure support components feel visually aligned with the detail screen work.

**Implementation Notes**

- Reuse existing theme tokens and the premium panel language from previous stages.
- Prefer lightweight structure and styling improvements over new abstractions unless they clearly reduce duplication.
- Avoid functional or navigation changes.

**Validation**

- Utility screens should feel visually finished and consistent with the rest of the app.
- Filter and selector screens should remain fast to scan and operate.
- The app should continue to compile and assemble successfully after the polish changes.
