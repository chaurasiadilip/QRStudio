package com.samayteck.renderer.gradient

import android.graphics.LinearGradient
import android.graphics.Shader

internal object LinearGradientFactory {

    fun create(
        size: Int,
        startColor: Int,
        endColor: Int
    ): Shader {

        return LinearGradient(
            0f,
            0f,
            size.toFloat(),
            size.toFloat(),
            startColor,
            endColor,
            Shader.TileMode.CLAMP
        )
    }
}