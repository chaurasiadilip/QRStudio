package com.samayteck.renderer.dot

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import com.samayteck.core.renderer.dot.DotRenderer
import kotlin.math.cos
import kotlin.math.sin

internal object StarDotRenderer : DotRenderer {

    override fun draw(
        canvas: Canvas,
        x: Float,
        y: Float,
        moduleSize: Float,
        paint: Paint
    ) {

        val cx =
            x + moduleSize / 2f

        val cy =
            y + moduleSize / 2f

        val outerRadius =
            moduleSize * 0.5f

        val innerRadius =
            moduleSize * 0.25f

        val path = Path()

        repeat(10) { index ->

            val radius =
                if (index % 2 == 0)
                    outerRadius
                else
                    innerRadius

            val angle =
                Math.toRadians(
                    (index * 36 - 90).toDouble()
                )

            val px =
                cx + radius *
                        cos(angle).toFloat()

            val py =
                cy + radius *
                        sin(angle).toFloat()

            if (index == 0) {
                path.moveTo(px, py)
            } else {
                path.lineTo(px, py)
            }
        }

        path.close()

        canvas.drawPath(
            path,
            paint
        )
    }
}