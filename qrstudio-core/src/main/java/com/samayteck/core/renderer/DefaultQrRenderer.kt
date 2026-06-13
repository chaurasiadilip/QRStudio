package com.samayteck.core.renderer

import android.graphics.Bitmap
import android.graphics.Canvas
import com.google.zxing.common.BitMatrix
import com.samayteck.core.model.StyledQrOptions
import com.samayteck.core.renderer.step.*

internal class DefaultQrRenderer(
    private val backgroundStep:
    BackgroundStep,

    private val frameStep:
    FrameStep,

    private val dotStep:
    DotStep,

    private val eyeStep:
    EyeStep,

    private val logoStep:
    LogoStep
) : QrRenderer {

    override fun render(
        matrix: BitMatrix,
        options: StyledQrOptions
    ): RenderResult {

        val bitmap =
            Bitmap.createBitmap(
                options.size,
                options.size,
                Bitmap.Config.ARGB_8888
            )

        val canvas =
            Canvas(bitmap)

        val margin =
            options.encodingOptions.margin

        val totalModules =
            matrix.width + (margin * 2)

        val moduleSize =
            options.size.toFloat() /
                    totalModules

        val offset =
            margin * moduleSize

        val context =
            RenderContext(
                matrix = matrix,
                options = options,
                bitmap = bitmap,
                canvas = canvas,
                moduleSize = moduleSize,
                offsetX = offset,
                offsetY = offset
            )

        RendererPipeline(
            steps = listOf(
                backgroundStep,
                frameStep,
                dotStep,
                eyeStep,
                logoStep
            )
        ).execute(context)

        return RenderResult(
            bitmap = bitmap
        )
    }
}