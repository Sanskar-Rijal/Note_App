# Kei Na Kei Ta Ho App

A modern note-taking application built using Jetpack Compose that allows users to create, edit, and delete notes securely. The app incorporates biometric authentication to ensure that only authorized users can access their notes. 

## Functional Requirements

- **Biometric Authentication**: Upon launching the app, users are prompted to authenticate using their device's biometric methods (fingerprint,pin or phone pattern). Only after successful authentication, users are able to view and interact with their notes.
  
- **Create and Manage Notes**: Users can:
  - Add new notes with a title and description.
  - Edit existing notes by long-pressing on the note.
  - Delete notes by tapping the notes.
 
  - **Theme**: The app uses a dynamic color palette for Android 12 and above, adapting to the system-wide theme changes. For devices running below Android 12, the app defaults to a purple theme.

- **Jetpack Compose UI**: The app utilizes Jetpack Compose for building modern and responsive UIs.

## Tech Stack

- **Programming Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **UI Framework**: Jetpack Compose
- **State Management**: StateFlow
- **Navigation**: Navigation Component with Compose
- **Biometric Authentication**: Android Biometric API
- **Dependency Injection with Hilt**-This app utilizes Hilt for dependency injection to manage object creation and provide dependencies throughout the app.

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/SecureNoteApp.git
   ```

2. Open the project in Android Studio.

3. Build and run the project on your Android device/emulator with biometric support.


### Authentication

When the app is opened, users are immediately prompted with a biometric authentication screen. Authentication can be done using fingerprint, face recognition, or device credentials (depending on the device's capabilities).

### Notes

- **Add a New Note**: Use the floating action button (FAB) on the note screen to create a new note. Enter a title and description, then save the note.
- **Edit a Note**: Long-press on an existing note to update its title and description.
- **Delete a Note**: Swipe or click the delete icon on a note to remove it from the list.

## Screenshots

  
