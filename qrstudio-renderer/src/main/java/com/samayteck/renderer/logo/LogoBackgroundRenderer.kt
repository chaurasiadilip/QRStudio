package com.samayteck.renderer.logo

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

internal object LogoBackgroundRenderer {

    fun draw(
        canvas: Canvas,
        centerX: Float,
        centerY: Float,
        radius: Float
    ) {

        canvas.drawCircle(
            centerX,
            centerY,
            radius,
            Paint().apply {
                color = Color.WHITE
            }
        )
    }
}