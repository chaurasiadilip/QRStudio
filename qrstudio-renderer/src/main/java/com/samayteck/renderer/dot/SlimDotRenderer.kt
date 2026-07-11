package com.samayteck.renderer.dot

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import com.samayteck.core.renderer.dot.DotRenderer

internal object SlimDotRenderer : DotRenderer {

    override fun draw(
        canvas: Canvas,
        x: Float,
        y: Float,
        moduleSize: Float,
        paint: Paint
    ) {
        val padding = moduleSize * 0.15f
        val rect = RectF(
            x + padding,
            y + padding,
            x + moduleSize - padding,
            y + moduleSize - padding
        )
        canvas.drawRect(rect, paint)
    }
}