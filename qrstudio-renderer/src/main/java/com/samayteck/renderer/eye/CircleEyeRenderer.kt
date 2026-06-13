package com.samayteck.renderer.eye

import android.graphics.Canvas
import android.graphics.Paint
import com.samayteck.core.renderer.eye.EyeRenderer

internal object CircleEyeRenderer :
    EyeRenderer {

    override fun draw(
        canvas: Canvas,
        left: Float,
        top: Float,
        size: Float,
        paint: Paint,
        backgroundColor: Int
    ) {

        val cx =
            left + size / 2f

        val cy =
            top + size / 2f

        canvas.drawCircle(
            cx,
            cy,
            size / 2f,
            paint
        )

        val clearPaint = Paint(
            Paint.ANTI_ALIAS_FLAG
        ).apply {
            color = backgroundColor
        }

        canvas.drawCircle(
            cx,
            cy,
            size * 0.33f,
            clearPaint
        )

        canvas.drawCircle(
            cx,
            cy,
            size * 0.22f,
            paint
        )
    }
}