package com.samayteck.renderer.gradient

import android.graphics.Shader
import android.graphics.SweepGradient

internal object SweepGradientFactory {

    fun create(
        size: Int,
        colors: IntArray
    ): Shader {

        return SweepGradient(
            size / 2f,
            size / 2f,
            colors,
            null
        )
    }
}