package com.samayteck.renderer.eye

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import com.samayteck.core.renderer.eye.EyeRenderer

internal object SquareEyeRenderer : EyeRenderer {

    override fun draw(
        canvas: Canvas,
        left: Float,
        top: Float,
        size: Float,
        paint: Paint,
        backgroundColor: Int
    ) {

        val outer = RectF(
            left,
            top,
            left + size,
            top + size
        )

        val middle = RectF(
            left + size * 0.15f,
            top + size * 0.15f,
            left + size * 0.85f,
            top + size * 0.85f
        )

        val center = RectF(
            left + size * 0.30f,
            top + size * 0.30f,
            left + size * 0.70f,
            top + size * 0.70f
        )

        canvas.drawRect(
            outer,
            paint
        )

        val clearPaint = Paint().apply {
            color = backgroundColor
        }

        canvas.drawRect(
            middle,
            clearPaint
        )

        canvas.drawRect(
            center,
            paint
        )
    }
}