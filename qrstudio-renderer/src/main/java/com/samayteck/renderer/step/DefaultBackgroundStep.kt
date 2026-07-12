package com.samayteck.renderer.step

import com.samayteck.core.renderer.RenderContext
import com.samayteck.renderer.factory.DefaultBackgroundRendererFactory
import com.samayteck.core.renderer.step.BackgroundStep
import com.samayteck.renderer.background.ImageBackgroundRenderer

internal class DefaultBackgroundStep : BackgroundStep {

    override fun render(
        context: RenderContext
    ) {
        val backgroundImage = context.options.backgroundImage
        if (backgroundImage != null) {
            ImageBackgroundRenderer(backgroundImage).draw(
                context.canvas,
                context.options.size
            )
        } else {
            val renderer =
                DefaultBackgroundRendererFactory
                    .create(
                        style =
                            context.options.backgroundStyle,

                        pattern =
                            context.options.backgroundPattern,

                        backgroundColor =
                            context.options.backgroundColor
                    )

            renderer.draw(
                context.canvas,
                context.options.size
            )
        }
    }
}