package com.samayteck.renderer.background

import android.graphics.Canvas
import android.graphics.Paint
import com.samayteck.core.renderer.background.BackgroundRenderer

class GridBackgroundRenderer(
    private val backgroundColor: Int,
    private val gridColor: Int,
    private val spacing: Int = 40
) : BackgroundRenderer {

    private val backgroundPaint by lazy {

        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = backgroundColor
            style = Paint.Style.FILL
        }
    }

    private val gridPaint by lazy {

        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = gridColor
            strokeWidth = 1f
        }
    }

    override fun draw(
        canvas: Canvas,
        size: Int
    ) {

        canvas.drawRect(
            0f,
            0f,
            size.toFloat(),
            size.toFloat(),
            backgroundPaint
        )

        for (x in 0 until size step spacing) {

            canvas.drawLine(
                x.toFloat(),
                0f,
                x.toFloat(),
                size.toFloat(),
                gridPaint
            )
        }

        for (y in 0 until size step spacing) {

            canvas.drawLine(
                0f,
                y.toFloat(),
                size.toFloat(),
                y.toFloat(),
                gridPaint
            )
        }
    }
}