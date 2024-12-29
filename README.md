# My3DViewer

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

The **3D-Viewer** is a robust and interactive Android application designed for viewing, manipulating, and customizing 3D models. The application leverages the *LibGDX* framework to offer seamless rendering and control over various 3D shapes like cubes, spheres, cylinders, and cones. It provides users with an intuitive interface to explore 3D objects, rotate them, resize them, and even change their colors or textures, enabling a fully personalized experience.

A standout feature of this application is the ability to upload custom textures from the device, making it ideal for professionals and hobbyists who wish to preview how different textures fit on their models. The real-time rotation and scaling controls allow users to visualize their designs from every angle, enhancing usability in industries such as architecture, game development, and product design.

With its clean design, touch-based navigation, and responsive controls, the 3D Model Viewer can be used for educational purposes, design prototyping, or as a simple yet powerful tool for experimenting with 3D geometry. This application is perfect for anyone looking to explore or work with 3D content on the go.

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `android`: Android mobile platform. Needs Android SDK.

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `android:lint`: performs Android project validation.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.
