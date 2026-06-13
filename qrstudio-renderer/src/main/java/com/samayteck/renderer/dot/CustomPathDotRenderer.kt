package com.samayteck.renderer.dot

import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import com.samayteck.core.renderer.dot.DotRenderer

class CustomPathDotRenderer(
    private val sourcePath: Path
) : DotRenderer {

    override fun draw(
        canvas: Canvas,
        x: Float,
        y: Float,
        moduleSize: Float,
        paint: Paint
    ) {

        val path = Path(sourcePath)

        val bounds =
            android.graphics.RectF()

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
            moduleSize / bounds.width(),
            moduleSize / bounds.height()
        )

        matrix.postTranslate(
            x,
            y
        )

        path.transform(matrix)

        canvas.drawPath(
            path,
            paint
        )
    }
}