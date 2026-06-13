package com.samayteck.renderer.background

import android.graphics.Canvas
import android.graphics.Paint
import com.samayteck.core.renderer.background.BackgroundRenderer

class SolidBackgroundRenderer(
    private val backgroundColor: Int
) : BackgroundRenderer {

    private val paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = backgroundColor
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
            paint
        )
    }
}