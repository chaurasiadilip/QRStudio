package com.samayteck.renderer.frame

import android.graphics.*
import com.samayteck.core.model.FrameOptions
import com.samayteck.core.renderer.frame.FrameRenderer

internal object TicketFrameRenderer :
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
        val labelHeight = if (options.label != null) options.labelSize * 1.2f else 0f
        val radius = 40f
        val path = Path()

        path.addRoundRect(
            RectF(padding, padding, size - padding, size - padding - labelHeight),
            radius, radius, Path.Direction.CW
        )

        canvas.drawPath(path, paint)

        val cutoutRadius = 25f
        val clearPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
        }

        val centerY = (padding + (size - padding - labelHeight)) / 2f
        canvas.drawCircle(padding, centerY, cutoutRadius, clearPaint)
        canvas.drawCircle(size - padding, centerY, cutoutRadius, clearPaint)

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
            canvas.drawText(text, size / 2f, size - padding, textPaint)
        }
    }
}
