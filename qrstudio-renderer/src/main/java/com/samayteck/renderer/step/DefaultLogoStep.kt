package com.samayteck.renderer.step

import com.samayteck.core.renderer.RenderContext
import com.samayteck.core.renderer.step.LogoStep
import com.samayteck.renderer.logo.BitmapLogoRenderer

internal class DefaultLogoStep :
    LogoStep {

    override fun render(
        context: RenderContext
    ) {

        val logoOptions =
            context.options
                .logoOptions

        val logo =
            logoOptions.bitmap
                ?: return

        BitmapLogoRenderer(
            logo = logo,

            logoPercent =
                logoOptions.logoPercent,

            drawBackground =
                logoOptions.drawBackground,

            backgroundColor =
                context.options.backgroundColor,
            
            backgroundPadding = 
                logoOptions.backgroundPadding
        ).draw(
            context.canvas,
            context.qrAreaSize.toInt(),
            context.centerX,
            context.centerY
        )
    }
}