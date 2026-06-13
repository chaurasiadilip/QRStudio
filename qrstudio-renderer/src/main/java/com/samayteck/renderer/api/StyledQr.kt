package com.samayteck.renderer.api

import android.graphics.Bitmap
import com.samayteck.core.encoder.DefaultQrEncoder
import com.samayteck.core.model.StyledQrOptions
import com.samayteck.renderer.renderer.RendererFactory

object StyledQr {

    fun generate(
        options: StyledQrOptions
    ): Result<Bitmap> {

        return runCatching {

            val encoder =
                DefaultQrEncoder()

            val matrix =
                encoder.encode(
                    content = options.content.encode(),
                    size = options.size,
                    options =
                        options.encodingOptions
                )

            RendererFactory
                .create()
                .render(
                    matrix.matrix,
                    options
                )
                .bitmap
        }
    }
}