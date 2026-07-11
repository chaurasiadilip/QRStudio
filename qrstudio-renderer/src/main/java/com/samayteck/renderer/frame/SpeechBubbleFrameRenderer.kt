package com.samayteck.renderer.frame

import android.graphics.*
import com.samayteck.core.model.FrameOptions
import com.samayteck.core.renderer.frame.FrameRenderer

internal object SpeechBubbleFrameRenderer : FrameRenderer {
    override fun draw(canvas: Canvas, size: Int, options: FrameOptions) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = options.strokeWidth
            color = options.frameColor
        }
        val p = options.padding
        val labelHeight = if (options.label != null) options.labelSize * 1.5f else 0f
        val radius = 40f
        val path = Path()
        
        // Main rounded rect
        val rect = RectF(p, p, size - p, size - p - labelHeight)
        path.addRoundRect(rect, radius, radius, Path.Direction.CW)
        
        // Bubble tail at bottom
        val tailWidth = size * 0.1f
        val tailHeight = p * 0.5f
        val centerX = size / 2f
        val bottom = size - p - labelHeight
        
        path.moveTo(centerX - tailWidth / 2, bottom)
        path.lineTo(centerX, bottom + tailHeight)
        path.lineTo(centerX + tailWidth / 2, bottom)
        
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
