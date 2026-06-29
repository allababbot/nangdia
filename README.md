# Nangdia

A modern Android movie & TV series discovery app built with Jetpack Compose. Nangdia helps you explore films, series, ratings, awards, and upcoming releases — powered by [TMDB](https://www.themoviedb.org/) and [MDBList](https://mdblist.com/).

## Features

- **Home** — Browse trending movies and TV series with ratings displayed on each card.
- **Search** — Quick and advanced search with filters by genre, rating, and year.
- **Detail Pages** — Rich metadata including Rotten Tomatoes scores, awards, streaming schedule, external links (MDBList, Rotten Tomatoes), and "More Like This" recommendations.
- **Persons** — View cast and crew, explore actor filmography.
- **Series** — Episode and season details for TV shows.
- **Calendar** — Interactive release calendar for upcoming movies and episodes.
- **Lists** — Create custom lists and browse trending curated lists.
- **Awards** — Dedicated awards index (Oscar, Emmy, etc.) with winners per year.
- **Profile & Settings** — UI customization, dark mode toggle, and account management.

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Kotlin |
| UI | Jetpack Compose |
| Architecture | MVVM + Clean Architecture |
| Dependency Injection | Hilt |
| Networking | Retrofit + OkHttp |
| Serialization | Kotlinx Serialization |
| Build | Gradle (KSP) |
| Min SDK | 26 (Android 8.0) |
| Target SDK | 34 (Android 14) |

## Project Structure

```
nangdia/
├── app/                  # Main application module
├── build.gradle.kts      # Root build configuration
├── settings.gradle.kts   # Project settings
├── gradle/               # Gradle wrapper & version catalog
├── di/                   # Dependency injection modules (shared)
├── docs/                 # API documentation (OpenAPI/Blueprint)
└── keystore/             # Signing keys (not committed)
```

## Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/allababbot/nangdia.git
   cd nangdia
   ```

2. Create a `local.properties` file in the root directory with your API keys:
   ```properties
   sdk.dir=/path/to/your/Android/Sdk
   mdblist.api.key=YOUR_MDBLIST_API_KEY
   tmdb.api.key=YOUR_TMDB_API_KEY
   release.store.file=keystore/release-test.jks
   release.store.password=YOUR_STORE_PASSWORD
   release.key.alias=YOUR_KEY_ALIAS
   release.key.password=YOUR_KEY_PASSWORD
   ```

3. Open the project in Android Studio or build from the command line:
   ```bash
   ./gradlew assembleDebug
   ```

## API Keys

This project requires API keys from:
- [TMDB](https://www.themoviedb.org/settings/api) — for movie/TV metadata
- [MDBList](https://mdblist.com/) — for ratings and external links

## License

This project is for personal/educational use. Please respect the terms of service of TMDB and MDBList when using this app.
