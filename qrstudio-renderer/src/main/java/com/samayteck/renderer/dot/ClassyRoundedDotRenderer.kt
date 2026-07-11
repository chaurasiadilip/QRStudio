package com.samayteck.renderer.dot

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import com.samayteck.core.renderer.dot.DotRenderer

internal object ClassyRoundedDotRenderer : DotRenderer {

    override fun draw(
        canvas: Canvas,
        x: Float,
        y: Float,
        moduleSize: Float,
        paint: Paint
    ) {
        val path = Path()
        val rect = RectF(x, y, x + moduleSize, y + moduleSize)
        val r1 = moduleSize * 0.45f
        val r2 = moduleSize * 0.15f
        
        // Custom path: Mix of large and small rounding for a "classy rounded" look
        val radii = floatArrayOf(
            r1, r1, // Top-left large
            r2, r2, // Top-right small
            r1, r1, // Bottom-right large
            r2, r2  // Bottom-left small
        )
        
        path.addRoundRect(rect, radii, Path.Direction.CW)
        canvas.drawPath(path, paint)
    }
}