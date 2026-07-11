package com.samayteck.renderer.eye.ball

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import com.samayteck.core.renderer.eye.EyeBallRenderer

internal object RoundedEyeBallRenderer : EyeBallRenderer {

    override fun draw(
        canvas: Canvas,
        cx: Float,
        cy: Float,
        size: Float,
        paint: Paint
    ) {
        val rect = RectF(
            cx - size / 2f,
            cy - size / 2f,
            cx + size / 2f,
            cy + size / 2f
        )
        val radius = size * 0.2f
        canvas.drawRoundRect(rect, radius, radius, paint)
    }
}