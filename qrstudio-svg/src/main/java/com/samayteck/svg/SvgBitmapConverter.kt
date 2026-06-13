package com.samayteck.svg

import android.graphics.Bitmap
import android.graphics.Canvas
import com.caverock.androidsvg.SVG

object SvgBitmapConverter {

    fun convert(
        svg: SVG,
        width: Int,
        height: Int
    ): Result<Bitmap> {

        return runCatching {

            require(width > 0) {
                "Width must be > 0"
            }

            require(height > 0) {
                "Height must be > 0"
            }

            val bitmap =
                Bitmap.createBitmap(
                    width,
                    height,
                    Bitmap.Config.ARGB_8888
                )

            val canvas =
                Canvas(bitmap)

            svg.setDocumentWidth(
                width.toFloat()
            )

            svg.setDocumentHeight(
                height.toFloat()
            )

            svg.renderToCanvas(
                canvas
            )

            bitmap
        }
    }
}