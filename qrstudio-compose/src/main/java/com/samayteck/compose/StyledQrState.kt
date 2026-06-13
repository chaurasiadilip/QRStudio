package com.samayteck.compose

import android.graphics.Bitmap
import androidx.compose.runtime.Immutable

@Immutable
data class StyledQrState(

    val bitmap: Bitmap? = null,

    val isLoading: Boolean = false,

    val throwable: Throwable? = null
)