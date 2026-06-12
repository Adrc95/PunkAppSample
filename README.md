# PunkAppSample

PunkAppSample is a sample Android application that explores modern Android development around the [Punk API](https://punkapi.com/).

The project focuses on a small but complete feature set:

- Beer listing with endless pagination
- Beer search within the current cached list
- Beer detail screen
- Local caching with Room
- Offline-first refresh behavior
- Pull-to-refresh support
- Splash screen integration

The codebase is organized as a multi-module project and follows a clean separation between UI, domain, and data concerns.

## Table of Contents

- Overview
- Features
- Architecture
- Modules
- Tech Stack
- Data Flow
- Testing
- Project Setup
- Quality Checks

## Overview

This app fetches beers from the network, persists them locally with Room, and exposes everything to the UI using Kotlin Flow.

The current implementation uses an offline-first approach for the main flows:

- the UI observes Room as the source of truth
- the repository refreshes data from the API when needed
- detail data is resolved from the local cache first and falls back to the network
- refreshed data is written back locally and reflected automatically through Room emissions

## Features

- Browse beers with paginated loading
- Search beers by name from the current cached list
- Open a beer detail screen with structured ingredient and food pairing information
- Refresh the list manually with pull-to-refresh
- Navigate through the app with the Android Navigation Component
- Start the app with the Android Splash Screen API

## Architecture

The app is structured around a multi-module setup with a clear separation of responsibilities:

```text
                         +------------------+
                         |       :app       |
                         | UI + Hilt root   |
                         +--------+---------+
                                  |
         +------------------------+------------------------+
         |                        |                        |
         v                        v                        v
   +-------------+         +-------------+         +-------------------------+
   |  :core:ui   |         | :core:common|         | :feature:beers:presentation |
   | base UI     |         | utils/ext   |         | list + detail UI            |
   +-------------+         +-------------+         +-------------+-----------+
                                                                |
                                                                v
                                                        +----------------------+
                                                        | :feature:beers:domain |
                                                        | use cases / models    |
                                                        +----------+-----------+
                                                                   |
                                                                   v
                                                        +----------------------+
                                                        |  :feature:beers:data |
                                                        | repos + data sources |
                                                        +----------+-----------+
                                                                   |
                                           +-----------------------+-----------------------+
                                           |                                               |
                                           v                                               v
                                  +----------------------+                       +----------------------+
                                  |     :core:network    |                       |    :core:database    |
                                  | Retrofit / OkHttp    |                       | Room                 |
                                  +----------------------+                       +----------------------+

                         +-----------------------------------------------+
                         | :feature:beers:testing                        |
                         | shared test builders / fixtures / utilities   |
                         +-----------------------------------------------+
```

This setup is backed by:

- `MVVM` for presentation logic
- Repository pattern for data access
- Clean separation between `presentation`, `domain`, and `data`
- `Hilt` for dependency injection
- `Room` as the local source of truth
- `Retrofit` for remote API access
- `Kotlin Flow` for reactive state propagation

## Modules

### App

- `app`
- Application entry point, navigation host, dependency wiring, and Android-specific setup

### Core

- `core:common`
- Shared utilities and common abstractions

- `core:ui`
- Base UI components and reusable presentation helpers

- `core:network`
- Retrofit service, DTOs, and network mappers

- `core:database`
- Room database, entities, DAOs, and local mappers

### Feature

- `feature:beers:domain`
- Business models, repository contract, and use cases

- `feature:beers:data`
- Repository implementation and data source orchestration

- `feature:beers:presentation`
- Fragments, view models, adapters, and UI state

- `feature:beers:testing`
- Shared test fixtures and builders

## Tech Stack

- Kotlin
- AndroidX
- Material Components
- View Binding
- Navigation Component
- Hilt
- Room
- Retrofit
- OkHttp
- Kotlin Coroutines and Flow
- Kotlin Serialization
- Glide
- SwipeRefreshLayout
- Shimmer
- JUnit
- MockK
- Turbine
- MockWebServer
- Detekt

## Data Flow

1. `BeerListFragment` and `DetailFragment` collect state from their ViewModels.
2. ViewModels delegate business operations to domain use cases.
3. `BeerRepository` coordinates network and local sources.
4. Room persists the cached beers and page metadata.
5. `Flow` emissions update the UI automatically when the database changes.

## Testing

The project includes:

- unit tests for domain use cases
- unit tests for network and database mappers
- instrumentation tests for repository behavior
- shared test builders for repeatable fixtures

## Project Setup

### Requirements

- Android Studio with Kotlin support
- JDK 21 or the JDK version required by your local Android setup
- Android SDK installed

### Run the app

1. Open the project in Android Studio.
2. Sync Gradle dependencies.
3. Run the `app` configuration on a device or emulator.

### Environment

The app uses the public Punk API, so no local backend setup is required.

## Quality Checks

Useful commands from the project root:

- `./gradlew test`
- `./gradlew connectedDebugAndroidTest`
- `./gradlew detekt`
- `./gradlew build`
