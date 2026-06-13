package com.samayteck.qrstudiotest.ui

import com.samayteck.core.model.BackgroundPattern
import com.samayteck.core.model.DotShape
import com.samayteck.core.model.EyeShape
import com.samayteck.core.model.FrameStyle

data class SampleUiState(

    val content: String =
        "https://github.com",

    val dotShape: DotShape =
        DotShape.ROUNDED,

    val eyeShape: EyeShape =
        EyeShape.ROUNDED,

    val frameStyle: FrameStyle =
        FrameStyle.ROUNDED,

    val backgroundPattern:
    BackgroundPattern =
        BackgroundPattern.NONE
)