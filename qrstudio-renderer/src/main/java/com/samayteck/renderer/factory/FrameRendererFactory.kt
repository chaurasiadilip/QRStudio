package com.samayteck.renderer.factory

import com.samayteck.core.model.FrameStyle
import com.samayteck.core.renderer.frame.FrameRenderer
import com.samayteck.renderer.frame.CircleFrameRenderer
import com.samayteck.renderer.frame.RoundedFrameRenderer
import com.samayteck.renderer.frame.TicketFrameRenderer

internal object FrameRendererFactory {

    fun create(
        style: FrameStyle
    ): FrameRenderer? {

        return when (style) {

            FrameStyle.NONE ->
                null

            FrameStyle.ROUNDED ->
                RoundedFrameRenderer

            FrameStyle.CIRCLE ->
                CircleFrameRenderer

            FrameStyle.TICKET ->
                TicketFrameRenderer
        }
    }
}