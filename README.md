# FirstKotlinApp - Daily Planner & Tracker

A simple, efficient Android application built with Jetpack Compose to help users manage their daily tasks, track habits, and schedule events.

## 🚀 Features

- **To-Do List**: Manage daily tasks with ease. Add, complete, and delete tasks in a streamlined interface.
- **Habit Tracker**: Build better routines by tracking daily habits with an integrated streak counter.
- **Calendar**: View and schedule upcoming events. Features a simple month-view overview and event management.
- **Seamless Navigation**: Smooth transition between screens using a modern Bottom Navigation Bar.
- **Optimized Performance**: Small app footprint and fast execution thanks to R8 optimizations.

## 🛠 Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose (Material 3)
- **Navigation**: Jetpack Navigation Compose
- **Architecture**: Simple state management with `remember` and `mutableStateOf`
- **Minimum SDK**: API 26 (Android 8.0) - required for modern `java.time` support.

## 🧠 Challenges & Solutions

### 1. The "Duplicate Deletion" Bug
**Problem**: When deleting the last task in the list, sometimes the task above it would also disappear.
**Cause**: The app originally used `tasks.size + 1` to generate IDs. If tasks were deleted, this led to duplicate IDs for new tasks. Since deletion was based on ID, removing one task removed all others with the same ID.
**Solution**: 
- Implemented **Unique ID Generation** by finding the current maximum ID in the list and incrementing it: `(tasks.maxOfOrNull { it.id } ?: 0) + 1`.
- Added **LazyColumn Keys**: Used the `key` parameter in `LazyColumn` items to help Compose uniquely identify and track each element, preventing UI glitches during list mutations.

### 2. App Size Optimization
**Problem**: The initial build of the app was roughly 18 MB, which is large for a simple utility app.
**Solution**:
- **R8 Full Mode**: Enabled `android.enableR8.fullMode=true` in `gradle.properties` for more aggressive code stripping and optimization.
- **Resource Shrinking**: Enabled `isMinifyEnabled` and `isShrinkResources` in the release build type.
- **Dependency Refinement**: Switched from `material-icons-extended` (which includes thousands of icons) to `material-icons-core`. By using standard icons like `Icons.Default.List` and `Icons.Default.DateRange`, we eliminated a massive library without sacrificing UI quality.

### 3. API Compatibility
**Problem**: Implementing the Calendar required robust date handling, but standard `java.time` APIs were causing issues on older Android versions.
**Solution**: Increased the `minSdk` to **26** to leverage native Java 8 Time APIs, ensuring reliable date formatting and manipulation for the calendar features.

## 📸 Screenshots

*(Add your screenshots here)*

## 🛠 Installation

1. Clone this repository.
2. Open the project in **Android Studio Ladybug (or newer)**.
3. Sync the Gradle project.
4. Run the app on an emulator or physical device (API 26+).
