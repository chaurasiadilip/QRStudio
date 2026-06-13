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

            backgroundPadding =
                logoOptions.backgroundPadding,

            backgroundColor =
                context.options.backgroundColor
        ).draw(
            context.canvas,
            context.options.size
        )
    }
}