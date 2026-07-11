package com.samayteck.core.renderer.eye

import android.graphics.Canvas
import android.graphics.Paint

interface EyeFrameRenderer {
    fun draw(
        canvas: Canvas,
        left: Float,
        top: Float,
        size: Float,
        paint: Paint,
        backgroundColor: Int
    )
}