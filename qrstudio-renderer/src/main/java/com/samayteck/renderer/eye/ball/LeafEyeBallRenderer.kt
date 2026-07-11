package com.samayteck.renderer.eye.ball

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import com.samayteck.core.renderer.eye.EyeBallRenderer

internal object LeafEyeBallRenderer : EyeBallRenderer {

    override fun draw(
        canvas: Canvas,
        cx: Float,
        cy: Float,
        size: Float,
        paint: Paint
    ) {
        val path = Path()
        val rect = RectF(cx - size / 2f, cy - size / 2f, cx + size / 2f, cy + size / 2f)
        val radius = size * 0.5f
        
        val radii = floatArrayOf(
            radius, radius, // Top-left rounded
            0f, 0f,         // Top-right square
            radius, radius, // Bottom-right rounded
            0f, 0f          // Bottom-left square
        )
        
        path.addRoundRect(rect, radii, Path.Direction.CW)
        canvas.drawPath(path, paint)
    }
}