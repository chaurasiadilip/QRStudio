package com.samayteck.renderer.frame

import android.graphics.*
import com.samayteck.core.model.FrameOptions
import com.samayteck.core.renderer.frame.FrameRenderer

internal object RoundedFrameRenderer :
    FrameRenderer {

    override fun draw(
        canvas: Canvas,
        size: Int,
        options: FrameOptions
    ) {

        val paint = Paint(
            Paint.ANTI_ALIAS_FLAG
        ).apply {
            style = Paint.Style.STROKE
            strokeWidth = options.strokeWidth
            color = options.frameColor
        }

        val padding = options.padding
        val labelHeight = if (options.label != null) options.labelSize * 1.5f else 0f
        
        val frameRect = RectF(
            padding,
            padding,
            size - padding,
            size - padding - labelHeight
        )

        canvas.drawRoundRect(
            frameRect,
            48f,
            48f,
            paint
        )

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
            
            val textX = size / 2f
            val textY = size - padding // Place at the very bottom padding area
            
            canvas.drawText(text, textX, textY, textPaint)
        }
    }
}
