package com.samayteck.core.model

import android.graphics.Bitmap
import com.samayteck.core.encoder.EncodingOptions
import android.graphics.Color
import com.samayteck.core.constants.QrConstants
import com.samayteck.core.content.QrContent

data class StyledQrOptions(

    val content: QrContent,

    val size: Int =
        QrConstants.DEFAULT_QR_SIZE,

    val dotShape: DotShape =
        DotShape.ROUNDED,

    val eyeShape: EyeShape =
        EyeShape.ROUNDED,

    val backgroundColor: Int =
        Color.WHITE,

    val backgroundPattern:
    BackgroundPattern =
        BackgroundPattern.NONE,

    val backgroundStyle:
    BackgroundStyle =
        BackgroundStyle.SOLID,

    val gradientStyle:
    GradientStyle =
        GradientStyle.None,

    val frameOptions:
    FrameOptions =
        FrameOptions(),

    val logoOptions:
    LogoOptions =
        LogoOptions(),

    val encodingOptions:
    EncodingOptions =
        EncodingOptions()
)
