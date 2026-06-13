package com.samayteck.renderer.background

import android.graphics.Canvas
import android.graphics.Paint
import com.samayteck.core.renderer.background.BackgroundRenderer

class DotsBackgroundRenderer(
    private val backgroundColor: Int,
    private val dotColor: Int,
    private val spacing: Int = 40,
    private val radius: Float = 3f
) : BackgroundRenderer {

    private val backgroundPaint by lazy {

        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = backgroundColor
            style = Paint.Style.FILL
        }
    }

    private val dotPaint by lazy {

        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = dotColor
            style = Paint.Style.FILL
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

            for (y in 0 until size step spacing) {

                canvas.drawCircle(
                    x.toFloat(),
                    y.toFloat(),
                    radius,
                    dotPaint
                )
            }
        }
    }
}