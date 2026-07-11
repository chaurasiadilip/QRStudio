package com.samayteck.renderer.dot

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import com.samayteck.core.renderer.dot.DotRenderer

internal object ClassyDotRenderer : DotRenderer {

    override fun draw(
        canvas: Canvas,
        x: Float,
        y: Float,
        moduleSize: Float,
        paint: Paint
    ) {
        val path = Path()
        val rect = RectF(x, y, x + moduleSize, y + moduleSize)
        val radius = moduleSize * 0.45f
        
        // Custom path: Top-left and bottom-right rounded, others square
        val radii = floatArrayOf(
            radius, radius, // Top-left
            0f, 0f,         // Top-right
            radius, radius, // Bottom-right
            0f, 0f          // Bottom-left
        )
        
        path.addRoundRect(rect, radii, Path.Direction.CW)
        canvas.drawPath(path, paint)
    }
}