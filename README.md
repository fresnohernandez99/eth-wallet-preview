# Kotlin Multiplatform Project

This is a Kotlin Multiplatform project aimed at showcasing a responsive user interface for both large and small screens, featuring an automatic theme and dynamic text. The project utilizes Compose for the UI and DataStore for storage on both Android and iOS.

## Project Structure

### `/composeApp`
This directory contains the code that will be shared across your Compose Multiplatform applications. It includes several subfolders:

- **`commonMain`**: Contains code that is common to all targets.
- **`iosMain`**: This is where platform-specific code for iOS resides. For instance, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app, this is the appropriate folder for such calls.
- Other folders specific to each platform that will contain Kotlin code compiled only for the indicated platform.

### `/iosApp`
This directory contains the iOS applications. Even if you’re sharing your UI with Compose Multiplatform, you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

## Project Features

- **Responsive User Interface**: Designed to adapt to different screen sizes, ensuring a smooth user experience on both large and small devices.
- **Automatic Theming**: The application supports automatic theme switching based on user preferences or system settings.
- **Dynamic Text**: Text content that can change based on user input or other factors, enhancing the interactivity of the app.
- **Custom Dependency Injection Model**: Implements a personalized dependency injection model using singletons in Kotlin, facilitating better management of dependencies throughout the application.
- **Wallet Registration Process**: Includes the complete process for registering a real wallet, utilizing native libraries in Kotlin for Android and iOS through a unified interface.

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin/multiplatform-dev/get-started.html)…
