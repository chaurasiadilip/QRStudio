package com.samayteck.renderer.renderer

import android.content.Context
import android.graphics.*
import com.samayteck.core.model.QrTemplate
import com.samayteck.core.model.StyledQrOptions
import com.samayteck.core.model.TemplateBackground
import com.samayteck.renderer.api.StyledQr

class PosterRenderer(private val context: Context) {

    fun render(template: QrTemplate, options: StyledQrOptions, targetWidth: Int): Result<Bitmap> {
        return runCatching {
            val targetHeight = (targetWidth / template.aspectRatio).toInt()
            val posterBitmap = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(posterBitmap)

            // 1. Draw Background
            drawBackground(canvas, template.background, targetWidth, targetHeight)

            // 2. Render and Draw QR Code
            val qrSize = (minOf(targetWidth, targetHeight) * template.qrPosition.sizePercent).toInt()
            val qrOptions = options.copy(size = qrSize)
            val qrBitmap = StyledQr.generate(qrOptions).getOrThrow()
            
            val qrLeft = (targetWidth * template.qrPosition.centerX) - (qrSize / 2f)
            val qrTop = (targetHeight * template.qrPosition.centerY) - (qrSize / 2f)
            canvas.drawBitmap(qrBitmap, qrLeft, qrTop, null)

            // 3. Draw Text Overlays
            template.textOverlays.forEach { overlay ->
                drawText(canvas, overlay, targetWidth, targetHeight)
            }

            posterBitmap
        }
    }

    private fun drawBackground(canvas: Canvas, background: TemplateBackground, width: Int, height: Int) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        when (background) {
            is TemplateBackground.Solid -> {
                canvas.drawColor(background.color)
            }
            is TemplateBackground.Gradient -> {
                val shader = when (background.type) {
                    TemplateBackground.GradientType.LINEAR -> 
                        LinearGradient(0f, 0f, 0f, height.toFloat(), background.colors, null, Shader.TileMode.CLAMP)
                    TemplateBackground.GradientType.RADIAL -> 
                        RadialGradient(width / 2f, height / 2f, maxOf(width, height) / 2f, background.colors, null, Shader.TileMode.CLAMP)
                    TemplateBackground.GradientType.SWEEP -> 
                        SweepGradient(width / 2f, height / 2f, background.colors, null)
                }
                paint.shader = shader
                canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
            }
            is TemplateBackground.Image -> {
                try {
                    val stream = context.assets.open(background.assetPath)
                    val bitmap = BitmapFactory.decodeStream(stream)
                    val src = Rect(0, 0, bitmap.width, bitmap.height)
                    val dst = Rect(0, 0, width, height)
                    canvas.drawBitmap(bitmap, src, dst, Paint(Paint.FILTER_BITMAP_FLAG))
                } catch (e: Exception) {
                    canvas.drawColor(Color.LTGRAY) // Fallback
                }
            }
        }
    }

    private fun drawText(canvas: Canvas, overlay: com.samayteck.core.model.TextOverlay, width: Int, height: Int) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = overlay.color
            textSize = overlay.fontSize * (width / 1000f) // Scale font size relative to width
            typeface = if (overlay.fontWeight == "BOLD") Typeface.DEFAULT_BOLD else Typeface.DEFAULT
            textAlign = when (overlay.alignment) {
                "LEFT" -> Paint.Align.LEFT
                "RIGHT" -> Paint.Align.RIGHT
                else -> Paint.Align.CENTER
            }
        }
        
        val x = width * overlay.x
        val y = height * overlay.y
        canvas.drawText(overlay.text, x, y, paint)
    }
}
