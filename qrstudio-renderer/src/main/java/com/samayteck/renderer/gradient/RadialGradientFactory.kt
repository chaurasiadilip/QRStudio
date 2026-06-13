package com.samayteck.renderer.gradient

import android.graphics.RadialGradient
import android.graphics.Shader

internal object RadialGradientFactory {

    fun create(
        size: Int,
        centerColor: Int,
        edgeColor: Int
    ): Shader {

        return RadialGradient(
            size / 2f,
            size / 2f,
            size / 2f,
            centerColor,
            edgeColor,
            Shader.TileMode.CLAMP
        )
    }
}