package com.samayteck.core.model

import android.graphics.Color

data class FrameOptions(
    val frameStyle: FrameStyle = FrameStyle.NONE,
    val strokeWidth: Float = 12f,
    val padding: Float = 24f,
    val label: String? = null,
    val labelColor: Int = Color.BLACK,
    val frameColor: Int = Color.BLACK,
    val labelSize: Float = 40f,
    val fontType: String = "SANS_SERIF"
)
