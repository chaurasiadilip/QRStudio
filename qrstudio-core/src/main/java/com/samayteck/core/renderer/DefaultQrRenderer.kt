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

        var framePadding = 0f
        var labelHeight = 0f

        if (options.frameOptions.frameStyle != com.samayteck.core.model.FrameStyle.NONE) {
            framePadding = options.frameOptions.padding
            if (options.frameOptions.label != null) {
                labelHeight = options.frameOptions.labelSize * 1.5f
            }
        }

        // The QR code must fit within the frame, with some extra safety gap (5% of size)
        val safetyGap = if (framePadding > 0) options.size * 0.05f else 0f
        
        val availableWidth = options.size - (framePadding * 2) - (safetyGap * 2)
        val availableHeight = options.size - (framePadding * 2) - (safetyGap * 2) - labelHeight

        val availableSize = minOf(availableWidth, availableHeight)

        val totalModules =
            matrix.width + (margin * 2)

        val moduleSize =
            availableSize /
                    totalModules

        val qrAreaSize = totalModules * moduleSize
        
        val offsetX = (options.size - qrAreaSize) / 2f
        val offsetY = (options.size - labelHeight - qrAreaSize) / 2f

        val context =
            RenderContext(
                matrix = matrix,
                options = options,
                bitmap = bitmap,
                canvas = canvas,
                moduleSize = moduleSize,
                offsetX = offsetX + (margin * moduleSize),
                offsetY = offsetY + (margin * moduleSize),
                centerX = options.size / 2f,
                centerY = (options.size - labelHeight) / 2f,
                qrAreaSize = availableSize
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