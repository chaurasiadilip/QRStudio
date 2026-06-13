package com.samayteck.renderer.factory

import com.samayteck.core.model.EyeShape
import com.samayteck.core.renderer.eye.EyeRenderer
import com.samayteck.renderer.eye.CircleEyeRenderer
import com.samayteck.renderer.eye.RoundedEyeRenderer
import com.samayteck.renderer.eye.SquareEyeRenderer

internal object EyeRendererFactory {

    fun create(
        shape: EyeShape
    ): EyeRenderer {

        return when (shape) {

            EyeShape.SQUARE ->
                SquareEyeRenderer

            EyeShape.ROUNDED ->
                RoundedEyeRenderer

            EyeShape.CIRCLE ->
                CircleEyeRenderer
        }
    }
}