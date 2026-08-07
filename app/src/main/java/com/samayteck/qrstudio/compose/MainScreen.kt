package com.samayteck.qrstudio.compose

import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.geometry.Size
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.spring
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.widthIn
import androidx.compose.ui.draw.alpha
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.systemBars
import androidx.compose.ui.graphics.toArgb
import com.samayteck.core.model.*
import com.samayteck.qrstudio.R
import com.samayteck.qrstudio.data.QrDatabase
import com.samayteck.qrstudio.data.QrHistoryEntity
import com.google.gson.Gson
import kotlinx.coroutines.launch
import com.samayteck.renderer.api.StyledQr
import com.samayteck.svg.SvgLogoProvider
import com.samayteck.core.content.basic.*
import com.samayteck.core.content.social.*
import com.samayteck.core.content.business.*
import com.samayteck.core.content.location.LocationContent
import com.samayteck.core.content.contact.VCardContent
import com.samayteck.core.content.contact.MeCardContent
import com.samayteck.core.content.event.CalendarContent
import com.samayteck.core.encoder.EncodingOptions
import com.samayteck.core.encoder.ErrorCorrectionLevel
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.samayteck.compose.StyledQrCode
import com.samayteck.qrstudio.util.QrExportUtils
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import android.graphics.Bitmap
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Tab
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.times
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.zIndex

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val database = remember { QrDatabase.getDatabase(context) }
    val historyDao = database.qrHistoryDao()
    
    val historyList by historyDao.getAllHistory().collectAsState(initial = emptyList())
    
    var currentScreen by remember { mutableStateOf("CONTENT") }
    var contentType by remember { mutableStateOf("URL") }
    var qrContent by remember { mutableStateOf("https://github.com") }
    
    // States
    var wifiSsid by remember { mutableStateOf("") }
    var wifiPassword by remember { mutableStateOf("") }
    var wifiSecurity by remember { mutableStateOf("WPA") }
    var wifiHidden by remember { mutableStateOf(false) }
    var socialUsername by remember { mutableStateOf("") }
    var socialMessage by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var emailAddress by remember { mutableStateOf("") }
    var emailSubject by remember { mutableStateOf("") }
    var emailBody by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var organization by remember { mutableStateOf("") }
    var jobTitle by remember { mutableStateOf("") }
    var workPhone by remember { mutableStateOf("") }
    var mobilePhone by remember { mutableStateOf("") }
    var contactEmail by remember { mutableStateOf("") }
    var contactUrl by remember { mutableStateOf("") }
    var street by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var zip by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var contactNote by remember { mutableStateOf("") }
    var latitude by remember { mutableStateOf("0.0") }
    var longitude by remember { mutableStateOf("0.0") }
    var eventTitle by remember { mutableStateOf("") }
    var eventStart by remember { mutableStateOf("") }
    var eventEnd by remember { mutableStateOf("") }
    var cryptoAddress by remember { mutableStateOf("") }
    var cryptoAmount by remember { mutableStateOf("") }

    var upiAddress by remember { mutableStateOf("") }
    var upiName by remember { mutableStateOf("") }
    var upiAmount by remember { mutableStateOf("") }
    var upiNote by remember { mutableStateOf("") }

    var dotShape by remember { mutableStateOf(DotShape.ROUNDED) }
    var eyeFrameShape by remember { mutableStateOf(EyeShape.ROUNDED) }
    var eyeBallShape by remember { mutableStateOf(EyeBallShape.ROUNDED) }
    var eyeFrameColor by remember { mutableStateOf<Color?>(null) }
    var eyeBallColor by remember { mutableStateOf<Color?>(null) }
    var frameStyle by remember { mutableStateOf(FrameStyle.NONE) }
    var frameLabel by remember { mutableStateOf("SCAN ME") }
    var frameColor by remember { mutableStateOf(Color.Black) }
    var frameGradientStyle by remember { mutableStateOf<GradientStyle>(GradientStyle.None) }
    var frameFont by remember { mutableStateOf("SANS_SERIF") }
    var showLogo by remember { mutableStateOf(false) }
    var useSvgLogo by remember { mutableStateOf(false) }
    var logoSize by remember { mutableStateOf(0.2f) }
    var logoDrawBackground by remember { mutableStateOf(true) }
    var selectedLogoName by remember { mutableStateOf("App Icon") }
    var logoShape by remember { mutableStateOf(LogoShape.CIRCLE) }
    
    var selectedTemplate by remember { mutableStateOf<QrTemplate?>(null) }
    
    var colorScheme by remember { mutableStateOf("Black") }
    var bgColor by remember { mutableStateOf("White") }
    var errorCorrectionLevel by remember { mutableStateOf(ErrorCorrectionLevel.HIGH) }

    var selectedTab by remember { mutableIntStateOf(0) }
    var qrPremiumPosterBitmap by remember { mutableStateOf<Bitmap?>(null) }

    val backgroundColor = remember(bgColor) {
        when (bgColor) {
            "Light Gray" -> android.graphics.Color.LTGRAY
            "Yellow" -> android.graphics.Color.YELLOW
            "Cyan" -> android.graphics.Color.CYAN
            else -> android.graphics.Color.WHITE
        }
    }

    val finalContent = remember(
        contentType, qrContent, wifiSsid, wifiPassword, wifiSecurity, wifiHidden,
        socialUsername, socialMessage, phoneNumber, emailAddress, emailSubject, emailBody,
        firstName, lastName, organization, jobTitle, workPhone, mobilePhone, contactEmail,
        contactUrl, street, city, zip, state, country, contactNote,
        latitude, longitude, eventTitle, eventStart, eventEnd,
        cryptoAddress, cryptoAmount,
        upiAddress, upiName, upiAmount, upiNote
    ) {
        when (contentType) {
            "URL" -> UrlContent(qrContent)
            "Text" -> TextContent(qrContent)
            "Wi-Fi" -> WifiContent(wifiSsid, wifiPassword, when (wifiSecurity) {
                "WPA" -> WifiContent.Security.WPA
                "WEP" -> WifiContent.Security.WEP
                else -> WifiContent.Security.NONE
            }, wifiHidden)
            "vCard" -> VCardContent(firstName, lastName, organization, jobTitle, workPhone, mobilePhone, contactEmail, contactUrl, street, city, zip, state, country)
            "MeCard" -> MeCardContent("$firstName $lastName", mobilePhone.ifBlank { workPhone }, contactEmail, "$street, $city", contactUrl, contactNote)
            "Map" -> LocationContent(latitude.toDoubleOrNull() ?: 0.0, longitude.toDoubleOrNull() ?: 0.0)
            "Event" -> CalendarContent(eventTitle, eventStart, eventEnd)
            "Bitcoin" -> BitcoinContent(cryptoAddress, cryptoAmount, socialMessage)
            "Ethereum" -> EthereumContent(cryptoAddress, cryptoAmount)
            "Solana" -> SolanaContent(cryptoAddress, cryptoAmount)
            "UPI" -> UpiContent(upiAddress, upiName, upiAmount, upiNote)
            "WhatsApp" -> WhatsAppContent(phoneNumber, socialMessage)
            "Telegram" -> TelegramContent(socialUsername)
            "Instagram" -> InstagramContent(socialUsername)
            "Facebook" -> FacebookContent(socialUsername)
            "YouTube" -> YouTubeContent(qrContent)
            "X (Twitter)" -> XContent(socialUsername)
            "TikTok" -> TikTokContent(socialUsername)
            "Discord" -> DiscordContent(socialUsername)
            "Twitch" -> TwitchContent(socialUsername)
            "Email" -> EmailContent(emailAddress, emailSubject, emailBody)
            "Phone" -> PhoneContent(phoneNumber)
            "Play Store" -> PlayStoreContent(qrContent)
            "App Store" -> AppStoreContent(qrContent)
            else -> UrlContent(qrContent)
        }
    }

    val gradientStyle = remember(colorScheme) {
        when (colorScheme) {
            "Blue Linear" -> GradientStyle.Linear(android.graphics.Color.BLUE, android.graphics.Color.CYAN)
            "Red Radial" -> GradientStyle.Radial(android.graphics.Color.RED, android.graphics.Color.BLACK)
            "Rainbow Sweep" -> GradientStyle.Sweep(intArrayOf(android.graphics.Color.RED, android.graphics.Color.YELLOW, android.graphics.Color.GREEN, android.graphics.Color.BLUE, android.graphics.Color.MAGENTA, android.graphics.Color.RED))
            else -> GradientStyle.None
        }
    }

    val logoBitmap = remember(showLogo, selectedLogoName) {
        if (!showLogo) return@remember null
       // ContextCompat.getDrawable(context, R.mipmap.ic_launcher)?.toBitmap()
        when (selectedLogoName) {
            "Apple" -> SvgLogoProvider.fromAsset(context, "apple.svg", 512).getOrNull()
            "Facebook" -> SvgLogoProvider.fromAsset(context, "facebook.svg", 512).getOrNull()
            "YouTube" -> SvgLogoProvider.fromAsset(context, "youtube.svg", 512).getOrNull()
            "Instagram" -> SvgLogoProvider.fromAsset(context, "instagram.svg", 512).getOrNull()
            "Reddit" -> SvgLogoProvider.fromAsset(context, "reddit.svg", 512).getOrNull()
            "WhatsApp" -> SvgLogoProvider.fromAsset(context, "whatsapp.svg", 512).getOrNull()
            "Wi-Fi" -> ContextCompat.getDrawable(context, R.drawable.wifi)?.toBitmap()
            "Vimeo" -> ContextCompat.getDrawable(context, R.drawable.vimeo)?.toBitmap()
            "Linkedin" -> ContextCompat.getDrawable(context, R.drawable.linkedin)?.toBitmap()
            "Call" -> ContextCompat.getDrawable(context, R.drawable.call)?.toBitmap()
            "Pinterest" -> ContextCompat.getDrawable(context, R.drawable.pinterest)?.toBitmap()
            "Calendar" -> ContextCompat.getDrawable(context, R.drawable.calendar)?.toBitmap()
            "Bitcoin" -> ContextCompat.getDrawable(context, R.drawable.bitcoin)?.toBitmap()
            "Ethereum" -> ContextCompat.getDrawable(context, R.drawable.ethereum)?.toBitmap()
            "Solana" -> ContextCompat.getDrawable(context, R.drawable.solana)?.toBitmap()
            "UPI" -> ContextCompat.getDrawable(context, R.drawable.upi_icon)?.toBitmap()
            "Twitch" -> ContextCompat.getDrawable(context, R.drawable.twitch)?.toBitmap()
            "Discord" -> ContextCompat.getDrawable(context, R.drawable.discord)?.toBitmap()
            else -> ContextCompat.getDrawable(context, R.mipmap.ic_launcher)?.toBitmap()
        }
    }

    val options = StyledQrOptions(
        content = finalContent, size = 1000, dotShape = dotShape,
        eyeFrameShape = eyeFrameShape, eyeBallShape = eyeBallShape,
        eyeFrameColor = eyeFrameColor?.let { it.toArgb() },
        eyeBallColor = eyeBallColor?.let { it.toArgb() },
        backgroundColor = backgroundColor, gradientStyle = gradientStyle,
        frameOptions = FrameOptions(
            frameStyle = frameStyle, 
            label = if (frameStyle != FrameStyle.NONE) frameLabel else null,
            frameColor = frameColor.toArgb(),
            labelColor = frameColor.toArgb(),
            fontType = frameFont
        ),
        logoOptions = LogoOptions(
            bitmap = logoBitmap, 
            logoPercent = logoSize,
            drawBackground = logoDrawBackground,
            logoShape = logoShape
        ),
        templateId = selectedTemplate?.id,
        encodingOptions = EncodingOptions(errorCorrectionLevel = errorCorrectionLevel)
    )

    LaunchedEffect(selectedTemplate, options) {
        qrPremiumPosterBitmap = if (selectedTemplate != null) {
            com.samayteck.renderer.renderer.PosterRenderer(context)
                .render(selectedTemplate!!, options, 1000)
                .getOrNull()
        } else {
            null
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { 
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.QrCode2, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(32.dp))
                        Spacer(Modifier.width(12.dp))
                      /*  Text(
                            text = if (currentScreen == "CONTENT") "Step 1: Content" else "Step 2: Design",
                            fontWeight = FontWeight.ExtraBold, 
                            fontSize = 20.sp
                        )*/
                    }
                },
                actions = {
                    if (currentScreen != "SCAN" && currentScreen != "HISTORY") {
                        val isFav = historyList.any { it.optionsJson == Gson().toJson(options) && it.isFavorite }
                        IconButton(onClick = { 
                            val existing = historyList.find { it.optionsJson == Gson().toJson(options) }
                            if (existing != null) {
                                scope.launch { historyDao.update(existing.copy(isFavorite = !existing.isFavorite)) }
                            } else {
                                scope.launch {
                                    historyDao.insert(QrHistoryEntity(
                                        content = finalContent.encode(),
                                        type = contentType,
                                        optionsJson = Gson().toJson(options),
                                        templateId = selectedTemplate?.id,
                                        isFavorite = true
                                    ))
                                }
                            }
                        }) {
                            Icon(
                                if (isFav) Icons.Default.Star else Icons.Default.StarOutline,
                                null,
                                tint = if (isFav) Color(0xFFFBC02D) else Color.Gray
                            )
                        }
                        IconButton(onClick = { currentScreen = "SCAN" }) {
                            Icon(Icons.Default.QrCodeScanner, contentDescription = "Scan QR")
                        }
                        IconButton(onClick = { currentScreen = "HISTORY" }) {
                            Icon(Icons.Default.History, contentDescription = "History")
                        }
                    }
                },
                navigationIcon = {
                    if (currentScreen == "DESIGN" || currentScreen == "SCAN" || currentScreen == "HISTORY") {
                        IconButton(onClick = { currentScreen = "CONTENT" }) {
                            Icon(Icons.Default.ArrowBack, null)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        bottomBar = {
            if (currentScreen != "SCAN") {
                Surface(
                    tonalElevation = 12.dp,
                    shadowElevation = 12.dp,
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                ) {
                    if (currentScreen == "CONTENT") {
                        Box(modifier = Modifier.fillMaxWidth().padding(20.dp).navigationBarsPadding()) {
                            Button(
                                onClick = { currentScreen = "DESIGN" },
                                modifier = Modifier.fillMaxWidth().height(56.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF3B76F6),
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Design Your QR", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Spacer(Modifier.width(8.dp))
                                Icon(Icons.Default.ArrowForward, null)
                            }
                        }
                    } else {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(20.dp).navigationBarsPadding(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            IconButton(
                                onClick = {
                                    StyledQr.generate(options).onSuccess { bitmap ->
                                        QrExportUtils.saveAsPdf(context, bitmap, "qr_code")
                                    }
                                },
                                modifier = Modifier.size(56.dp).background(Color.White, RoundedCornerShape(16.dp)).border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(16.dp))
                            ) {
                                Icon(Icons.Default.PictureAsPdf, null, tint = MaterialTheme.colorScheme.primary)
                            }
                            Button(
                                onClick = { 
                                    if (selectedTemplate != null && qrPremiumPosterBitmap != null) {
                                        QrExportUtils.shareQrCode(context, qrPremiumPosterBitmap!!, "poster")
                                    } else {
                                        StyledQr.generate(options).onSuccess { bitmap ->
                                            QrExportUtils.shareQrCode(context, bitmap, "qr_code")
                                        }
                                    }
                                    scope.launch {
                                        historyDao.insert(QrHistoryEntity(
                                            content = finalContent.encode(),
                                            type = contentType,
                                            optionsJson = Gson().toJson(options),
                                            templateId = selectedTemplate?.id
                                        ))
                                    }
                                },
                                modifier = Modifier.weight(1f).height(56.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White,
                                    contentColor = MaterialTheme.colorScheme.primary
                                ),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                            ) {
                                Icon(Icons.Default.Share, null)
                                Spacer(Modifier.width(8.dp))
                                Text("Share", fontWeight = FontWeight.Bold)
                            }
                            Button(
                                onClick = { 
                                    if (selectedTemplate != null && qrPremiumPosterBitmap != null) {
                                        QrExportUtils.saveToGallery(context, qrPremiumPosterBitmap!!, "poster")
                                    } else {
                                        StyledQr.generate(options).onSuccess { bitmap ->
                                            QrExportUtils.saveToGallery(context, bitmap, "qr_code")
                                        }
                                    }
                                    scope.launch {
                                        historyDao.insert(QrHistoryEntity(
                                            content = finalContent.encode(),
                                            type = contentType,
                                            optionsJson = Gson().toJson(options),
                                            templateId = selectedTemplate?.id
                                        ))
                                    }
                                },
                                modifier = Modifier.weight(1.5f).height(56.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF3B76F6),
                                    contentColor = Color.White
                                )
                            ) {
                                Icon(Icons.Default.FileDownload, null)
                                Spacer(Modifier.width(8.dp))
                                Text("Save", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    ) { padding ->
        Column(modifier = modifier.fillMaxSize().padding(padding)) {
            if (currentScreen == "SCAN") {
                ScannerScreen(
                    onClose = { currentScreen = "CONTENT" },
                    onResult = { result ->
                        qrContent = result
                        contentType = if (result.startsWith("http")) "URL" else "Text"
                        currentScreen = "CONTENT"
                        
                        // Save to history as a scanned item
                        scope.launch {
                            historyDao.insert(QrHistoryEntity(
                                content = result,
                                type = if (result.startsWith("http")) "URL" else "Text",
                                optionsJson = Gson().toJson(StyledQrOptions(
                                    content = if (result.startsWith("http")) UrlContent(result) else TextContent(result)
                                )),
                                isScanned = true
                            ))
                        }
                    }
                )
            } else if (currentScreen == "HISTORY") {
                HistoryScreen(
                    historyList = historyList,
                    onItemClick = { entity ->
                        val restoredOptions = Gson().fromJson(entity.optionsJson, StyledQrOptions::class.java)
                        // This is a simplified restoration, in a real app we'd map all states
                        qrContent = entity.content
                        contentType = entity.type
                        selectedTemplate = restoredOptions.templateId?.let { id -> QrTemplate.ALL_TEMPLATES.find { it.id == id } }
                        currentScreen = "CONTENT"
                    },
                    onDelete = { entity -> scope.launch { historyDao.delete(entity) } },
                    onToggleFavorite = { entity -> scope.launch { historyDao.update(entity.copy(isFavorite = !entity.isFavorite)) } }
                )
            } else if (currentScreen == "CONTENT") {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    ContentTabRevamp(
                        contentType, { contentType = it }, qrContent, { qrContent = it },
                        wifiSsid, { wifiSsid = it }, wifiPassword, { wifiPassword = it }, wifiSecurity, { wifiSecurity = it }, wifiHidden, { wifiHidden = it },
                        socialUsername, { socialUsername = it }, socialMessage, { socialMessage = it },
                        phoneNumber, { phoneNumber = it }, emailAddress, { emailAddress = it }, emailSubject, { emailSubject = it }, emailBody, { emailBody = it },
                        firstName, { firstName = it }, lastName, { lastName = it }, organization, { organization = it }, jobTitle, { jobTitle = it },
                        workPhone, { workPhone = it }, mobilePhone, { mobilePhone = it }, contactEmail, { contactEmail = it }, contactUrl, { contactUrl = it },
                        street, { street = it }, city, { city = it }, zip, { zip = it }, state, { state = it }, country, { country = it }, contactNote, { contactNote = it },
                        latitude, { latitude = it }, longitude, { longitude = it }, eventTitle, { eventTitle = it }, eventStart, { eventStart = it }, eventEnd, { eventEnd = it },
                        cryptoAddress, { cryptoAddress = it }, cryptoAmount, { cryptoAmount = it },
                        upiAddress, { upiAddress = it }, upiName, { upiName = it }, upiAmount, { upiAmount = it }, upiNote, { upiNote = it }
                    )
                    Spacer(Modifier.height(80.dp))
                }
            } else {
                // Modern UI Refresh
                Box(modifier = Modifier.fillMaxSize().background(Color(0xFFF0F4FF))) {
                    // Subtle background decoration
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawCircle(
                            color = Color(0xFFE0E7FF),
                            radius = 400f,
                            center = androidx.compose.ui.geometry.Offset(size.width, 0f)
                        )
                    }

                    Column(modifier = Modifier.fillMaxSize()) {
                        // High-end Preview Area
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.85f),
                            contentAlignment = Alignment.Center
                        ) {
                            Surface(
                                modifier = Modifier
                                    .aspectRatio(selectedTemplate?.aspectRatio ?: 1f)
                                    .fillMaxWidth(if (selectedTemplate != null && selectedTemplate!!.aspectRatio > 1f) 0.92f else 0.82f)
                                    .shadow(
                                        elevation = 24.dp,
                                        shape = RoundedCornerShape(if (selectedTemplate != null) 24.dp else 36.dp),
                                        clip = false
                                    ),
                                shape = RoundedCornerShape(if (selectedTemplate != null) 24.dp else 36.dp),
                                color = Color.White,
                                border = BorderStroke(2.dp, Color.White)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(if (selectedTemplate != null) 0.dp else 24.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (selectedTemplate != null && qrPremiumPosterBitmap != null) {
                                        Image(
                                            bitmap = qrPremiumPosterBitmap!!.asImageBitmap(),
                                            contentDescription = "Poster Preview",
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    } else {
                                        StyledQrCode(options = options, modifier = Modifier.fillMaxSize())
                                    }
                                }
                            }
                        }

                        // Customization Surface - Elevated and Modern
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1.15f),
                            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                            color = Color.White,
                            shadowElevation = 20.dp
                        ) {
                            Column {
                                // Modern Tab Bar
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 16.dp, start = 24.dp, end = 24.dp),
                                    shape = RoundedCornerShape(20.dp),
                                    color = Color(0xFFF5F7FF)
                                ) {
                                    ScrollableTabRow(
                                        selectedTabIndex = selectedTab,
                                        edgePadding = 8.dp,
                                        containerColor = Color.Transparent,
                                        divider = {},
                                        indicator = {} // We'll handle selection inside TabItemRevamp
                                    ) {
                                        listOf(
                                            Triple("Style", Icons.Default.AutoAwesome, 0),
                                            Triple("Colors", Icons.Default.Palette, 1),
                                            Triple("Logo", Icons.Default.Image, 2),
                                            Triple("Templates", Icons.Default.Dashboard, 3)
                                        ).forEach { (label, icon, index) ->
                                            TabItemRevamp(label, icon, selectedTab == index) { selectedTab = index }
                                        }
                                    }
                                }

                                Box(modifier = Modifier.fillMaxSize()) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .verticalScroll(rememberScrollState())
                                            .padding(horizontal = 24.dp, vertical = 24.dp),
                                        verticalArrangement = Arrangement.spacedBy(24.dp)
                                    ) {
                                        when (selectedTab) {
                                            0 -> StyleTabRevamp(
                                                dotShape, { dotShape = it },
                                                eyeFrameShape, { eyeFrameShape = it },
                                                eyeBallShape, { eyeBallShape = it },
                                                eyeFrameColor, { eyeFrameColor = it },
                                                eyeBallColor, { eyeBallColor = it },
                                                frameStyle, { frameStyle = it },
                                                frameLabel, { frameLabel = it },
                                                frameColor, { frameColor = it },
                                                frameGradientStyle, { frameGradientStyle = it },
                                                frameFont, { frameFont = it },
                                                errorCorrectionLevel, { errorCorrectionLevel = it }
                                            )
                                            1 -> ColorsTabRevamp(colorScheme, { colorScheme = it }, bgColor, { bgColor = it })
                                            2 -> LogoTabRevamp(
                                                show = showLogo, onShow = { showLogo = it },
                                                logoName = selectedLogoName, onLogoNameChange = { selectedLogoName = it },
                                                size = logoSize, onSize = { logoSize = it },
                                                drawBg = logoDrawBackground, onDrawBgChange = { logoDrawBackground = it },
                                                shape = logoShape, onShapeChange = { logoShape = it }
                                            )
                                            3 -> TemplateTabRevamp(
                                                selectedTemplate = selectedTemplate,
                                                onTemplateSelected = { selectedTemplate = it }
                                            )
                                        }
                                        Spacer(Modifier.height(100.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TabItemRevamp(label: String, icon: ImageVector, selected: Boolean, onClick: () -> Unit) {
    val bgColor by animateColorAsState(if (selected) Color.White else Color.Transparent)
    val contentColor by animateColorAsState(if (selected) MaterialTheme.colorScheme.primary else Color.Gray)
    val iconScale by animateFloatAsState(if (selected) 1.15f else 1.0f)
    
    Box(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 4.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                icon, 
                null, 
                modifier = Modifier.size(20.dp).graphicsLayer(scaleX = iconScale, scaleY = iconScale),
                tint = contentColor
            )
            if (selected) {
                Spacer(Modifier.width(8.dp))
                Text(
                    text = label,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = contentColor
                )
            }
        }
    }
}

@Composable
fun SectionHeader(title: String, icon: ImageVector) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
    ) {
        Surface(
            modifier = Modifier.size(36.dp),
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
        ) {
            Icon(
                icon, null, 
                modifier = Modifier.padding(8.dp).size(20.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(Modifier.width(12.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF1A1C1E)
        )
    }
}

@Composable
fun ContentTabRevamp(
    type: String, onTypeChange: (String) -> Unit, content: String, onContentChange: (String) -> Unit,
    wifiSsid: String, onSsidChange: (String) -> Unit, wifiPass: String, onPassChange: (String) -> Unit, wifiSec: String, onSecChange: (String) -> Unit, wifiHidden: Boolean, onHiddenChange: (Boolean) -> Unit,
    socialUser: String, onSocialUserChange: (String) -> Unit, socialMsg: String, onSocialMsgChange: (String) -> Unit,
    phone: String, onPhoneChange: (String) -> Unit, email: String, onEmailChange: (String) -> Unit, subject: String, onSubjectChange: (String) -> Unit, body: String, onBodyChange: (String) -> Unit,
    fName: String, onFNameChange: (String) -> Unit, lName: String, onLNameChange: (String) -> Unit, org: String, onOrgChange: (String) -> Unit, job: String, onJobChange: (String) -> Unit,
    wPhone: String, onWPhoneChange: (String) -> Unit, mPhone: String, onMPhoneChange: (String) -> Unit, cEmail: String, onCEmailChange: (String) -> Unit, cUrl: String, onCUrlChange: (String) -> Unit,
    street: String, onStreetChange: (String) -> Unit, city: String, onCityChange: (String) -> Unit, zip: String, onZipChange: (String) -> Unit, state: String, onStateChange: (String) -> Unit, country: String, onCountryChange: (String) -> Unit, note: String, onNoteChange: (String) -> Unit,
    lat: String, onLatChange: (String) -> Unit, lon: String, onLonChange: (String) -> Unit, eTitle: String, onETitleChange: (String) -> Unit, eStart: String, onEStartChange: (String) -> Unit, eEnd: String, onEEndChange: (String) -> Unit,
    cAddr: String, onCAddrChange: (String) -> Unit, cAmt: String, onCAmtChange: (String) -> Unit,
    upiAddr: String, onUpiAddrChange: (String) -> Unit, upiName: String, onUpiNameChange: (String) -> Unit, upiAmt: String, onUpiAmtChange: (String) -> Unit, upiNote: String, onUpiNoteChange: (String) -> Unit
) {
    val categories = listOf(
        Triple("Standard", Icons.Default.GridView, listOf( "URL", "Text", "Wi-Fi", "Email", "Phone", "SMS", "Map", "Event","Bitcoin", "Ethereum", "Solana", "UPI")),
        Triple("Social", Icons.Default.Share, listOf("X (Twitter)", "TikTok", "LinkedIn", "Discord", "Twitch", "WhatsApp", "Telegram", "Instagram", "Facebook", "YouTube")),
        Triple("Contacts", Icons.Default.Person, listOf("vCard", "MeCard"))
    )

    Column(verticalArrangement = Arrangement.spacedBy(32.dp)) {
        Column {
            Text("Add your content", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text("Choose the type of content you want your QR code to open.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        categories.forEach { (cat, icon, types) ->
            Column {
                SectionHeader(cat, icon)
                Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    types.forEach { t ->
                        ContentItemCard(
                            label = t,
                            icon = getContentIcon(t),
                            selected = type == t,
                            onClick = { onTypeChange(t) }
                        )
                    }
                }
            }
        }

        HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outlineVariant)

        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            when (type) {
                "URL", "YouTube", "Play Store", "App Store" -> {
                    SectionHeader("Link Details", Icons.Default.Link)
                    RevampTextField(content, onContentChange, "URL / Link", Icons.Default.Link)
                    
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                    ) {
                        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Info, null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(Modifier.width(12.dp))
                            Text(
                                "Make sure your link is correct. You won't be able to edit it after this step.",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                "Text" -> {
                    SectionHeader("Message", Icons.Default.Notes)
                    RevampTextField(content, onContentChange, "Your Message", Icons.Default.Notes, minLines = 4)
                }
                "Wi-Fi" -> {
                    SectionHeader("Network Info", Icons.Default.Wifi)
                    RevampTextField(wifiSsid, onSsidChange, "SSID (Name)", Icons.Default.Wifi)
                    RevampTextField(wifiPass, onPassChange, "Password", Icons.Default.Lock)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        listOf("WPA", "WEP", "None").forEach { s ->
                            InputChip(selected = wifiSec == s, onClick = { onSecChange(s) }, label = { Text(s) })
                        }
                        Spacer(Modifier.weight(1f))
                        Text("Hidden", fontSize = 12.sp)
                        Switch(wifiHidden, onHiddenChange, modifier = Modifier.customScale(0.8f))
                    }
                }
                "vCard", "MeCard" -> {
                    SectionHeader("Contact Info", Icons.Default.AccountCircle)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Box(Modifier.weight(1f)) { RevampTextField(fName, onFNameChange, "First Name") }
                        Box(Modifier.weight(1f)) { RevampTextField(lName, onLNameChange, "Last Name") }
                    }
                    RevampTextField(mPhone, onMPhoneChange, "Mobile", Icons.Default.PhoneAndroid)
                    RevampTextField(cEmail, onCEmailChange, "Email", Icons.Default.Email)
                    RevampTextField(cUrl, onCUrlChange, "Website", Icons.Default.Language)
                    if (type == "vCard") {
                        RevampTextField(org, onOrgChange, "Company", Icons.Default.Business)
                        RevampTextField(job, onJobChange, "Job Title", Icons.Default.Work)
                    }
                    SectionHeader("Address", Icons.Default.Map)
                    RevampTextField(street, onStreetChange, "Street")
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Box(Modifier.weight(1f)) { RevampTextField(city, onCityChange, "City") }
                        Box(Modifier.weight(1f)) { RevampTextField(zip, onZipChange, "Zip Code") }
                    }
                }
                "WhatsApp", "SMS", "Phone" -> {
                    SectionHeader("Phone Details", Icons.Default.Phone)
                    RevampTextField(phone, onPhoneChange, "Phone Number", Icons.Default.Phone)
                    if (type != "Phone") RevampTextField(socialMsg, onSocialMsgChange, "Initial Message", Icons.Default.Chat)
                }
                "Telegram", "Instagram", "Facebook", "X (Twitter)", "TikTok", "LinkedIn", "Twitch" -> {
                    SectionHeader("Profile", Icons.Default.Person)
                    RevampTextField(socialUser, onSocialUserChange, "Username", Icons.Default.AlternateEmail)
                }
                "Discord" -> {
                    SectionHeader("Server Invite", Icons.Default.Group)
                    RevampTextField(socialUser, onSocialUserChange, "Invite Code", Icons.Default.Link)
                }
                "Email" -> {
                    SectionHeader("Email Message", Icons.Default.Email)
                    RevampTextField(email, onEmailChange, "Recipient", Icons.Default.Email)
                    RevampTextField(subject, onSubjectChange, "Subject")
                    RevampTextField(body, onBodyChange, "Message Body", minLines = 3)
                }
                "Map" -> {
                    SectionHeader("Coordinates", Icons.Default.LocationOn)
                    RevampTextField(lat, onLatChange, "Latitude")
                    RevampTextField(lon, onLonChange, "Longitude")
                }
                "Event" -> {
                    SectionHeader("Event Details", Icons.Default.Event)
                    RevampTextField(eTitle, onETitleChange, "Title")
                    RevampTextField(eStart, onEStartChange, "Start (YYYYMMDDTHHMMSSZ)")
                    RevampTextField(eEnd, onEEndChange, "End (YYYYMMDDTHHMMSSZ)")
                }
                "Bitcoin", "Ethereum", "Solana" -> {
                    SectionHeader("Wallet", Icons.Default.CurrencyBitcoin)
                    RevampTextField(cAddr, onCAddrChange, "$type Address")
                    RevampTextField(cAmt, onCAmtChange, "Amount")
                }
                "UPI" -> {
                    SectionHeader("Payment Details", Icons.Default.Payments)
                    RevampTextField(upiAddr, onUpiAddrChange, "UPI ID / VPA", Icons.Default.AccountBalanceWallet)
                    RevampTextField(upiName, onUpiNameChange, "Payee Name", Icons.Default.Person)
                    RevampTextField(upiAmt, onUpiAmtChange, "Amount (Optional)", Icons.Default.AttachMoney)
                    RevampTextField(upiNote, onUpiNoteChange, "Note (Optional)", Icons.Default.Notes)
                }
            }
        }
    }
}

@Composable
fun RevampTextField(value: String, onValueChange: (String) -> Unit, label: String, icon: ImageVector? = null, minLines: Int = 1) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(label, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
        OutlinedTextField(
            value = value, onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            leadingIcon = icon?.let { { Icon(it, null, tint = MaterialTheme.colorScheme.primary) } },
            trailingIcon = if (value.isNotEmpty()) { { Icon(Icons.Default.Check, null, tint = Color(0xFF4CAF50)) } } else null,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            minLines = minLines
        )
    }
}

@Composable
fun ContentItemCard(label: String, icon: Any, selected: Boolean, onClick: () -> Unit) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier.size(width = 80.dp, height = 100.dp)
            .border(
                width = if (selected) 2.dp else 0.dp,
                color = if (selected) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.size(40.dp),
                contentAlignment = Alignment.Center
            ) {
                when (icon) {
                    is ImageVector -> Icon(icon, null, tint = if (selected) MaterialTheme.colorScheme.primary else Color.Unspecified, modifier = Modifier.size(32.dp))
                    is Int -> Image(painter = painterResource(icon), null, modifier = Modifier.size(32.dp))
                }
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}

@Composable
fun getContentIcon(type: String): Any {
    return when (type) {
        "Bitcoin" -> R.drawable.bitcoin
        "Ethereum" -> R.drawable.ethereum
        "Solana" -> R.drawable.solana
        "UPI" -> R.drawable.upi_icon
        "URL" -> Icons.Default.Link
        "Text" -> Icons.Default.Notes
        "Wi-Fi" -> R.drawable.wifi
        "Email" -> Icons.Default.Email
        "Phone" -> Icons.Default.Phone
        "SMS" -> Icons.Default.Chat
        "Map" -> Icons.Default.LocationOn
        "Event" -> Icons.Default.Event
        "WhatsApp" -> R.drawable.whatsapp
        "Telegram" -> R.drawable.telegram
        "Instagram" -> R.drawable.instagram
        "Facebook" -> Icons.Default.Facebook
        "YouTube" -> R.drawable.youtube
        "X (Twitter)" -> R.drawable.x
        "TikTok" -> R.drawable.tiktok
        "LinkedIn" -> R.drawable.linkedin
        "Discord" -> R.drawable.discord
        "Twitch" -> R.drawable.twitch
        "vCard" -> Icons.Default.AccountBox
        "MeCard" -> Icons.Default.AccountCircle
        else -> Icons.Default.Link
    }
}

@Composable
fun StyleTabRevamp(
    dot: DotShape, onDot: (DotShape) -> Unit,
    eFrame: EyeShape, onEFrame: (EyeShape) -> Unit,
    eBall: EyeBallShape, onEBall: (EyeBallShape) -> Unit,
    eFColor: Color?, onEFColor: (Color?) -> Unit,
    eBColor: Color?, onEBColor: (Color?) -> Unit,
    fStyle: FrameStyle, onFStyle: (FrameStyle) -> Unit,
    fLabel: String, onFLabel: (String) -> Unit,
    fColor: Color, onFColor: (Color) -> Unit,
    fGradient: GradientStyle, onFGradient: (GradientStyle) -> Unit,
    fFont: String, onFFont: (String) -> Unit,
    errorCorrection: ErrorCorrectionLevel, onErrorCorrection: (ErrorCorrectionLevel) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(28.dp)) {
        Column {
            SectionHeader("Error Correction", Icons.Default.Security)
            Text("Higher levels allow the QR to be readable even if damaged or covered by a logo.", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(bottom = 8.dp))
            Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                ErrorCorrectionLevel.entries.forEach { level ->
                    val description = when(level) {
                        ErrorCorrectionLevel.LOW -> "7%"
                        ErrorCorrectionLevel.MEDIUM -> "15%"
                        ErrorCorrectionLevel.QUARTILE -> "25%"
                        ErrorCorrectionLevel.HIGH -> "30%"
                    }
                    ShapeSelectionItem("${level.name} ($description)", errorCorrection == level) { onErrorCorrection(level) }
                }
            }
        }

        Column {
            SectionHeader("Frame Style", Icons.Default.CropFree)
            Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                FrameStyle.entries.forEach { s ->
                    ShapeSelectionItem(s.name, fStyle == s) { onFStyle(s) }
                }
            }
            if (fStyle != FrameStyle.NONE) {
                Spacer(Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Box(Modifier.weight(1f)) { RevampTextField(fLabel, onFLabel, "Frame Label", Icons.Default.Label) }
                    // Simple color trigger
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(fColor)
                            .border(2.dp, MaterialTheme.colorScheme.outline, CircleShape)
                            .clickable { 
                                // In a full app, this would open a real color picker
                                // For now, let's toggle a few professional colors
                                val colors = listOf(Color.Black, Color(0xFF1976D2), Color(0xFFD32F2F), Color(0xFF388E3C))
                                val nextIndex = (colors.indexOf(fColor) + 1) % colors.size
                                onFColor(colors[nextIndex])
                            }
                    )
                }
                
                Text("Frame Gradient", style = MaterialTheme.typography.labelLarge)
                Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    val gradients = listOf(
                        "None" to GradientStyle.None,
                        "Blue" to GradientStyle.Linear(android.graphics.Color.BLUE, android.graphics.Color.CYAN),
                        "Red" to GradientStyle.Radial(android.graphics.Color.RED, android.graphics.Color.BLACK),
                        "Rainbow" to GradientStyle.Sweep(intArrayOf(android.graphics.Color.RED, android.graphics.Color.YELLOW, android.graphics.Color.GREEN, android.graphics.Color.BLUE, android.graphics.Color.MAGENTA, android.graphics.Color.RED))
                    )
                    gradients.forEach { (name, style) ->
                        ElevatedFilterChip(
                            selected = fGradient == style,
                            onClick = { onFGradient(style) },
                            label = { Text(name) }
                        )
                    }
                }

                Text("Label Font", style = MaterialTheme.typography.labelLarge)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("SANS_SERIF", "SERIF", "MONOSPACE").forEach { font ->
                        ElevatedFilterChip(
                            selected = fFont == font,
                            onClick = { onFFont(font) },
                            label = { Text(font.lowercase().replaceFirstChar { it.uppercase() }) }
                        )
                    }
                }
            }
        }

        Column {
            SectionHeader("Body Shape", Icons.Default.GridView)
            Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                DotShape.entries.forEach { s ->
                    ShapeSelectionItem(s.name, dot == s) { onDot(s) }
                }
            }
        }

        Column {
            SectionHeader("Eye Frame", Icons.Default.CheckBoxOutlineBlank)
            Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                EyeShape.entries.forEach { s ->
                    ShapeSelectionItem(s.name, eFrame == s) { onEFrame(s) }
                }
            }
            Spacer(Modifier.height(12.dp))
            EyeColorPicker(eFColor, onEFColor, "Frame Color")
        }

        Column {
            SectionHeader("Eye Ball", Icons.Default.Lens)
            Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                EyeBallShape.entries.forEach { s ->
                    ShapeSelectionItem(s.name, eBall == s) { onEBall(s) }
                }
            }
            Spacer(Modifier.height(12.dp))
            EyeColorPicker(eBColor, onEBColor, "Ball Color")
        }
    }
}

