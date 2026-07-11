package com.samayteck.renderer.frame

import android.graphics.*
import com.samayteck.core.model.FrameOptions
import com.samayteck.core.renderer.frame.FrameRenderer

internal object TextOnlyFrameRenderer : FrameRenderer {
    override fun draw(canvas: Canvas, size: Int, options: FrameOptions) {
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
}