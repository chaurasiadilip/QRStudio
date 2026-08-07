package com.samayteck.qrstudio.compose

import android.Manifest
import android.util.Log
import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import com.samayteck.qrstudio.util.QrSafetyAnalyzer
import com.samayteck.qrstudio.util.SafetyLevel
import com.samayteck.qrstudio.util.SafetyReport
import java.util.concurrent.Executors

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScannerScreen(
    onClose: () -> Unit,
    onResult: (String) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    var scannedResult by remember { mutableStateOf<String?>(null) }
    var safetyReport by remember { mutableStateOf<SafetyReport?>(null) }
    var isTorchEnabled by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!cameraPermissionState.status.isGranted) {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    if (cameraPermissionState.status.isGranted) {
        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { ctx ->
                    val previewView = PreviewView(ctx)
                    val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)

                    cameraProviderFuture.addListener({
                        val cameraProvider = cameraProviderFuture.get()
                        val preview = Preview.Builder().build().also {
                            it.setSurfaceProvider(previewView.surfaceProvider)
                        }

                        val imageAnalysis = ImageAnalysis.Builder()
                            .setTargetResolution(Size(1280, 720))
                            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                            .build()

                        imageAnalysis.setAnalyzer(Executors.newSingleThreadExecutor()) { imageProxy ->
                            val result = decodeQrCode(imageProxy)
                            if (result != null && scannedResult == null) {
                                Log.d("ScannerScreen", "QR Scanned: $result")
                                scannedResult = result
                                safetyReport = QrSafetyAnalyzer.analyze(result)
                            }
                            imageProxy.close()
                        }

                        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                        try {
                            cameraProvider.unbindAll()
                            val camera = cameraProvider.bindToLifecycle(
                                lifecycleOwner,
                                cameraSelector,
                                preview,
                                imageAnalysis
                            )
                            // Note: Torch control would need a reference to the camera object
                        } catch (e: Exception) {
                            Log.e("ScannerScreen", "Camera binding failed", e)
                        }
                    }, ContextCompat.getMainExecutor(ctx))
                    previewView
                },
                modifier = Modifier.fillMaxSize()
            )

            // Overlays
            ScannerOverlay()

            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onClose,
                    modifier = Modifier.background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                ) {
                    Icon(Icons.Default.Close, null, tint = Color.White)
                }
                
                Text(
                    "Smart Scanner",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                IconButton(
                    onClick = { isTorchEnabled = !isTorchEnabled },
                    modifier = Modifier.background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                ) {
                    Icon(
                        if (isTorchEnabled) Icons.Default.FlashOn else Icons.Default.FlashOff,
                        null,
                        tint = if (isTorchEnabled) Color.Yellow else Color.White
                    )
                }
            }

            // Results Dialog
            if (scannedResult != null && safetyReport != null) {
                SafetyAnalysisDialog(
                    result = scannedResult!!,
                    report = safetyReport!!,
                    onDismiss = { scannedResult = null },
                    onUse = {
                        onResult(scannedResult!!)
                        onClose()
                    }
                )
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.Warning, null, modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.error)
                Spacer(Modifier.height(16.dp))
                Text("Camera permission is required to scan QR codes.")
                Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                    Text("Grant Permission")
                }
            }
        }
    }
}

@Composable
fun ScannerOverlay() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        val rectSize = width * 0.7f
        val left = (width - rectSize) / 2
        val top = (height - rectSize) / 2
        val rect = Rect(left, top, left + rectSize, top + rectSize)

        // Dim background
        val path = Path().apply {
            addRect(Rect(0f, 0f, width, height))
            addRoundRect(RoundRect(rect, CornerRadius(24.dp.toPx(), 24.dp.toPx())))
        }

        clipPath(path, clipOp = ClipOp.Difference) {
            drawRect(Color.Black.copy(alpha = 0.6f))
        }

        // Animated Scan Line or Corners
        drawRoundRect(
            color = Color(0xFF3B76F6),
            topLeft = rect.topLeft,
            size = rect.size,
            style = Stroke(width = 4.dp.toPx()),
            cornerRadius = CornerRadius(24.dp.toPx(), 24.dp.toPx())
        )
    }
}

@Composable
fun SafetyAnalysisDialog(
    result: String,
    report: SafetyReport,
    onDismiss: () -> Unit,
    onUse: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    when (report.level) {
                        SafetyLevel.SAFE -> Icons.Default.Close // Placeholder for Shield/Check
                        SafetyLevel.SUSPICIOUS -> Icons.Default.Warning
                        SafetyLevel.DANGEROUS -> Icons.Default.Warning
                        SafetyLevel.UNKNOWN -> Icons.Default.Warning
                    },
                    contentDescription = null,
                    tint = when (report.level) {
                        SafetyLevel.SAFE -> Color(0xFF4CAF50)
                        SafetyLevel.SUSPICIOUS -> Color(0xFFFBC02D)
                        SafetyLevel.DANGEROUS -> Color(0xFFD32F2F)
                        SafetyLevel.UNKNOWN -> Color.Gray
                    }
                )
                Spacer(Modifier.width(8.dp))
                Text(report.title, fontWeight = FontWeight.ExtraBold)
            }
        },
        text = {
            Column {
                Text(result, maxLines = 2, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(12.dp))
                Text(report.description)
                if (report.details.isNotEmpty()) {
                    Spacer(Modifier.height(8.dp))
                    report.details.forEach { detail ->
                        Text("• $detail", fontSize = 12.sp, color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onUse,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (report.level == SafetyLevel.DANGEROUS) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Copy to Generator")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Scan Again")
            }
        },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    )
}

private fun decodeQrCode(imageProxy: ImageProxy): String? {
    val buffer = imageProxy.planes[0].buffer
    val data = ByteArray(buffer.remaining())
    buffer.get(data)
    
    val source = PlanarYUVLuminanceSource(
        data, imageProxy.width, imageProxy.height, 0, 0, imageProxy.width, imageProxy.height, false
    )
    val binaryBitmap = BinaryBitmap(HybridBinarizer(source))
    
    return try {
        val reader = MultiFormatReader()
        val hints = mapOf<DecodeHintType, Any>(
            DecodeHintType.POSSIBLE_FORMATS to listOf(BarcodeFormat.QR_CODE),
            DecodeHintType.TRY_HARDER to true
        )
        val result = reader.decode(binaryBitmap, hints)
        result.text
    } catch (e: Exception) {
        null
    }
}
