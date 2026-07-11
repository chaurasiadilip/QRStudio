package com.samayteck.renderer.frame

import android.graphics.*
import com.samayteck.core.model.FrameOptions
import com.samayteck.core.renderer.frame.FrameRenderer

internal object BoxFrameRenderer : FrameRenderer {
    override fun draw(canvas: Canvas, size: Int, options: FrameOptions) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = options.strokeWidth
            color = options.frameColor
        }
        val p = options.padding
        val labelHeight = if (options.label != null) options.labelSize * 1.5f else 0f
        
        // Outer border
        canvas.drawRect(p, p, size - p, size - p - labelHeight, paint)
        
        options.label?.let { text ->
            val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.FILL
                color = options.frameColor
            }
            
            val labelRect = RectF(p, size - p - labelHeight, size - p, size - p)
            canvas.drawRect(labelRect, fillPaint)
            
            val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.WHITE // High contrast for label on filled box
                textSize = options.labelSize
                textAlign = Paint.Align.CENTER
                typeface = when(options.fontType) {
                    "SERIF" -> Typeface.create(Typeface.SERIF, Typeface.BOLD)
                    "MONOSPACE" -> Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
                    else -> Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
                }
            }
            
            canvas.drawText(text, size / 2f, size - p - (labelHeight * 0.3f), textPaint)
        }
    }
}

