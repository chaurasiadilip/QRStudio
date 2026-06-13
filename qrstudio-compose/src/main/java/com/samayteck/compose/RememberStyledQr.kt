package com.samayteck.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.samayteck.core.model.StyledQrOptions
import com.samayteck.renderer.api.StyledQr

@Composable
fun rememberStyledQr(
    options: StyledQrOptions
): StyledQrState {

    var state by remember {

        mutableStateOf(
            StyledQrState(
                isLoading = true
            )
        )
    }

    LaunchedEffect(options) {

        state =
            StyledQrState(
                isLoading = true
            )

        StyledQr.generate(options)
            .onSuccess {

                state =
                    StyledQrState(
                        bitmap = it
                    )
            }
            .onFailure {

                state =
                    StyledQrState(
                        throwable = it
                    )
            }
    }

    return state
}