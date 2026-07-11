package com.samayteck.renderer.eye.frame

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import com.samayteck.core.renderer.eye.EyeFrameRenderer

internal object RoundedEyeFrameRenderer : EyeFrameRenderer {

    override fun draw(
        canvas: Canvas,
        left: Float,
        top: Float,
        size: Float,
        paint: Paint,
        backgroundColor: Int
    ) {
        val outer = RectF(left, top, left + size, top + size)
        val radius = size * 0.2f
        canvas.drawRoundRect(outer, radius, radius, paint)
        
        // Inner rounded rect (5 modules)
        val clearPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = backgroundColor
        }
        val padding = size / 7f
        val inner = RectF(
            left + padding,
            top + padding,
            left + size - padding,
            top + size - padding
        )
        val innerRadius = radius * (5f / 7f)
        canvas.drawRoundRect(inner, innerRadius, innerRadius, clearPaint)
    }
}