package com.samayteck.core.model

import android.graphics.Bitmap

data class LogoOptions(
    val bitmap: Bitmap? = null,
    val logoPercent: Float = 0.20f,
    val drawBackground: Boolean = true,
    val backgroundPadding: Float = 12f,
    val logoShape: LogoShape = LogoShape.CIRCLE
) {

    @Deprecated(
        message = "Use logoPercent instead",
        replaceWith = ReplaceWith("logoPercent")
    )
    val sizePercent: Float
        get() = logoPercent
}
