package com.samayteck.qrstudio.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.samayteck.core.model.StyledQrOptions

@Entity(tableName = "qr_history")
data class QrHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val content: String,
    val type: String,
    val optionsJson: String, // We'll store StyledQrOptions as JSON
    val templateId: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = false,
    val isScanned: Boolean = false
)

class QrTypeConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromOptions(options: StyledQrOptions): String {
        return gson.toJson(options)
    }

    @TypeConverter
    fun toOptions(json: String): StyledQrOptions {
        return gson.fromJson(json, StyledQrOptions::class.java)
    }
}
