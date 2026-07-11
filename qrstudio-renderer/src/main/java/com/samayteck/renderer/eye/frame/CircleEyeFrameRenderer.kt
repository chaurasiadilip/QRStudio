package com.samayteck.renderer.eye.frame

import android.graphics.Canvas
import android.graphics.Paint
import com.samayteck.core.renderer.eye.EyeFrameRenderer

internal object CircleEyeFrameRenderer : EyeFrameRenderer {

    override fun draw(
        canvas: Canvas,
        left: Float,
        top: Float,
        size: Float,
        paint: Paint,
        backgroundColor: Int
    ) {
        val cx = left + size / 2f
        val cy = top + size / 2f
        
        // Outer circle (7 modules)
        canvas.drawCircle(cx, cy, size / 2f, paint)
        
        // Inner circle (5 modules) - clearing
        val clearPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = backgroundColor
        }
        canvas.drawCircle(cx, cy, size * (5f / 14f), clearPaint)
    }
}