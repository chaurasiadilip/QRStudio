package com.samayteck.renderer.gradient

import android.graphics.Paint
import com.samayteck.core.model.GradientStyle

internal object GradientPaintFactory {

    fun create(
        size: Int,
        gradientStyle: GradientStyle
    ): Paint {

        return Paint(
            Paint.ANTI_ALIAS_FLAG
        ).apply {

            style = Paint.Style.FILL

            shader =
                when (gradientStyle) {

                    is GradientStyle.None ->
                        null

                    is GradientStyle.Linear ->
                        LinearGradientFactory.create(
                            size = size,
                            startColor =
                                gradientStyle.startColor,
                            endColor =
                                gradientStyle.endColor
                        )

                    is GradientStyle.Radial ->
                        RadialGradientFactory.create(
                            size = size,
                            centerColor =
                                gradientStyle.centerColor,
                            edgeColor =
                                gradientStyle.edgeColor
                        )

                    is GradientStyle.Sweep ->
                        SweepGradientFactory.create(
                            size = size,
                            colors =
                                gradientStyle.colors
                        )
                }
        }
    }
}