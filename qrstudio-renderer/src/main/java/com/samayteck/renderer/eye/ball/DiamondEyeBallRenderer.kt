package com.samayteck.renderer.eye.ball

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import com.samayteck.core.renderer.eye.EyeBallRenderer

internal object DiamondEyeBallRenderer : EyeBallRenderer {

    override fun draw(
        canvas: Canvas,
        cx: Float,
        cy: Float,
        size: Float,
        paint: Paint
    ) {
        val radius = size / 2f
        val path = Path().apply {
            moveTo(cx, cy - radius)
            lineTo(cx + radius, cy)
            lineTo(cx, cy + radius)
            lineTo(cx - radius, cy)
            close()
        }
        canvas.drawPath(path, paint)
    }
}