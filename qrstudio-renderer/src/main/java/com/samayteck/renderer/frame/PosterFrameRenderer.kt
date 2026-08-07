package com.samayteck.renderer.frame

import android.graphics.*
import com.samayteck.core.model.FrameOptions
import com.samayteck.core.renderer.frame.FrameRenderer
import com.samayteck.renderer.gradient.GradientPaintFactory

internal object PosterFrameRenderer : FrameRenderer {
    override fun draw(canvas: Canvas, size: Int, options: FrameOptions) {
        val paint = GradientPaintFactory.create(size, options.gradientStyle).apply {
            if (shader == null) {
                color = options.frameColor
            }
        }
        
        val p = options.padding
        val labelHeight = if (options.label != null) options.labelSize * 2.5f else 0f
        
        // Draw thick Header/Footer bar for "Poster" look
        val footerRect = RectF(p, size - p - labelHeight, size - p, size - p)
        canvas.drawRoundRect(footerRect, 20f, 20f, paint)
        
        // Draw thin border around the QR area
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = options.strokeWidth / 2f
        val borderRect = RectF(p, p, size - p, size - p - labelHeight)
        canvas.drawRoundRect(borderRect, 20f, 20f, paint)

        drawLabel(canvas, size, options)
    }

    private fun drawLabel(canvas: Canvas, size: Int, options: FrameOptions) {
        options.label?.let { text ->
            val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.WHITE // Inverted for the solid footer
                textSize = options.labelSize
                textAlign = Paint.Align.CENTER
                typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            }
            val labelY = size - options.padding - (options.labelSize * 0.7f)
            canvas.drawText(text, size / 2f, labelY, textPaint)
        }
    }
}
