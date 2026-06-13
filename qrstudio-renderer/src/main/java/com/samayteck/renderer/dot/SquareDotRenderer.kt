package com.samayteck.renderer.dot

import android.graphics.Canvas
import android.graphics.Paint
import com.samayteck.core.renderer.dot.DotRenderer

internal object SquareDotRenderer : DotRenderer {

    override fun draw(
        canvas: Canvas,
        x: Float,
        y: Float,
        moduleSize: Float,
        paint: Paint
    ) {

        canvas.drawRect(
            x,
            y,
            x + moduleSize,
            y + moduleSize,
            paint
        )
    }
}