package com.samayteck.renderer.logo

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import com.samayteck.core.model.LogoShape
import kotlin.math.cos
import kotlin.math.sin

internal object LogoBackgroundRenderer {

    fun draw(
        canvas: Canvas,
        centerX: Float,
        centerY: Float,
        width: Float,
        height: Float,
        shape: LogoShape,
        backgroundColor: Int
    ) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = backgroundColor
        }

        val rect = RectF(
            centerX - width / 2f,
            centerY - height / 2f,
            centerX + width / 2f,
            centerY + height / 2f
        )

        when (shape) {
            LogoShape.CIRCLE -> {
                canvas.drawOval(rect, paint)
            }
            LogoShape.SQUARE -> {
                canvas.drawRect(rect, paint)
            }
            LogoShape.ROUNDED_SQUARE -> {
                val radius = minOf(width, height) * 0.2f
                canvas.drawRoundRect(rect, radius, radius, paint)
            }
            LogoShape.HEXAGON -> {
                drawHexagon(canvas, centerX, centerY, maxOf(width, height) / 2f, paint)
            }
        }
    }

    private fun drawHexagon(canvas: Canvas, centerX: Float, centerY: Float, radius: Float, paint: Paint) {
        val path = Path()
        val angle = Math.PI / 3
        for (i in 0..5) {
            val x = centerX + radius * cos(i * angle).toFloat()
            val y = centerY + radius * sin(i * angle).toFloat()
            if (i == 0) path.moveTo(x, y)
            else path.lineTo(x, y)
        }
        path.close()
        canvas.drawPath(path, paint)
    }
}
