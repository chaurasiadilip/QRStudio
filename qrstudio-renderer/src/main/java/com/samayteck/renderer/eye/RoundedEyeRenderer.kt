package com.samayteck.renderer.eye

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import com.samayteck.core.renderer.eye.EyeRenderer

internal object RoundedEyeRenderer :
    EyeRenderer {

    override fun draw(
        canvas: Canvas,
        left: Float,
        top: Float,
        size: Float,
        paint: Paint,
        backgroundColor: Int
    ) {

        val radius =
            size * 0.20f

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

        canvas.drawRoundRect(
            outer,
            radius,
            radius,
            paint
        )

        val clearPaint = Paint(
            Paint.ANTI_ALIAS_FLAG
        ).apply {
            color = backgroundColor
        }

        canvas.drawRoundRect(
            middle,
            radius,
            radius,
            clearPaint
        )

        canvas.drawRoundRect(
            center,
            radius,
            radius,
            paint
        )
    }
}