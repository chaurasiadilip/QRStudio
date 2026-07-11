package com.samayteck.renderer.frame

import android.graphics.*
import com.samayteck.core.model.FrameOptions
import com.samayteck.core.renderer.frame.FrameRenderer

internal object CircleFrameRenderer :
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

        val labelHeight = if (options.label != null) options.labelSize * 1.2f else 0f
        val radius = size / 2f - options.padding - (labelHeight / 2f)
        val cy = size / 2f - (labelHeight / 2f)

        canvas.drawCircle(
            size / 2f,
            cy,
            radius,
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
            canvas.drawText(text, size / 2f, size - options.padding, textPaint)
        }
    }
}
