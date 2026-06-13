package com.samayteck.renderer.factory

import android.graphics.Color
import com.samayteck.core.model.BackgroundPattern
import com.samayteck.core.model.BackgroundStyle
import com.samayteck.core.renderer.background.BackgroundRenderer
import com.samayteck.renderer.background.DotsBackgroundRenderer
import com.samayteck.renderer.background.GridBackgroundRenderer
import com.samayteck.renderer.background.SolidBackgroundRenderer
import com.samayteck.renderer.background.TransparentBackgroundRenderer

internal object DefaultBackgroundRendererFactory {

    fun create(
        style: BackgroundStyle,
        pattern: BackgroundPattern,
        backgroundColor: Int
    ): BackgroundRenderer {

        if (
            style == BackgroundStyle.TRANSPARENT
        ) {
            return TransparentBackgroundRenderer
        }

        return when (pattern) {

            BackgroundPattern.NONE ->
                SolidBackgroundRenderer(
                    backgroundColor
                )

            BackgroundPattern.DOTS ->
                DotsBackgroundRenderer(
                    backgroundColor =
                        backgroundColor,

                    dotColor =
                        Color.LTGRAY
                )

            BackgroundPattern.GRID ->
                GridBackgroundRenderer(
                    backgroundColor =
                        backgroundColor,

                    gridColor =
                        Color.LTGRAY
                )
        }
    }
}