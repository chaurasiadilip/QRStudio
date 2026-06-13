package com.samayteck.renderer.frame

import android.graphics.*
import com.samayteck.core.model.FrameOptions
import com.samayteck.core.renderer.frame.FrameRenderer

class CustomFrameRenderer(
    private val sourcePath: Path
) : FrameRenderer {

    override fun draw(
        canvas: Canvas,
        size: Int,
        options: FrameOptions
    ) {

        val path = Path(sourcePath)

        val bounds = RectF()

        path.computeBounds(
            bounds,
            true
        )

        if (
            bounds.width() <= 0f ||
            bounds.height() <= 0f
        ) {
            return
        }

        val matrix = Matrix()

        matrix.postScale(
            size / bounds.width(),
            size / bounds.height()
        )

        path.transform(matrix)

        val paint = Paint(
            Paint.ANTI_ALIAS_FLAG
        ).apply {
            style = Paint.Style.STROKE
            strokeWidth = options.strokeWidth
            color = options.frameColor
        }

        canvas.drawPath(
            path,
            paint
        )
        
        options.label?.let { text ->
             val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = options.labelColor
                textSize = size * 0.05f
                textAlign = Paint.Align.CENTER
                typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            }
            canvas.drawText(text, size / 2f, size - options.padding, textPaint)
        }
    }
}