package com.samayteck.renderer.dot

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import com.samayteck.core.renderer.dot.DotRenderer

internal object RoundedDotRenderer : DotRenderer {

    override fun draw(
        canvas: Canvas,
        x: Float,
        y: Float,
        moduleSize: Float,
        paint: Paint
    ) {

        canvas.drawRoundRect(
            RectF(
                x,
                y,
                x + moduleSize,
                y + moduleSize
            ),
            moduleSize * 0.30f,
            moduleSize * 0.30f,
            paint
        )
    }
}