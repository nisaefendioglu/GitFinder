# GitFinder: GitHub User & Repository Explorer

GitFinder is an Android application built to explore GitHub users and their repositories. It allows users to search for GitHub profiles, view detailed information, browse their repositories, and manage a list of favorite users locally.

## Features

  * **User Search:** Quickly find GitHub users by their username.
  * **User Details:** View comprehensive information about a GitHub user, including:
      * Profile Picture 
      * Name, Bio
      * Followers & Following count
      * Public Repositories count
  * **Repository Listing:** See a list of public repositories for a selected user.
  * **Repository Navigation:** Click on any repository to open its original GitHub page in a web browser.
  * **Favorites Management:**
      * Add users to your favorites list directly from their profile or search results.
      * Remove users from your favorites.
      * View all your favorite users in a dedicated section.
  * **Local Persistence:** Favorite users are stored locally on the device using Room Database, ensuring they persist across app sessions.

## Technologies Used

This project leverages modern Android development best practices and a variety of robust libraries to deliver a performant and maintainable application:

  * **Language:** Kotlin
  * **Architecture:** Adheres to a clean architecture (MVVM with Use Cases) to ensure separation of concerns, testability, and maintainability.
  * **UI Toolkit:** Android Views (XML layouts with Fragments and Activities) for building the user interface.
  * **Concurrency:** Kotlin Coroutines & Flow (`kotlinx.coroutines.core`, `kotlinx.coroutines.android`) for efficient asynchronous operations and reactive programming.
  * **Dependency Injection:** Hilt (`hilt.android`, `hilt.android.compiler`) for robust and scalable dependency management.
  * **Networking:** Retrofit (`retrofit`, `retrofit.converter.gson`) for making API requests to GitHub, complemented by OkHttp Logging Interceptor (`okhttp.logging.interceptor`) for network call logging during development.
  * **Image Loading:** Glide (`glide`, `compiler`) for efficient and fast image loading and caching of user avatars.
  * **Local Database:** Room Persistence Library (`androidx.room.runtime`, `androidx.room.ktx`, `androidx.room.compiler`) for local data storage of favorite users.
  * **Navigation:** Android Jetpack Navigation Component (`androidx.navigation.fragment.ktx`, `androidx.navigation.ui.ktx`) for managing in-app navigation.
  * **UI Components:** AndroidX Core (`androidx.core.ktx`, `androidx.appcompat`, `material`, `androidx.constraintlayout`, `androidx.activity.ktx`, `androidx.fragment.ktx`, `androidx.recyclerview`) and Material Design components for a modern and consistent user interface.
  * **Unit Tests:**
      * Extensive unit tests are implemented for **ViewModels** and **Use Cases** to verify business logic, state transformations (using `StateFlow` / `LiveData`), and interactions with mocked dependencies.
      * Key libraries used for unit testing include `JUnit`, `Mockito-Kotlin`, `kotlinx-coroutines-test`, and `Turbine` (for `Flow` testing).
  * **Instrumented Tests:**
      * Instrumented tests are written for the **Room DAO (Data Access Object)** layer to validate persistence operations (insert, delete, query) against an in-memory database. These tests run on an Android device or emulator, ensuring real-world database interactions are correct.
      * `androidx.room.testing` and `androidx.arch.core:core-testing` are utilized for robust Room testing.


## Testing Strategy

The application adopts a comprehensive testing strategy to ensure reliability and maintainability:

  * **Unit Tests:**
      * Extensive unit tests are implemented for **ViewModels** and **Use Cases** to verify business logic, state transformations, and interactions with mocked dependencies.
      * Key libraries used for unit testing include `JUnit`, `Mockito-Kotlin`, `kotlinx-coroutines-test`, and `Turbine` (for `Flow` testing).
  * **Instrumented Tests:**
      * Instrumented tests are written for the **Room DAO (Data Access Object)** layer to validate persistence operations (insert, delete, query) against an in-memory database. These tests run on an Android device or emulator, ensuring real-world database interactions are correct.
      * `androidx.room.testing` and `androidx.arch.core:core-testing` are utilized for robust Room testing.

## Screens 
<img width="1568" alt="Screenshot 2025-06-18 at 8 40 48 PM" src="https://github.com/user-attachments/assets/7a2d284c-c152-4378-a4bf-22e34ee9e8dd" />

## Setup and Installation

To get a local copy up and running, follow these simple steps.

1.  **Clone the repo**
    ```
    git clone https://github.com/nisaefendioglu/GitFinder.git
    ```
2.  **Open in Android Studio**
    Open the cloned project in Android Studio.
3.  **Sync Gradle**
    Let Gradle sync and download all necessary dependencies.
4.  **Run on Device/Emulator**
    Run the application on an Android emulator or a physical device.

## API Usage

This application uses the **GitHub REST API**. No special API key is required for public user and repository data, but be mindful of [GitHub API rate limits](https://docs.github.com/en/rest/overview/rate-limits-for-the-rest-api).

