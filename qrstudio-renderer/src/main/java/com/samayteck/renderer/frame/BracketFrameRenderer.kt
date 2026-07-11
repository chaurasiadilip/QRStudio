package com.samayteck.renderer.frame

import android.graphics.*
import com.samayteck.core.model.FrameOptions
import com.samayteck.core.renderer.frame.FrameRenderer

internal object BracketFrameRenderer : FrameRenderer {
    override fun draw(canvas: Canvas, size: Int, options: FrameOptions) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = options.strokeWidth
            color = options.frameColor
        }
        val p = options.padding
        val labelHeight = if (options.label != null) options.labelSize * 1.5f else 0f
        val l = size * 0.15f // bracket length

        // Top Left
        canvas.drawLine(p, p, p + l, p, paint)
        canvas.drawLine(p, p, p, p + l, paint)
        // Top Right
        canvas.drawLine(size - p, p, size - p - l, p, paint)
        canvas.drawLine(size - p, p, size - p, p + l, paint)
        
        // Bottom height adjusted for label
        val b = size - p - labelHeight
        
        // Bottom Left
        canvas.drawLine(p, b, p + l, b, paint)
        canvas.drawLine(p, b, p, b - l, paint)
        // Bottom Right
        canvas.drawLine(size - p, b, size - p - l, b, paint)
        canvas.drawLine(size - p, b, size - p, b - l, paint)

        drawLabel(canvas, size, options)
    }
}


private fun drawLabel(canvas: Canvas, size: Int, options: FrameOptions) {
    options.label?.let { text ->
        val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = options.labelColor
            textSize = options.labelSize
            textAlign = Paint.Align.CENTER
            typeface = when(options.fontType) {
                "SERIF" -> Typeface.create(Typeface.SERIF, Typeface.BOLD)
                "MONOSPACE" -> Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
                else -> Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            }
        }
        canvas.drawText(text, size / 2f, size - options.padding, textPaint)
    }
}
