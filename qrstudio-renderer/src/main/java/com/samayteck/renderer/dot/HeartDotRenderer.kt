package com.samayteck.renderer.dot

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import com.samayteck.core.renderer.dot.DotRenderer

internal object HeartDotRenderer : DotRenderer {

    override fun draw(
        canvas: Canvas,
        x: Float,
        y: Float,
        moduleSize: Float,
        paint: Paint
    ) {

        val cx =
            x + moduleSize / 2f

        val path = Path().apply {

            // Top center dip
            moveTo(cx, y + moduleSize * 0.3f)

            // Left lobe
            cubicTo(
                x + moduleSize * 0.1f, y - moduleSize * 0.05f,
                x - moduleSize * 0.1f, y + moduleSize * 0.55f,
                cx, y + moduleSize * 0.95f
            )

            // Right lobe
            cubicTo(
                x + moduleSize * 1.1f, y + moduleSize * 0.55f,
                x + moduleSize * 0.9f, y - moduleSize * 0.05f,
                cx, y + moduleSize * 0.3f
            )

            close()
        }

        canvas.drawPath(
            path,
            paint
        )
    }
}