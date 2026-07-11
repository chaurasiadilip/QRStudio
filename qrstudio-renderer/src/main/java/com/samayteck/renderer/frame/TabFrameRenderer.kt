package com.samayteck.renderer.frame

import android.graphics.*
import com.samayteck.core.model.FrameOptions
import com.samayteck.core.renderer.frame.FrameRenderer

internal object TabFrameRenderer : FrameRenderer {
    override fun draw(canvas: Canvas, size: Int, options: FrameOptions) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = options.strokeWidth
            color = options.frameColor
        }
        val p = options.padding
        val labelHeight = if (options.label != null) options.labelSize * 1.5f else 0f
        val path = Path()
        
        // Rect with rounded top corners
        val radius = 40f
        val rect = RectF(p, p, size - p, size - p - labelHeight - p)
        val radii = floatArrayOf(radius, radius, radius, radius, 0f, 0f, 0f, 0f)
        path.addRoundRect(rect, radii, Path.Direction.CW)
        
        // Tab at bottom
        val tabWidth = size * 0.4f
        val tabHeight = labelHeight
        val centerX = size / 2f
        val bottom = size - p - labelHeight - p
        
        path.moveTo(centerX - tabWidth / 2, bottom)
        path.lineTo(centerX - tabWidth / 2 + 10f, bottom + tabHeight)
        path.lineTo(centerX + tabWidth / 2 - 10f, bottom + tabHeight)
        path.lineTo(centerX + tabWidth / 2, bottom)
        
        canvas.drawPath(path, paint)
        
        drawLabel(canvas, size, options)
    }
}

private fun drawLabel(canvas: Canvas, size: Int, options: FrameOptions) {
    options.label?.let { text ->
        val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = options.labelColor
            textSize = options.labelSize
            textAlign = Paint.Align.CENTER
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        }
        canvas.drawText(text, size / 2f, size - options.padding, textPaint)
    }
}
