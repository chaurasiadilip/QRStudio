package com.samayteck.renderer.eye

import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import com.samayteck.core.renderer.eye.EyeRenderer

class CustomEyeRenderer(
    private val path: Path
) : EyeRenderer {

    override fun draw(
        canvas: Canvas,
        left: Float,
        top: Float,
        size: Float,
        paint: Paint,
        backgroundColor: Int
    ) {

        val customPath =
            Path(path)

        val bounds = RectF()

        customPath.computeBounds(
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

        matrix.postTranslate(
            left,
            top
        )

        customPath.transform(
            matrix
        )

        canvas.drawPath(
            customPath,
            paint
        )
    }
}