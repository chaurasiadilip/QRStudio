package com.samayteck.renderer.dot

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import com.samayteck.core.renderer.dot.DotRenderer

internal object DiamondDotRenderer : DotRenderer {

    override fun draw(
        canvas: Canvas,
        x: Float,
        y: Float,
        moduleSize: Float,
        paint: Paint
    ) {

        val cx = x + moduleSize / 2f
        val cy = y + moduleSize / 2f

        val path = Path().apply {

            moveTo(cx, y)

            lineTo(
                x + moduleSize,
                cy
            )

            lineTo(
                cx,
                y + moduleSize
            )

            lineTo(
                x,
                cy
            )

            close()
        }

        canvas.drawPath(
            path,
            paint
        )
    }
}