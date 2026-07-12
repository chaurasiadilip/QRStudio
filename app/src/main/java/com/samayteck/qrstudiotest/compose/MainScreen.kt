package com.samayteck.qrstudiotest.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.compose.ui.graphics.toArgb
import java.util.Locale
import androidx.compose.ui.res.painterResource
import com.samayteck.compose.StyledQrCode
import com.samayteck.core.content.basic.*
import com.samayteck.core.content.social.*
import com.samayteck.core.content.business.*
import com.samayteck.core.content.location.LocationContent
import com.samayteck.core.content.contact.VCardContent
import com.samayteck.core.content.contact.MeCardContent
import com.samayteck.core.content.event.CalendarContent
import com.samayteck.core.encoder.EncodingOptions
import com.samayteck.core.encoder.ErrorCorrectionLevel
import com.samayteck.core.model.*
import com.samayteck.qrstudiotest.R
import com.samayteck.qrstudiotest.util.QrExportUtils
import com.samayteck.renderer.api.StyledQr
import com.samayteck.svg.SvgLogoProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
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
    var frameFont by remember { mutableStateOf("SANS_SERIF") }
    var showLogo by remember { mutableStateOf(false) }
    var useSvgLogo by remember { mutableStateOf(false) }
    var logoSize by remember { mutableStateOf(0.2f) }
    var logoDrawBackground by remember { mutableStateOf(true) }
    var selectedLogoName by remember { mutableStateOf("App Icon") }
    var logoShape by remember { mutableStateOf(LogoShape.CIRCLE) }
    
    var colorScheme by remember { mutableStateOf("Black") }
    var bgColor by remember { mutableStateOf("White") }
    var errorCorrectionLevel by remember { mutableStateOf(ErrorCorrectionLevel.HIGH) }

    var selectedTab by remember { mutableIntStateOf(0) }

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
        encodingOptions = EncodingOptions(errorCorrectionLevel = errorCorrectionLevel)
    )

    Scaffold(
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
                navigationIcon = {
                    if (currentScreen == "DESIGN") {
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
                        Button(
                            onClick = { 
                                StyledQr.generate(options).onSuccess { bitmap ->
                                    QrExportUtils.shareQrCode(context, bitmap, "qr_code")
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
                                StyledQr.generate(options).onSuccess { bitmap ->
                                    QrExportUtils.saveToGallery(context, bitmap, "qr_code")
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
    ) { padding ->
        Column(modifier = modifier.fillMaxSize().padding(padding)) {
            if (currentScreen == "CONTENT") {
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
                // High-end Preview Area
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.9f)
                        .background(Color(0xFFF8F9FF)),
                    contentAlignment = Alignment.Center
                ) {
                    ElevatedCard(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .fillMaxWidth(0.85f)
                            .shadow(
                                elevation = 32.dp,
                                shape = RoundedCornerShape(40.dp),
                                ambientColor = Color.Black.copy(alpha = 0.1f),
                                spotColor = Color.Black.copy(alpha = 0.1f)
                            ),
                        shape = RoundedCornerShape(40.dp),
                        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
                    ) {
                        Box(modifier = Modifier.fillMaxSize().padding(32.dp), contentAlignment = Alignment.Center) {
                            StyledQrCode(options = options, modifier = Modifier.fillMaxSize())
                        }
                    }
                }

                // Customization Surface
                Surface(
                    modifier = Modifier.fillMaxWidth().weight(1.15f),
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                    tonalElevation = 3.dp,
                    shadowElevation = 16.dp,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
                ) {
                    Column {
                        ScrollableTabRow(
                            selectedTabIndex = selectedTab,
                            edgePadding = 24.dp,
                            containerColor = Color.Transparent,
                            divider = {},
                            indicator = { tabPositions ->
                                if (selectedTab < tabPositions.size) {
                                    Box(
                                        Modifier
                                            .tabIndicatorOffset(tabPositions[selectedTab])
                                            .height(4.dp)
                                            .padding(horizontal = 24.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.primary)
                                    )
                                }
                            }
                        ) {
                            TabItemRevamp("Style", Icons.Default.AutoAwesome, selectedTab == 0) { selectedTab = 0 }
                            TabItemRevamp("Colors", Icons.Default.Palette, selectedTab == 1) { selectedTab = 1 }
                            TabItemRevamp("Logo", Icons.Default.Image, selectedTab == 2) { selectedTab = 2 }
                        }

                        Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState())
                                    .padding(24.dp),
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
                                }
                                Spacer(Modifier.height(80.dp)) // Extra space for bottom bar
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
    Tab(
        selected = selected, onClick = onClick,
        text = { Text(label, fontSize = 13.sp, fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium) },
        icon = { Icon(icon, null, modifier = Modifier.size(22.dp)) },
        selectedContentColor = MaterialTheme.colorScheme.primary,
        unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
fun SectionHeader(title: String, icon: ImageVector) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
        Icon(icon, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(8.dp))
        Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
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
        Triple("Standard", Icons.Default.GridView, listOf("Bitcoin", "Ethereum", "Solana", "UPI", "URL", "Text", "Wi-Fi", "Email", "Phone", "SMS", "Map", "Event")),
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

fun Modifier.customScale(scale: Float) = this.then(
    Modifier.graphicsLayer(scaleX = scale, scaleY = scale)
)
