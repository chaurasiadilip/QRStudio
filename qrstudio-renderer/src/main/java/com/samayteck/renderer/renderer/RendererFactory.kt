package com.samayteck.renderer.renderer

import com.samayteck.core.renderer.QrRenderer
import com.samayteck.renderer.step.DefaultBackgroundStep
import com.samayteck.renderer.step.DefaultDotStep
import com.samayteck.renderer.step.DefaultEyeStep
import com.samayteck.renderer.step.DefaultFrameStep
import com.samayteck.renderer.step.DefaultLogoStep

object RendererFactory {

    fun create(): QrRenderer {

        return DefaultQrRenderer(

            backgroundStep =
                DefaultBackgroundStep(),

            frameStep =
                DefaultFrameStep(),

            dotStep =
                DefaultDotStep(),

            eyeStep =
                DefaultEyeStep(),

            logoStep =
                DefaultLogoStep()
        )
    }
}