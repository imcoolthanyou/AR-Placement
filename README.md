# AR-Placement


AR-Placement is an Android application built with Kotlin that demonstrates core concepts of augmented reality. It allows users to select a virtual object and then place it onto detected real-world surfaces using ARCore and Sceneform.

## Features

-   **Drill Selection UI**: A simple main screen allows users to select from a list of virtual "drills," displaying their description and usage tips.
-   **ARCore Plane Detection**: Utilizes ARCore to detect horizontal surfaces in the user's environment.
-   **Tap-to-Place Functionality**: Users can tap on a detected plane to place a 3D object in the AR scene.
-   **Sceneform 3D Rendering**: Employs the Sceneform framework to render and manage 3D objects.
-   **Interactive Objects**: Placed objects are interactive, allowing users to move, scale, and rotate them using touch gestures.
-   **Single Object Placement**: The application is configured to clear any previously placed object before adding a new one, ensuring only one object is active in the scene at a time.

## How It Works

1.  **Main Screen (`MainActivity.kt`)**: The application launches to a main screen where a dropdown menu (`Spinner`) presents a list of drills.
2.  **Selection**: When a user selects a drill, the UI updates to show an image, a description, and helpful tips for the selected item. The "Start AR Drill" button becomes visible.
3.  **Launch AR**: Tapping the "Start AR Drill" button starts the `ARViewActivity`.
4.  **AR Session (`ARViewActivity.kt`)**: This activity initializes an `ArFragment`, which handles the camera feed and AR session. The user is prompted to scan their surroundings by moving the phone.
5.  **Placement**: Once ARCore detects a surface, the user can tap on it. The app's `setOnTapArPlaneListener` triggers the `placeObject` function.
6.  **Object Creation**: A red 3D cube is created programmatically using `ShapeFactory` and `MaterialFactory`.
7.  **Anchoring**: An `AnchorNode` is created at the tapped location to fix the virtual object to the real-world position. The 3D cube is attached to this anchor and wrapped in a `TransformableNode` to make it interactive.

## Technologies Used

-   **Kotlin**: Primary programming language for the application.
-   **ARCore**: Google's platform for building augmented reality experiences. Used for motion tracking and environmental understanding.
-   **Sceneform**: A 3D framework that makes it easier to build ARCore apps without needing to learn OpenGL.
-   **Android SDK**: Standard toolkit for Android development.
-   **AndroidX Libraries**: Used for core components, UI, and application architecture.
-   **ViewBinding**: Used for safer and more concise interaction with UI views.

## Setup and Installation

To run this project, you will need an ARCore-compatible Android device.

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/imcoolthanyou/ar-placement.git
    ```
2.  **Open in Android Studio:**
    Open the cloned directory in the latest version of Android Studio.

3.  **Build and Run:**
    Let Gradle sync the dependencies. Then, build and run the `app` module on your connected ARCore-enabled device.

4.  **Grant Permissions:**
    The app will request camera permissions, which are required for the AR functionality to work.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