@Composable
fun ShapeSelectionItem(label: String, selected: Boolean, onClick: () -> Unit) {
    val name = if (label.contains("(")) label else label.lowercase().replaceFirstChar { it.uppercase() }.replace("_", " ")
    Box(
        modifier = Modifier
            .widthIn(min = 100.dp)
            .height(48.dp)
            .padding(horizontal = 4.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (selected) MaterialTheme.colorScheme.primary 
                else MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name, 
            color = if (selected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant, 
            fontSize = 13.sp, 
            fontWeight = if (selected) FontWeight.ExtraBold else FontWeight.SemiBold, 
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun EyeColorPicker(selectedColor: Color?, onColorChange: (Color?) -> Unit, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically, 
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(label, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.width(80.dp))
        
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Default/Null color option
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray.copy(alpha = 0.3f))
                    .border(if (selectedColor == null) 3.dp else 1.dp, if (selectedColor == null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline, CircleShape)
                    .clickable { onColorChange(null) },
                contentAlignment = Alignment.Center
            ) {
                if (selectedColor == null) Icon(Icons.Default.Check, null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.primary)
            }

            listOf(Color.Black, Color(0xFFD32F2F), Color(0xFF1976D2), Color(0xFF388E3C), Color(0xFFFBC02D), Color(0xFF7B1FA2)).forEach { color ->
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(color)
                        .border(if (selectedColor == color) 3.dp else 0.dp, MaterialTheme.colorScheme.primary, CircleShape)
                        .shadow(if (selectedColor == color) 4.dp else 0.dp, CircleShape)
                        .clickable { onColorChange(color) },
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedColor == color) Icon(Icons.Default.Check, null, modifier = Modifier.size(16.dp), tint = Color.White)
                }
            }
        }
    }
}

