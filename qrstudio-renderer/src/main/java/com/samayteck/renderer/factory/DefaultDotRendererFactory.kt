package com.samayteck.renderer.factory

import com.samayteck.core.model.DotShape
import com.samayteck.core.renderer.dot.DotRenderer
import com.samayteck.renderer.dot.CircleDotRenderer
import com.samayteck.renderer.dot.ClassyDotRenderer
import com.samayteck.renderer.dot.ClassyRoundedDotRenderer
import com.samayteck.renderer.dot.DiamondDotRenderer
import com.samayteck.renderer.dot.HeartDotRenderer
import com.samayteck.renderer.dot.HexagonDotRenderer
import com.samayteck.renderer.dot.RoundedDotRenderer
import com.samayteck.renderer.dot.SlimDotRenderer
import com.samayteck.renderer.dot.SquareDotRenderer
import com.samayteck.renderer.dot.StarDotRenderer

internal object DefaultDotRendererFactory {

    fun create(
        shape: DotShape
    ): DotRenderer {

        return when (shape) {

            DotShape.SQUARE ->
                SquareDotRenderer

            DotShape.ROUNDED ->
                RoundedDotRenderer

            DotShape.CIRCLE ->
                CircleDotRenderer

            DotShape.DIAMOND ->
                DiamondDotRenderer

            DotShape.HEXAGON ->
                HexagonDotRenderer

            DotShape.HEART ->
                HeartDotRenderer

            DotShape.STAR ->
                StarDotRenderer

            DotShape.CLASSY ->
                ClassyDotRenderer

            DotShape.CLASSY_ROUNDED ->
                ClassyRoundedDotRenderer

            DotShape.SLIM ->
                SlimDotRenderer
        }
    }
}