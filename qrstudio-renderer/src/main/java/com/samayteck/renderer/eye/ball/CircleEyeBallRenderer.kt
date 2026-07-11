package com.samayteck.renderer.eye.ball

import android.graphics.Canvas
import android.graphics.Paint
import com.samayteck.core.renderer.eye.EyeBallRenderer

internal object CircleEyeBallRenderer : EyeBallRenderer {

    override fun draw(
        canvas: Canvas,
        cx: Float,
        cy: Float,
        size: Float,
        paint: Paint
    ) {
        canvas.drawCircle(cx, cy, size / 2f, paint)
    }
}