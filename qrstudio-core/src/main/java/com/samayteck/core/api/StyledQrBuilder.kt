package com.samayteck.core.api

import android.graphics.Color
import com.samayteck.core.content.basic.TextContent
import com.samayteck.core.model.*



class StyledQrBuilder {

    private var content: String = ""

    private var size: Int = 1000

    private var dotShape: DotShape =
        DotShape.CIRCLE

    private var eyeFrameShape: EyeShape =
        EyeShape.ROUNDED

    private var eyeBallShape: EyeBallShape =
        EyeBallShape.ROUNDED

    private var backgroundPattern:
            BackgroundPattern =
        BackgroundPattern.NONE

    private var backgroundStyle:
            BackgroundStyle =
        BackgroundStyle.SOLID

    private var backgroundColor: Int =
        Color.WHITE

    private var gradientStyle:
            GradientStyle =
        GradientStyle.None

    private var frameOptions:
            FrameOptions =
        FrameOptions()

    private var logoOptions:
            LogoOptions =
        LogoOptions()

    fun content(
        value: String
    ) = apply {
        content = value
    }

    fun size(
        value: Int
    ) = apply {
        size = value
    }

    fun dotShape(
        value: DotShape
    ) = apply {
        dotShape = value
    }

    fun eyeFrameShape(
        value: EyeShape
    ) = apply {
        eyeFrameShape = value
    }

    fun eyeBallShape(
        value: EyeBallShape
    ) = apply {
        eyeBallShape = value
    }

    fun backgroundPattern(
        value: BackgroundPattern
    ) = apply {
        backgroundPattern = value
    }

    fun backgroundStyle(
        value: BackgroundStyle
    ) = apply {
        backgroundStyle = value
    }

    fun backgroundColor(
        value: Int
    ) = apply {
        backgroundColor = value
    }

    fun gradient(
        value: GradientStyle
    ) = apply {
        gradientStyle = value
    }

    fun frame(
        value: FrameOptions
    ) = apply {
        frameOptions = value
    }

    fun logo(
        value: LogoOptions
    ) = apply {
        logoOptions = value
    }

    fun build(): StyledQrOptions {

        validate()

        return StyledQrOptions(
            content = TextContent(content),
            size = size,
            dotShape = dotShape,
            eyeFrameShape = eyeFrameShape,
            eyeBallShape = eyeBallShape,
            backgroundPattern = backgroundPattern,
            backgroundStyle = backgroundStyle,
            backgroundColor = backgroundColor,
            gradientStyle = gradientStyle,
            frameOptions = frameOptions,
            logoOptions = logoOptions
        )
    }

    private fun validate() {

        if (content.isBlank()) {
            throw StyledQrException.EmptyContent
        }

        if (size <= 0) {
            throw StyledQrException.InvalidSize
        }

        if (
            logoOptions.sizePercent < 0f ||
            logoOptions.sizePercent > 0.25f
        ) {
            throw StyledQrException.InvalidLogoSize
        }
    }
}