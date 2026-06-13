package com.samayteck.renderer.dot

import android.graphics.Canvas
import android.graphics.Paint
import com.samayteck.core.renderer.dot.DotRenderer

internal object CircleDotRenderer :
    DotRenderer {

    override fun draw(
        canvas: Canvas,
        x: Float,
        y: Float,
        moduleSize: Float,
        paint: Paint
    ) {

        canvas.drawCircle(
            x + moduleSize / 2f,
            y + moduleSize / 2f,
            moduleSize * 0.45f,
            paint
        )
    }
}