package com.samayteck.core.renderer

class RendererPipeline(
    private val steps: List<RenderStep>
) {

    fun execute(
        context: RenderContext
    ) {

        steps.forEach { step ->

            step.render(
                context
            )
        }
    }
}