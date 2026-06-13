package com.samayteck.core.renderer.dot

import android.graphics.Canvas
import android.graphics.Paint

interface DotRenderer {
    fun draw(
        canvas: Canvas,
        x: Float,
        y: Float,
        moduleSize: Float,
        paint: Paint
    )
}