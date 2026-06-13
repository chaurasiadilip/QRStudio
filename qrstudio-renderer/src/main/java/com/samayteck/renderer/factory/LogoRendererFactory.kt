package com.samayteck.renderer.factory

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.samayteck.core.renderer.logo.LogoRenderer
import com.samayteck.renderer.logo.BitmapLogoRenderer
import com.samayteck.renderer.logo.VectorLogoRenderer

internal object LogoRendererFactory {

    fun bitmap(
        bitmap: Bitmap,
        percent: Float
    ): LogoRenderer {

        return BitmapLogoRenderer(
            bitmap,
            percent
        )
    }

    fun vector(
        drawable: Drawable,
        percent: Float
    ): LogoRenderer {

        return VectorLogoRenderer(
            drawable,
            percent
        )
    }
}