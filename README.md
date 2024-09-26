# Kei Na Kei Ta Ho App

A modern note-taking application built using Jetpack Compose that allows users to create, edit, and delete notes securely. The app incorporates biometric authentication to ensure that only authorized users can access their notes. 

## Functional Requirements

- **Biometric Authentication**: Upon launching the app, users are prompted to authenticate using their device's biometric methods (fingerprint,pin or phone pattern). Only after successful authentication, users are able to view and interact with their notes.
  
- **Create and Manage Notes**: Users can:
  - Add new notes with a title and description.
  - Edit existing notes by long-pressing on the note.
  - Delete notes by tapping the notes.
 
  - **Theme**: The app uses a dynamic color palette for Android 12 and above, adapting to the system-wide theme changes. For devices running below Android 12, the app defaults to a purple theme.

### **Non-functional Requirements**

1. **Security:**
   - The app uses biometric authentication (fingerprint or phone pattern) to secure access to notes, ensuring that only authenticated users can view and modify their content.

2. **Performance:**
   - The app should load quickly and be responsive, providing smooth navigation between screens, even with a large number of notes.
   - Biometric authentication should process efficiently, with minimal delay in granting access to the notes.

3. **Reliability:**
   - The app must handle errors gracefully, such as failed biometric authentication or hardware unavailability.
   - It should ensure data consistency, with reliable saving, updating, and deleting of notes.

4. **Usability:**
   - The app follows modern Android UI guidelines with Material 3 design, ensuring a user-friendly and intuitive experience.
   - It adapts to different screen sizes and orientations (portrait and landscape modes).

5. **Compatibility:**
   - The app supports Android 8 (API level 26) and above, with dynamic theming for devices running Android 12 and later, ensuring compatibility across a range of devices.

6. **Maintainability:**
   - The app is modular, with clearly defined components and view models, making it easy to maintain and extend.
   - Hilt is used for dependency injection, ensuring that code is clean, manageable, and easy to test.

7. **Extensibility:**
   - The app is designed to allow for future expansions, such as adding more note-related features (e.g., categorization, reminders) or additional security features.

8. **Data Privacy:**
   - User notes are stored locally on the device.
   - The app does not send or share any user data externally.

9. **Efficiency:**
    - The app minimizes resource usage, ensuring low battery consumption and efficient memory usage, even during background operations.

---

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


> Splash Screen Animation And Biometrics
<img src="https://media.giphy.com/media/e4L7BqzVTdwYadBMDm/giphy.gif" height=400 width=200/>  

> Adding Note
<img src="https://media.giphy.com/media/ytDg2vnqPADH8oznIj/giphy.gif" height=400 width=200/>



## Screenshots

<div align="start">
  <a href="https://imgur.com/XUf6Sr9">
    <img src="https://i.imgur.com/XUf6Sr9.gif" title="source: imgur.com" height="400" width="200" style="margin-right: 20px;" />
  </a>
  <a href="https://imgur.com/5PrNOAn">
    <img src="https://i.imgur.com/5PrNOAn.gif" title="source: imgur.com" height="400" width="200" style="margin-right: 20px;" />
  </a>
  <a href="https://imgur.com/folEKUc">
    <img src="https://i.imgur.com/folEKUc.gif" title="source: imgur.com" height="400" width="200" style="margin-right: 20px;" />
  </a>
  <a href="https://imgur.com/oDF1z2j">
    <img src="https://i.imgur.com/oDF1z2j.gif" title="source: imgur.com" height="400" width="200" style="margin-right: 20px;" />
  </a>
  <a href="https://imgur.com/3gOI1HG">
    <img src="https://i.imgur.com/3gOI1HG.gif" title="source: imgur.com" height="400" width="200" />
  </a>
</div>


  
