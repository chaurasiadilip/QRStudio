package com.samayteck.renderer.factory

import com.samayteck.core.model.FrameStyle
import com.samayteck.core.renderer.frame.FrameRenderer
import com.samayteck.renderer.frame.BoxFrameRenderer
import com.samayteck.renderer.frame.BracketFrameRenderer
import com.samayteck.renderer.frame.CircleFrameRenderer
import com.samayteck.renderer.frame.FocusFrameRenderer
import com.samayteck.renderer.frame.HeartFrameRenderer
import com.samayteck.renderer.frame.HexagonFrameRenderer
import com.samayteck.renderer.frame.MinimalFrameRenderer
import com.samayteck.renderer.frame.RoundedFrameRenderer
import com.samayteck.renderer.frame.SpeechBubbleFrameRenderer
import com.samayteck.renderer.frame.TabFrameRenderer
import com.samayteck.renderer.frame.TextOnlyFrameRenderer
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
            
            FrameStyle.BRACKET ->
                BracketFrameRenderer
                
            FrameStyle.FOCUS ->
                FocusFrameRenderer
            
            FrameStyle.SPEECH_BUBBLE ->
                SpeechBubbleFrameRenderer
                
            FrameStyle.MINIMAL ->
                MinimalFrameRenderer
                
            FrameStyle.TAB ->
                TabFrameRenderer
                
            FrameStyle.HEART ->
                HeartFrameRenderer
                
            FrameStyle.HEXAGON ->
                HexagonFrameRenderer

            FrameStyle.BOX ->
                BoxFrameRenderer

            FrameStyle.TEXT_ONLY ->
                TextOnlyFrameRenderer
        }
    }
}