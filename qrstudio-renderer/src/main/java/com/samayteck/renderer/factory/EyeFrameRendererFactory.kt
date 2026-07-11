package com.samayteck.renderer.factory

import com.samayteck.core.model.EyeShape
import com.samayteck.core.renderer.eye.EyeFrameRenderer
import com.samayteck.renderer.eye.frame.CircleEyeFrameRenderer
import com.samayteck.renderer.eye.frame.CorneredEyeFrameRenderer
import com.samayteck.renderer.eye.frame.LeafEyeFrameRenderer
import com.samayteck.renderer.eye.frame.RoundedEyeFrameRenderer
import com.samayteck.renderer.eye.frame.ShieldEyeFrameRenderer
import com.samayteck.renderer.eye.frame.SquareEyeFrameRenderer

internal object EyeFrameRendererFactory {

    fun create(
        shape: EyeShape
    ): EyeFrameRenderer {

        return when (shape) {

            EyeShape.SQUARE ->
                SquareEyeFrameRenderer

            EyeShape.ROUNDED ->
                RoundedEyeFrameRenderer

            EyeShape.CIRCLE ->
                CircleEyeFrameRenderer

            EyeShape.LEAF ->
                LeafEyeFrameRenderer

            EyeShape.SHIELD ->
                ShieldEyeFrameRenderer

            EyeShape.CORNERED ->
                CorneredEyeFrameRenderer
        }
    }
}