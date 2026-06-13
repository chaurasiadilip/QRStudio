package com.samayteck.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import com.samayteck.core.model.StyledQrOptions

@Composable
fun StyledQrCode(
    options: StyledQrOptions,
    modifier: Modifier = Modifier,
) {

    val state =
        rememberStyledQr(options)

    when {

        state.isLoading -> {

            Box(
                modifier = modifier,
                contentAlignment =
                    Alignment.Center
            ) {

                CircularProgressIndicator()
            }
        }

        state.throwable != null -> {

            Box(
                modifier = modifier,
                contentAlignment =
                    Alignment.Center
            ) {

                Text(
                    text =
                        state.throwable.message
                            ?: "QR generation failed"
                )
            }
        }

        state.bitmap != null -> {

            Image(
                bitmap =
                    state.bitmap
                        .asImageBitmap(),

                contentDescription =
                    "Styled QR",

                modifier = modifier
            )
        }
    }
}