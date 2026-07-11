package com.samayteck.renderer.factory

import com.samayteck.core.model.EyeBallShape
import com.samayteck.core.renderer.eye.EyeBallRenderer
import com.samayteck.renderer.eye.ball.CircleEyeBallRenderer
import com.samayteck.renderer.eye.ball.DiamondEyeBallRenderer
import com.samayteck.renderer.eye.ball.LeafEyeBallRenderer
import com.samayteck.renderer.eye.ball.RoundedEyeBallRenderer
import com.samayteck.renderer.eye.ball.SquareEyeBallRenderer

internal object EyeBallRendererFactory {

    fun create(
        shape: EyeBallShape
    ): EyeBallRenderer {

        return when (shape) {

            EyeBallShape.SQUARE ->
                SquareEyeBallRenderer

            EyeBallShape.ROUNDED ->
                RoundedEyeBallRenderer

            EyeBallShape.CIRCLE ->
                CircleEyeBallRenderer

            EyeBallShape.DIAMOND ->
                DiamondEyeBallRenderer

            EyeBallShape.LEAF ->
                LeafEyeBallRenderer
        }
    }
}