package com.samayteck.core.renderer.eye

import android.graphics.Canvas
import android.graphics.Paint

interface EyeBallRenderer {
    fun draw(
        canvas: Canvas,
        cx: Float,
        cy: Float,
        size: Float,
        paint: Paint
    )
}