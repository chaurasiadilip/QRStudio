package com.samayteck.qrstudio.util

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

object QrExportUtils {

    fun saveToGallery(context: Context, bitmap: Bitmap, name: String): Uri? {
        val filename = "${name}_${System.currentTimeMillis()}.png"
        var fos: OutputStream? = null
        var imageUri: Uri? = null
        
        val contentResolver = context.contentResolver

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/QRStudio")
            }
            imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = imageUri?.let { contentResolver.openOutputStream(it) }
        } else {
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/QRStudio"
            val file = File(imagesDir)
            if (!file.exists()) file.mkdir()
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
            imageUri = Uri.fromFile(image)
        }

        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
        }
        
        return imageUri
    }

    fun shareQrCode(context: Context, bitmap: Bitmap, name: String) {
        val cachePath = File(context.cacheDir, "images")
        cachePath.mkdirs()
        val stream = FileOutputStream("$cachePath/qr_code.png")
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.close()

        val imagePath = File(context.cacheDir, "images")
        val newFile = File(imagePath, "qr_code.png")
        val contentUri = FileProvider.getUriForFile(context, "${context.packageName}.provider", newFile)

        if (contentUri != null) {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                setDataAndType(contentUri, context.contentResolver.getType(contentUri))
                putExtra(Intent.EXTRA_STREAM, contentUri)
                type = "image/png"
            }
            context.startActivity(Intent.createChooser(shareIntent, "Share QR Code"))
        }
    }

    fun saveAsPdf(context: Context, bitmap: Bitmap, name: String): Uri? {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        
        val canvas = page.canvas
        canvas.drawBitmap(bitmap, 0f, 0f, null)
        pdfDocument.finishPage(page)

        val filename = "${name}_${System.currentTimeMillis()}.pdf"
        var imageUri: Uri? = null
        val contentResolver = context.contentResolver

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS + "/QRStudio")
            }
            imageUri = contentResolver.insert(MediaStore.Files.getContentUri("external"), contentValues)
            imageUri?.let { uri ->
                contentResolver.openOutputStream(uri)?.use { 
                    pdfDocument.writeTo(it)
                }
            }
        } else {
            val docsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString() + "/QRStudio"
            val file = File(docsDir)
            if (!file.exists()) file.mkdir()
            val pdfFile = File(docsDir, filename)
            pdfDocument.writeTo(FileOutputStream(pdfFile))
            imageUri = Uri.fromFile(pdfFile)
        }
        
        pdfDocument.close()
        return imageUri
    }
}
