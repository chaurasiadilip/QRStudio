# QRStudio Test

QRStudio is a comprehensive and highly customizable QR code generation library for Android. It allows developers to create QR codes with custom styles, including various dot shapes, eye ball designs, and frames.

## Features

- **Customizable Rendering**: Easily change the appearance of QR code dots, eye balls, and frames.
- **Social Media Content**: Built-in support for generating QR codes for Facebook, WhatsApp, Reddit, YouTube, and more.
- **SVG Support**: Export or render QR codes as SVGs for high-quality scaling.
- **Jetpack Compose Integration**: Seamlessly use QR codes in your Compose-based Android applications.
- **Flexible Core**: Extend the library to support new content types and rendering styles.

## Project Structure

The project is divided into several modules:

- **`:app`**: A sample Android application showcasing the library's features.
- **`:qrstudio-core`**: Contains the core logic for QR code content (e.g., `FacebookContent`, `WhatsAppContent`) and encoding.
- **`:qrstudio-renderer`**: The rendering engine that handles the visual representation of the QR code.
- **`:qrstudio-compose`**: Jetpack Compose UI components for displaying QR codes.
- **`:qrstudio-svg`**: Provides SVG rendering capabilities.

## Getting Started

### Prerequisites

- Android Studio Flamingo or newer
- JDK 17
- Android SDK 21+

### Installation

To use QRStudio in your project, add the following to your `build.gradle` file:

```kotlin
repositories {
    mavenCentral()
    // If using JitPack
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("com.github.samayteck:qrstudio-core:latest_version")
    implementation("com.github.samayteck:qrstudio-renderer:latest_version")
    implementation("com.github.samayteck:qrstudio-compose:latest_version")
}
```

## Usage

### Creating a Simple QR Code

```kotlin
val content = FacebookContent(username = "myprofile")
val qrCode = QrGenerator.generate(content)
```

### Customizing the Appearance

You can use different renderers to change the look of the QR code:

```kotlin
val renderer = DefaultDotRendererFactory.create(DotStyle.CIRCLE)
val frameRenderer = FrameRendererFactory.create(FrameStyle.BRACKET)
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