@Composable
fun ColorsTabRevamp(scheme: String, onScheme: (String) -> Unit, bg: String, onBg: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(28.dp)) {
        Column {
            SectionHeader("Body Color / Gradient", Icons.Default.Gradient)
            val schemes = listOf("Black" to Color.Black, "Blue Linear" to Color.Blue, "Red Radial" to Color.Red, "Rainbow Sweep" to Color.Magenta)
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                schemes.forEach { (name, color) ->
                    ColorOptionItem(name, color, scheme == name) { onScheme(name) }
                }
            }
        }
        Column {
            SectionHeader("Background Color", Icons.Default.FormatColorFill)
            Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                listOf("White" to Color.White, "Light Gray" to Color.LightGray, "Yellow" to Color.Yellow, "Cyan" to Color.Cyan).forEach { (name, color) ->
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(color)
                            .border(if (bg == name) 3.dp else 1.dp, if (bg == name) MaterialTheme.colorScheme.primary else Color.LightGray, RoundedCornerShape(16.dp))
                            .clickable { onBg(name) },
                        contentAlignment = Alignment.Center
                    ) {
                        if (bg == name) Icon(Icons.Default.Check, null, tint = if (color == Color.White) Color.Black else Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun ColorOptionItem(name: String, color: Color, selected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(24.dp).clip(CircleShape).background(color))
            Spacer(Modifier.width(16.dp))
            Text(name, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.weight(1f))
            if (selected) Icon(Icons.Default.CheckCircle, null, tint = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun LogoTabRevamp(
    show: Boolean, onShow: (Boolean) -> Unit, 
    logoName: String, onLogoNameChange: (String) -> Unit,
    size: Float, onSize: (Float) -> Unit,
    drawBg: Boolean, onDrawBgChange: (Boolean) -> Unit,
    shape: LogoShape, onShapeChange: (LogoShape) -> Unit
) {
    val logos = listOf(
        "App Icon" to Icons.Default.QrCode2,
        "Apple" to Icons.Default.Smartphone,
        "Facebook" to Icons.Default.Facebook,
        "YouTube" to R.drawable.youtube,
        "Instagram" to R.drawable.instagram,
        "Reddit" to R.drawable.reddit,
        "WhatsApp" to R.drawable.whatsapp,
        "Wi-Fi" to R.drawable.wifi,
        "Vimeo" to R.drawable.vimeo,
        "Linkedin" to R.drawable.linkedin,
        "Call" to R.drawable.call,
        "Pinterest" to R.drawable.pinterest,
        "Calendar" to R.drawable.calendar,
        "Bitcoin" to R.drawable.bitcoin,
        "Discord" to R.drawable.discord,
        "Twitch" to R.drawable.twitch,
        "Ethereum" to R.drawable.ethereum,
        "Solana" to R.drawable.solana,
        "UPI" to R.drawable.upi_icon
    )

    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        SectionHeader("Center Logo", Icons.Default.Face)
        
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        ) {
            Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Enable Logo", fontWeight = FontWeight.Bold)
                    Spacer(Modifier.weight(1f))
                    Switch(show, onShow)
                }
                
                if (show) {
                    HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outlineVariant)
                    
                    Text("Select Logo", style = MaterialTheme.typography.labelLarge)
                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Upload Option
                        Box(
                            modifier = Modifier
                                .size(width = 80.dp, height = 72.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.White)
                                .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(16.dp))
                                .clickable { /* Handle Upload */ },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                                Icon(Icons.Default.FileUpload, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
                                Text("Upload", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        logos.forEach { (name, source) ->
                            Box(
                                modifier = Modifier
                                    .size(width = 80.dp, height = 72.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.White)
                                    .border(
                                        width = if (logoName == name) 2.dp else 1.dp,
                                        color = if (logoName == name) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .clickable { onLogoNameChange(name) }
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                if (logoName == name) {
                                    Box(modifier = Modifier.fillMaxSize().padding(2.dp), contentAlignment = Alignment.TopEnd) {
                                        Icon(Icons.Default.CheckCircle, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                                    }
                                }
                                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                                    when (source) {
                                        is ImageVector -> {
                                            Icon(
                                                imageVector = source,
                                                contentDescription = name,
                                                tint = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.size(28.dp)
                                            )
                                        }
                                        is Int -> {
                                            Image(
                                                painter = painterResource(id = source),
                                                contentDescription = name,
                                                modifier = Modifier.size(28.dp)
                                            )
                                        }
                                    }
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        text = name,
                                        fontSize = 10.sp,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Logo Background", fontWeight = FontWeight.Bold)
                        Spacer(Modifier.weight(1f))
                        Switch(drawBg, onDrawBgChange)
                    }

                    if (drawBg) {
                        Text("Background Shape", style = MaterialTheme.typography.labelLarge)
                        Row(
                            modifier = Modifier.horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            LogoShape.entries.forEach { s ->
                                ShapeSelectionItem(s.name, shape == s) { onShapeChange(s) }
                            }
                        }
                    }

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Logo Size", fontWeight = FontWeight.Bold)
                            Spacer(Modifier.weight(1f))
                            Text("${(size * 100).toInt()}%", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                        }
                        Slider(value = size, onValueChange = onSize, valueRange = 0.1f..0.25f)
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryScreen(
    historyList: List<QrHistoryEntity>,
    onItemClick: (QrHistoryEntity) -> Unit,
    onDelete: (QrHistoryEntity) -> Unit,
    onToggleFavorite: (QrHistoryEntity) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredList = remember(historyList, searchQuery) {
        if (searchQuery.isBlank()) historyList
        else historyList.filter { it.content.contains(searchQuery, ignoreCase = true) || it.type.contains(searchQuery, ignoreCase = true) }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Scan & Design History", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search history...") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )
        
        Spacer(Modifier.height(16.dp))
        
        if (filteredList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.History, null, modifier = Modifier.size(64.dp), tint = Color.LightGray)
                    Text(if (searchQuery.isEmpty()) "No history yet" else "No results found", color = Color.Gray)
                }
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(filteredList) { entity ->
                    HistoryItem(entity, onItemClick, onDelete, onToggleFavorite)
                }
            }
        }
    }
}

@Composable
fun HistoryItem(
    entity: QrHistoryEntity,
    onItemClick: (QrHistoryEntity) -> Unit,
    onDelete: (QrHistoryEntity) -> Unit,
    onToggleFavorite: (QrHistoryEntity) -> Unit
) {
    ElevatedCard(
        onClick = { onItemClick(entity) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(48.dp).background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(getContentIcon(entity.type) as ImageVector, null, tint = MaterialTheme.colorScheme.primary)
            }
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(entity.content, maxLines = 1, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f, fill = false))
                    if (entity.isScanned) {
                        Spacer(Modifier.width(8.dp))
                        Surface(
                            color = Color(0xFFE8F5E9),
                            contentColor = Color(0xFF2E7D32),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                "SCANNED",
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                Text(entity.type, style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = { onToggleFavorite(entity) }) {
                Icon(
                    if (entity.isFavorite) Icons.Default.Star else Icons.Default.StarOutline,
                    null,
                    tint = if (entity.isFavorite) Color(0xFFFBC02D) else Color.Gray
                )
            }
            IconButton(onClick = { onDelete(entity) }) {
                Icon(Icons.Default.DeleteOutline, null, tint = Color.Gray)
            }
        }
    }
}

@Composable
fun TemplateTabRevamp(
    selectedTemplate: QrTemplate?,
    onTemplateSelected: (QrTemplate?) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        SectionHeader("Poster Templates", Icons.Default.Dashboard)
        Text(
            "Select a pre-designed layout for your QR code poster.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // "None" option to go back to standard QR
            Surface(
                modifier = Modifier.fillMaxWidth().clickable { onTemplateSelected(null) },
                shape = RoundedCornerShape(16.dp),
                color = if (selectedTemplate == null) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, if (selectedTemplate == null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Clear, null, tint = if (selectedTemplate == null) MaterialTheme.colorScheme.primary else Color.Gray)
                    Spacer(Modifier.width(16.dp))
                    Text("Standard QR (No Template)", fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.weight(1f))
                    if (selectedTemplate == null) Icon(Icons.Default.CheckCircle, null, tint = MaterialTheme.colorScheme.primary)
                }
            }

            QrTemplate.ALL_TEMPLATES.forEach { template ->
                Surface(
                    modifier = Modifier.fillMaxWidth().clickable { onTemplateSelected(template) },
                    shape = RoundedCornerShape(16.dp),
                    color = if (selectedTemplate?.id == template.id) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                    border = BorderStroke(1.dp, if (selectedTemplate?.id == template.id) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant)
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    when (val bg = template.background) {
                                        is TemplateBackground.Solid -> Color(bg.color)
                                        is TemplateBackground.Gradient -> Color(bg.colors.first())
                                        else -> Color.LightGray
                                    }
                                )
                        )
                        Spacer(Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(template.name, fontWeight = FontWeight.Bold)
                            Text(template.description, style = MaterialTheme.typography.bodySmall, maxLines = 1)
                        }
                        if (selectedTemplate?.id == template.id) {
                            Icon(Icons.Default.CheckCircle, null, tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }
    }
}

fun Modifier.customScale(scale: Float) = this.then(
    Modifier.graphicsLayer(scaleX = scale, scaleY = scale)
)
