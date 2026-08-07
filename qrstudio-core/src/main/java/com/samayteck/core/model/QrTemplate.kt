package com.samayteck.core.model

import android.graphics.Color

data class QrTemplate(
    val id: String,
    val name: String,
    val description: String,
    val aspectRatio: Float = 1f, // width / height
    val qrPosition: QrPosition = QrPosition(),
    val background: TemplateBackground = TemplateBackground.Solid(Color.WHITE),
    val textOverlays: List<TextOverlay> = emptyList(),
    val defaultOptions: StyledQrOptions? = null // Optional base styling
) {
    companion object {
        val ALL_TEMPLATES = listOf(
            QrTemplate(
                id = "business_card",
                name = "Modern Business",
                description = "Clean professional layout for business cards.",
                aspectRatio = 1.75f,
                qrPosition = QrPosition(centerX = 0.8f, centerY = 0.5f, sizePercent = 0.4f),
                background = TemplateBackground.Solid(Color.WHITE),
                textOverlays = listOf(
                    TextOverlay("JOHN DOE", 0.1f, 0.4f, 48f, Color.BLACK, "BOLD", "LEFT"),
                    TextOverlay("Creative Director", 0.1f, 0.5f, 24f, Color.GRAY, "NORMAL", "LEFT"),
                    TextOverlay("www.samayteck.com", 0.1f, 0.8f, 20f, Color.BLUE, "NORMAL", "LEFT")
                )
            ),
            QrTemplate(
                id = "restaurant_menu",
                name = "Table Menu",
                description = "Vertical flyer for restaurant tables.",
                aspectRatio = 0.7f,
                qrPosition = QrPosition(centerX = 0.5f, centerY = 0.6f, sizePercent = 0.5f),
                background = TemplateBackground.Gradient(
                    intArrayOf(Color.parseColor("#FFF8E1"), Color.parseColor("#FFECB3")),
                    TemplateBackground.GradientType.LINEAR
                ),
                textOverlays = listOf(
                    TextOverlay("SCAN FOR MENU", 0.5f, 0.2f, 60f, Color.parseColor("#795548"), "BOLD", "CENTER"),
                    TextOverlay("Check our daily specials!", 0.5f, 0.9f, 30f, Color.BLACK, "NORMAL", "CENTER")
                )
            ),
            QrTemplate(
                id = "social_media",
                name = "Social Growth",
                description = "Vibrant layout for Instagram or TikTok.",
                aspectRatio = 1f,
                qrPosition = QrPosition(centerX = 0.5f, centerY = 0.5f, sizePercent = 0.6f),
                background = TemplateBackground.Gradient(
                    intArrayOf(Color.parseColor("#833ab4"), Color.parseColor("#fd1d1d"), Color.parseColor("#fcb045")),
                    TemplateBackground.GradientType.LINEAR
                ),
                textOverlays = listOf(
                    TextOverlay("FOLLOW US", 0.5f, 0.15f, 70f, Color.WHITE, "BOLD", "CENTER"),
                    TextOverlay("@samayteck", 0.5f, 0.85f, 40f, Color.WHITE, "BOLD", "CENTER")
                )
            ),
            QrTemplate(
                id = "wedding_invitation",
                name = "Wedding Joy",
                description = "Elegant floral layout for invitations.",
                aspectRatio = 0.8f,
                qrPosition = QrPosition(centerX = 0.5f, centerY = 0.75f, sizePercent = 0.35f),
                background = TemplateBackground.Gradient(
                    intArrayOf(Color.parseColor("#FFF5F5"), Color.parseColor("#FFE0E0")),
                    TemplateBackground.GradientType.RADIAL
                ),
                textOverlays = listOf(
                    TextOverlay("SAVE THE DATE", 0.5f, 0.15f, 50f, Color.parseColor("#B71C1C"), "BOLD", "CENTER"),
                    TextOverlay("Sarah & Mark", 0.5f, 0.3f, 80f, Color.parseColor("#B71C1C"), "BOLD", "CENTER"),
                    TextOverlay("SCAN FOR RSVP", 0.5f, 0.92f, 24f, Color.GRAY, "NORMAL", "CENTER")
                )
            ),
            QrTemplate(
                id = "event_ticket",
                name = "VIP Entry",
                description = "Dark theme layout for event tickets.",
                aspectRatio = 1.8f,
                qrPosition = QrPosition(centerX = 0.2f, centerY = 0.5f, sizePercent = 0.6f),
                background = TemplateBackground.Solid(Color.parseColor("#212121")),
                textOverlays = listOf(
                    TextOverlay("TECH CONFERENCE 2026", 0.65f, 0.3f, 40f, Color.WHITE, "BOLD", "CENTER"),
                    TextOverlay("VIP PASS", 0.65f, 0.5f, 60f, Color.parseColor("#FFD600"), "BOLD", "CENTER"),
                    TextOverlay("ADMIT ONE", 0.65f, 0.8f, 30f, Color.WHITE, "NORMAL", "CENTER")
                )
            )
        )
    }
}

data class QrPosition(
    val centerX: Float = 0.5f, // 0.0 to 1.0 relative to canvas width
    val centerY: Float = 0.5f, // 0.0 to 1.0 relative to canvas height
    val sizePercent: Float = 0.3f // size relative to the smaller dimension of canvas
)

sealed class TemplateBackground {
    data class Solid(val color: Int) : TemplateBackground()
    data class Gradient(val colors: IntArray, val type: GradientType = GradientType.LINEAR) : TemplateBackground() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Gradient) return false
            if (!colors.contentEquals(other.colors)) return false
            if (type != other.type) return false
            return true
        }

        override fun hashCode(): Int {
            var result = colors.contentHashCode()
            result = 31 * result + type.hashCode()
            return result
        }
    }
    data class Image(val assetPath: String) : TemplateBackground()
    
    enum class GradientType { LINEAR, RADIAL, SWEEP }
}

data class TextOverlay(
    val text: String,
    val x: Float, // 0.0 to 1.0 relative to canvas width
    val y: Float, // 0.0 to 1.0 relative to canvas height
    val fontSize: Float = 24f,
    val color: Int = Color.BLACK,
    val fontWeight: String = "NORMAL", // NORMAL, BOLD
    val alignment: String = "CENTER" // LEFT, CENTER, RIGHT
)
