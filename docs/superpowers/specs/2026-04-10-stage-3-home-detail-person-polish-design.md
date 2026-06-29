# Stage 3 Home Detail Person Polish Design

**Objective**

Apply a balanced cinematic-editorial polish to the home screen, media detail screen, and person detail screen so the app feels more premium, more intentional, and visually consistent with the earlier theme and premium-pass work.

**Scope**

- Keep all existing flows, navigation, and content structure intact.
- Improve visual hierarchy through stronger hero treatment, cleaner section rhythm, and more deliberate information panels.
- Focus on the most visible presentation layers only: home hero/header treatment, media detail composition, and person detail presentation.

**Design Direction**

This pass should sit between cinematic and editorial. The visuals should feel elevated and atmospheric, but still organized and readable.

- Use image-led hero areas where appropriate, but keep overlays and panels readable.
- Strengthen section transitions with spacing, surface treatment, and clearer grouping.
- Make headers feel curated rather than purely utilitarian.
- Keep cards, chips, and metadata panels aligned with the visual language introduced in the premium pass.

**Home Screen**

- Replace the simple title-only top area with a stronger hero-style brand panel.
- Make the app identity feel intentional with a stronger heading, supporting copy, and better section separation.
- Keep horizontal media rails, but improve the rhythm between sections and the way cards sit beneath the header.

**Media Detail Screen**

- Refine the hero area so the backdrop, poster, and metadata feel more integrated.
- Make quick actions and metadata read as intentional panels instead of loose elements.
- Improve the cadence of ratings, storyline, cast, information, parental guide, and recommendation sections with cleaner section spacing and more consistent card treatment.
- Keep the screen rich, but avoid visual clutter.

**Person Detail Screen**

- Upgrade the profile header so it feels closer in quality to the media detail presentation.
- Add a clearer identity panel for the person name and known-for label.
- Improve biography and known-for sections with better spacing and stronger supporting surfaces.

**Visual Rules**

- Favor layered surfaces and subtle borders over heavy shadows.
- Use the brand green for emphasis, not for large floods of color.
- Keep hero overlays dark enough to preserve text contrast.
- Reuse rounded geometry consistently across panels, chips, and cards.
- Maintain strong readability for all long-form content sections.

**Implementation Notes**

- Start with home to establish the hero/header pattern.
- Then apply the same polish logic to detail and person detail, adapting it to image-led layouts.
- Reuse existing theme tokens and premium-pass component language whenever possible.
- Avoid functional changes, new navigation, or deeper state rewrites in this pass.

**Validation**

- Home should feel more branded and deliberate at first glance.
- Media detail should feel more cohesive and premium without becoming busy.
- Person detail should visually belong to the same app family as media detail.
- The app should continue to build successfully after the polish changes.
