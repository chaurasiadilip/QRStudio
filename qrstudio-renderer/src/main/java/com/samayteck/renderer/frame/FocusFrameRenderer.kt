package com.samayteck.renderer.frame

import android.graphics.*
import com.samayteck.core.model.FrameOptions
import com.samayteck.core.renderer.frame.FrameRenderer

internal object FocusFrameRenderer : FrameRenderer {
    override fun draw(canvas: Canvas, size: Int, options: FrameOptions) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = options.strokeWidth * 0.5f
            color = options.frameColor
        }
        val p = options.padding
        val labelHeight = if (options.label != null) options.labelSize * 1.5f else 0f
        val gap = options.strokeWidth * 2f
        val b = size - p - labelHeight
        
        canvas.drawRect(p, p, size - p, b, paint)
        
        paint.strokeWidth = options.strokeWidth
        // Corners
        val l = size * 0.1f
        canvas.drawLine(p - gap, p - gap, p - gap + l, p - gap, paint)
        canvas.drawLine(p - gap, p - gap, p - gap, p - gap + l, paint)
        
        canvas.drawLine(size - p + gap, p - gap, size - p + gap - l, p - gap, paint)
        canvas.drawLine(size - p + gap, p - gap, size - p + gap, p - gap + l, paint)

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
