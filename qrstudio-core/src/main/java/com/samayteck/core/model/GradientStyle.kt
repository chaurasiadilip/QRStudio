package com.samayteck.core.model

sealed interface GradientStyle {
    data object None : GradientStyle

    data class Linear(
        val startColor: Int,
        val endColor: Int
    ) : GradientStyle

    data class Radial(
        val centerColor: Int,
        val edgeColor: Int
    ) : GradientStyle

    data class Sweep(
        val colors: IntArray
    ) : GradientStyle
}