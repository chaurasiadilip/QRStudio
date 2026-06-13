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
        val radius = 40f
        val path = Path()

        path.addRoundRect(
            RectF(padding, padding, size - padding, size - padding),
            radius, radius, Path.Direction.CW
        )

        canvas.drawPath(path, paint)

        val cutoutRadius = 25f
        val clearPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
        }

        canvas.drawCircle(padding, size / 2f, cutoutRadius, clearPaint)
        canvas.drawCircle(size - padding, size / 2f, cutoutRadius, clearPaint)

        options.label?.let { text ->
             val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = options.labelColor
                textSize = size * 0.05f
                textAlign = Paint.Align.CENTER
                typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            }
            canvas.drawText(text, size / 2f, size - padding + 5f, textPaint)
        }
    }
}