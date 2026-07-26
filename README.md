# QRStudio Test

QRStudio is a comprehensive and highly customizable QR code generation library for Android. It allows developers to create QR codes with custom styles, including various dot shapes, eye ball designs, and frames.

## Features

- **Customizable Rendering**: Easily change the appearance of QR code dots, eye balls, and frames.
- **Logo Support**: Embed logos (Bitmap, SVG, or Vector) in the center of your QR codes with customizable size and background.
- **Gradient Support**: Apply beautiful linear or radial gradients to your QR codes.
- **Social Media Content**: Built-in support for generating QR codes for:
  - Facebook, Instagram, TikTok, X (Twitter)
  - WhatsApp, Telegram
  - LinkedIn, YouTube, and more.
- **SVG Support**: Export or render QR codes as SVGs for high-quality scaling.
- **Jetpack Compose Integration**: Seamlessly use the `StyledQrCode` composable in your Android apps.

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
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("com.github.chaurasiadilip:qrstudio-core:latest_version")
    implementation("com.github.chaurasiadilip:qrstudio-renderer:latest_version")
    implementation("com.github.chaurasiadilip:qrstudio-compose:latest_version")
}
```

## Usage

### Simple QR Code Generation

```kotlin
val content = FacebookContent(username = "myprofile")
val qrCode = QrGenerator.generate(content)
```

### Advanced Customization (View System)

```kotlin
val options = StyledQrOptions(
    content = FacebookContent("samayteck"),
    dotRenderer = DefaultDotRendererFactory.create(DotStyle.CIRCLE),
    eyeBallRenderer = CircleEyeBallRenderer(),
    frameRenderer = BracketFrameRenderer(),
    gradientStyle = LinearGradientStyle(startColor = Color.BLUE, endColor = Color.CYAN),
    logoOptions = LogoOptions(
        logoRenderer = SvgLogoRenderer(context, R.raw.my_logo),
        logoPercent = 0.2f
    )
)
```

### Jetpack Compose Integration

```kotlin
@Composable
fun MyQrScreen() {
    val options = remember {
        StyledQrOptions(
            content = InstagramContent("samayteck"),
            dotRenderer = DefaultDotRendererFactory.create(DotStyle.SQUARE)
        )
    }

    StyledQrCode(
        options = options,
        modifier = Modifier.size(250.dp)
    )
}
```

## Screenshots

| Simple QR | Custom Style | With Logo |
| :---: | :---: | :---: |
| ![Simple](docs/screenshots/simple.png) | ![Custom](docs/screenshots/custom.png) | ![Logo](docs/screenshots/logo.png) |

## Roadmap

- [ ] Animated QR Code support (GIF/MP4).
- [ ] More Frame and Eye Ball styles (e.g., Rounded, Star, Heart).
- [ ] Built-in Wi-Fi and vCard content generators.
- [ ] Export to PDF/Print optimization.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
