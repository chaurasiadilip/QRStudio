package com.samayteck.renderer.eye.frame

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import com.samayteck.core.renderer.eye.EyeFrameRenderer

internal object ShieldEyeFrameRenderer : EyeFrameRenderer {

    override fun draw(
        canvas: Canvas,
        left: Float,
        top: Float,
        size: Float,
        paint: Paint,
        backgroundColor: Int
    ) {
        val path = Path()
        val rect = RectF(left, top, left + size, top + size)
        val radius = size * 0.4f
        val radii = floatArrayOf(radius, radius, radius, radius, radius, radius, 0f, 0f)
        path.addRoundRect(rect, radii, Path.Direction.CW)
        canvas.drawPath(path, paint)
        
        // Inner shield (5 modules)
        val clearPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = backgroundColor
        }
        val innerPath = Path()
        val padding = size / 7f
        val innerRect = RectF(left + padding, top + padding, left + size - padding, top + size - padding)
        val innerRadius = radius * (5f / 7f)
        val innerRadii = floatArrayOf(innerRadius, innerRadius, innerRadius, innerRadius, innerRadius, innerRadius, 0f, 0f)
        innerPath.addRoundRect(innerRect, innerRadii, Path.Direction.CW)
        canvas.drawPath(innerPath, clearPaint)
    }
}