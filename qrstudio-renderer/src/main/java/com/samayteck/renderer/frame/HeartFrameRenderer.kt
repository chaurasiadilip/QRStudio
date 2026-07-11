package com.samayteck.renderer.frame

import android.graphics.*
import com.samayteck.core.model.FrameOptions
import com.samayteck.core.renderer.frame.FrameRenderer

internal object HeartFrameRenderer : FrameRenderer {
    override fun draw(canvas: Canvas, size: Int, options: FrameOptions) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = options.strokeWidth
            color = options.frameColor
        }
        
        val path = Path()
        val s = size.toFloat()
        val p = options.padding
        val labelHeight = if (options.label != null) options.labelSize * 1.5f else 0f
        
        // Simple heart path around the QR area, slightly higher to make room for label
        val b = s - p - labelHeight
        path.moveTo(s / 2, p + s * 0.15f)
        path.cubicTo(s * 0.1f, p, s * 0.1f, s * 0.55f, s / 2, b)
        path.cubicTo(s * 0.9f, s * 0.55f, s * 0.9f, p, s / 2, p + s * 0.15f)
        
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
