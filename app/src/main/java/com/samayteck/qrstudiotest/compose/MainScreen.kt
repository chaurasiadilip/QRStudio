package com.samayteck.qrstudiotest.compose

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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.samayteck.compose.StyledQrCode
import com.samayteck.core.content.basic.*
import com.samayteck.core.content.social.*
import com.samayteck.core.content.business.*
import com.samayteck.core.content.location.LocationContent
import com.samayteck.core.content.contact.VCardContent
import com.samayteck.core.model.*
import com.samayteck.qrstudiotest.R
import com.samayteck.svg.SvgLogoProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    var contentType by remember { mutableStateOf("URL") }
    var qrContent by remember { mutableStateOf("https://github.com") }
    
    // Additional states for complex types
    var wifiSsid by remember { mutableStateOf("") }
    var wifiPassword by remember { mutableStateOf("") }
    var wifiSecurity by remember { mutableStateOf("WPA") }
    
    var socialUsername by remember { mutableStateOf("") }
    var socialMessage by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var emailAddress by remember { mutableStateOf("") }
    var emailSubject by remember { mutableStateOf("") }
    var emailBody by remember { mutableStateOf("") }

    var dotShape by remember { mutableStateOf(DotShape.CIRCLE) }
    var eyeShape by remember { mutableStateOf(EyeShape.CIRCLE) }
    var frameStyle by remember { mutableStateOf(FrameStyle.NONE) }
    var frameLabel by remember { mutableStateOf("SCAN ME") }
    
    var showLogo by remember { mutableStateOf(false) }
    var useSvgLogo by remember { mutableStateOf(false) }
    var logoSize by remember { mutableStateOf(0.2f) }
    var colorScheme by remember { mutableStateOf("Black") }
    var bgColor by remember { mutableStateOf("White") }

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
        contentType, qrContent, wifiSsid, wifiPassword, wifiSecurity,
        socialUsername, socialMessage, phoneNumber, emailAddress, emailSubject, emailBody
    ) {
        when (contentType) {
            "URL" -> UrlContent(qrContent)
            "Text" -> TextContent(qrContent)
            "Wi-Fi" -> WifiContent(
                ssid = wifiSsid,
                password = wifiPassword,
                security = when (wifiSecurity) {
                    "WPA" -> WifiContent.Security.WPA
                    "WEP" -> WifiContent.Security.WEP
                    else -> WifiContent.Security.NONE
                }
            )
            "WhatsApp" -> WhatsAppContent(phoneNumber, socialMessage)
            "Telegram" -> TelegramContent(socialUsername)
            "Instagram" -> InstagramContent(socialUsername)
            "Facebook" -> FacebookContent(socialUsername)
            "YouTube" -> YouTubeContent(qrContent)
            "X (Twitter)" -> XContent(socialUsername)
            "TikTok" -> TikTokContent(socialUsername)
            "Email" -> EmailContent(emailAddress, emailSubject, emailBody)
            "Phone" -> PhoneContent(phoneNumber)
            "Play Store" -> PlayStoreContent(qrContent)
            "App Store" -> AppStoreContent(qrContent)
            else -> UrlContent(qrContent)
        }
    }

    val gradientStyle = remember(colorScheme) {
        when (colorScheme) {
            "Blue Linear" -> GradientStyle.Linear(
                startColor = android.graphics.Color.BLUE,
                endColor = android.graphics.Color.CYAN
            )
            "Red Radial" -> GradientStyle.Radial(
                centerColor = android.graphics.Color.RED,
                edgeColor = android.graphics.Color.BLACK
            )
            "Rainbow Sweep" -> GradientStyle.Sweep(
                colors = intArrayOf(
                    android.graphics.Color.RED,
                    android.graphics.Color.YELLOW,
                    android.graphics.Color.GREEN,
                    android.graphics.Color.BLUE,
                    android.graphics.Color.MAGENTA,
                    android.graphics.Color.RED
                )
            )
            else -> GradientStyle.None
        }
    }

    val logoBitmap = remember(showLogo, useSvgLogo) {
        if (!showLogo) return@remember null
        if (useSvgLogo) {
            SvgLogoProvider.fromAsset(context, "apple.svg", 512).getOrNull()
        } else {
            ContextCompat.getDrawable(context, R.mipmap.ic_launcher)?.toBitmap()
        }
    }

    val options = StyledQrOptions(
        content = finalContent,
        size = 1000,
        dotShape = dotShape,
        eyeShape = eyeShape,
        backgroundColor = backgroundColor,
        gradientStyle = gradientStyle,
        frameOptions = FrameOptions(
            frameStyle = frameStyle,
            label = if (frameStyle != FrameStyle.NONE) frameLabel else null
        ),
        logoOptions = LogoOptions(
            bitmap = logoBitmap,
            logoPercent = logoSize
        )
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("QR Studio Pro", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        bottomBar = {
            Surface(
                tonalElevation = 8.dp,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedButton(
                        onClick = { /* Share logic */ },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Share, null)
                        Spacer(Modifier.width(8.dp))
                        Text("Share")
                    }
                    Button(
                        onClick = { /* Download logic */ },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Download, null)
                        Spacer(Modifier.width(8.dp))
                        Text("Download")
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // QR Preview Section
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            ) {
                Box(
                    modifier = Modifier.padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    ElevatedCard(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .fillMaxWidth(0.85f),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
                    ) {
                        StyledQrCode(
                            options = options,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White)
                                .padding(16.dp)
                        )
                    }
                }
            }

            // Tabs for customization
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.2f)
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                ScrollableTabRow(
                    selectedTabIndex = selectedTab,
                    edgePadding = 16.dp,
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary,
                    divider = {}
                ) {
                    TabItem("Content", Icons.Default.Edit, selectedTab == 0) { selectedTab = 0 }
                    TabItem("Shapes", Icons.Default.GridView, selectedTab == 1) { selectedTab = 1 }
                    TabItem("Colors", Icons.Default.Palette, selectedTab == 2) { selectedTab = 2 }
                    TabItem("Logo & Frame", Icons.Default.AutoAwesome, selectedTab == 3) { selectedTab = 3 }
                }

                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    when (selectedTab) {
                        0 -> ContentTab(
                            contentType = contentType,
                            onTypeChange = { contentType = it },
                            content = qrContent,
                            onContentChange = { qrContent = it },
                            wifiSsid = wifiSsid,
                            onSsidChange = { wifiSsid = it },
                            wifiPass = wifiPassword,
                            onPassChange = { wifiPassword = it },
                            wifiSec = wifiSecurity,
                            onSecChange = { wifiSecurity = it },
                            socialUser = socialUsername,
                            onSocialUserChange = { socialUsername = it },
                            socialMsg = socialMessage,
                            onSocialMsgChange = { socialMessage = it },
                            phone = phoneNumber,
                            onPhoneChange = { phoneNumber = it },
                            email = emailAddress,
                            onEmailChange = { emailAddress = it },
                            subject = emailSubject,
                            onSubjectChange = { emailSubject = it },
                            body = emailBody,
                            onBodyChange = { emailBody = it }
                        )
                        1 -> ShapesTab(dotShape, { dotShape = it }, eyeShape, { eyeShape = it })
                        2 -> ColorsTab(colorScheme, { colorScheme = it }, bgColor, { bgColor = it })
                        3 -> LogoFrameTab(
                            showLogo, { showLogo = it },
                            useSvgLogo, { useSvgLogo = it },
                            logoSize, { logoSize = it },
                            frameStyle, { frameStyle = it },
                            frameLabel, { frameLabel = it }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TabItem(label: String, icon: ImageVector, selected: Boolean, onClick: () -> Unit) {
    Tab(
        selected = selected,
        onClick = onClick,
        text = { Text(label, style = MaterialTheme.typography.labelLarge) },
        icon = { Icon(icon, contentDescription = null) }
    )
}

@Composable
fun ContentTab(
    contentType: String, onTypeChange: (String) -> Unit,
    content: String, onContentChange: (String) -> Unit,
    wifiSsid: String, onSsidChange: (String) -> Unit,
    wifiPass: String, onPassChange: (String) -> Unit,
    wifiSec: String, onSecChange: (String) -> Unit,
    socialUser: String, onSocialUserChange: (String) -> Unit,
    socialMsg: String, onSocialMsgChange: (String) -> Unit,
    phone: String, onPhoneChange: (String) -> Unit,
    email: String, onEmailChange: (String) -> Unit,
    subject: String, onSubjectChange: (String) -> Unit,
    body: String, onBodyChange: (String) -> Unit
) {
    val categories = remember {
        listOf(
            "Basic" to listOf("URL", "Text", "Wi-Fi", "Email", "Phone", "SMS"),
            "Social" to listOf("WhatsApp", "Telegram", "Instagram", "Facebook", "YouTube", "X (Twitter)", "TikTok", "LinkedIn", "Snapchat", "Spotify", "Reddit"),
            "Business" to listOf("Play Store", "App Store", "Google Forms", "Google Doc", "Google Sheets", "Google Review", "Payment", "Paypal", "Etsy"),
            "Files" to listOf("PDF", "Image", "Video", "Audio", "PPTX", "Excel")
        )
    }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        categories.forEach { (category, types) ->
            Text(category, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                types.forEach { type ->
                    FilterChip(
                        selected = contentType == type,
                        onClick = { onTypeChange(type) },
                        label = { Text(type) },
                        leadingIcon = {
                            Icon(
                                when (type) {
                                    "URL" -> Icons.Default.Link
                                    "Text" -> Icons.Default.Notes
                                    "Wi-Fi" -> Icons.Default.Wifi
                                    "Email" -> Icons.Default.Email
                                    "Phone" -> Icons.Default.Phone
                                    "WhatsApp" -> Icons.Default.Chat
                                    "Play Store" -> Icons.Default.Shop
                                    "App Store" -> Icons.Default.Store
                                    "YouTube" -> Icons.Default.PlayCircle
                                    "Facebook" -> Icons.Default.Facebook
                                    "Instagram" -> Icons.Default.PhotoCamera
                                    "X (Twitter)" -> Icons.Default.Close
                                    "TikTok" -> Icons.Default.MusicNote
                                    "LinkedIn" -> Icons.Default.Work
                                    "PDF" -> Icons.Default.PictureAsPdf
                                    "Image" -> Icons.Default.Image
                                    "Video" -> Icons.Default.VideoLibrary
                                    else -> Icons.Default.Description
                                },
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    )
                }
            }
        }

        HorizontalDivider()

        Text("Edit $contentType", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

        when (contentType) {
            "URL", "YouTube", "Play Store", "App Store", "Google Forms", "Google Doc", "Google Sheets", "Google Review", "Payment", "Paypal", "Etsy" -> {
                OutlinedTextField(
                    value = content,
                    onValueChange = onContentChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("URL / Link") },
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = { Icon(Icons.Default.Link, null) }
                )
            }
            "Text" -> {
                OutlinedTextField(
                    value = content,
                    onValueChange = onContentChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Enter text here") },
                    shape = RoundedCornerShape(12.dp),
                    minLines = 3
                )
            }
            "Wi-Fi" -> {
                OutlinedTextField(
                    value = wifiSsid,
                    onValueChange = onSsidChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Network Name (SSID)") },
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = { Icon(Icons.Default.Wifi, null) }
                )
                OutlinedTextField(
                    value = wifiPass,
                    onValueChange = onPassChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Password") },
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = { Icon(Icons.Default.Lock, null) }
                )
                Text("Security Type", style = MaterialTheme.typography.labelLarge)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("WPA", "WEP", "None").forEach { sec ->
                        ElevatedFilterChip(selected = wifiSec == sec, onClick = { onSecChange(sec) }, label = { Text(sec) })
                    }
                }
            }
            "WhatsApp", "Phone", "SMS" -> {
                OutlinedTextField(
                    value = phone,
                    onValueChange = onPhoneChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Phone Number") },
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = { Icon(Icons.Default.Phone, null) }
                )
                if (contentType == "WhatsApp" || contentType == "SMS") {
                    OutlinedTextField(
                        value = socialMsg,
                        onValueChange = onSocialMsgChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Initial Message (Optional)") },
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }
            "Telegram", "Instagram", "Facebook", "X (Twitter)", "TikTok", "LinkedIn", "Snapchat", "Spotify", "Reddit" -> {
                OutlinedTextField(
                    value = socialUser,
                    onValueChange = onSocialUserChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Username or Profile ID") },
                    shape = RoundedCornerShape(12.dp),
                    prefix = { Text("@") }
                )
            }
            "Email" -> {
                OutlinedTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Email Address") },
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = { Icon(Icons.Default.Email, null) }
                )
                OutlinedTextField(
                    value = subject,
                    onValueChange = onSubjectChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Subject") },
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value = body,
                    onValueChange = onBodyChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Message Body") },
                    shape = RoundedCornerShape(12.dp),
                    minLines = 3
                )
            }
            "PDF", "Image", "Video", "Audio", "PPTX", "Excel" -> {
                OutlinedTextField(
                    value = content,
                    onValueChange = onContentChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("File URL (or upload placeholder)") },
                    shape = RoundedCornerShape(12.dp),
                    supportingText = { Text("In a full app, this would be a file picker") },
                    leadingIcon = { Icon(Icons.Default.AttachFile, null) }
                )
            }
        }
    }
}

@Composable
fun ShapesTab(
    dotShape: DotShape, onDotChange: (DotShape) -> Unit,
    eyeShape: EyeShape, onEyeChange: (EyeShape) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Dot Style", style = MaterialTheme.typography.titleMedium)
        DotSelector(selected = dotShape, onSelected = onDotChange)

        Text("Eye Style", style = MaterialTheme.typography.titleMedium)
        EyeSelector(selected = eyeShape, onSelected = onEyeChange)
    }
}

@Composable
fun ColorsTab(
    colorScheme: String, onColorSchemeChange: (String) -> Unit,
    bgColor: String, onBgColorChange: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Foreground Style", style = MaterialTheme.typography.titleMedium)
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(
                "Black" to Color.Black,
                "Blue Linear" to Color.Blue,
                "Red Radial" to Color.Red,
                "Rainbow Sweep" to Color.Magenta
            ).forEach { (name, color) ->
                InputChip(
                    selected = colorScheme == name,
                    onClick = { onColorSchemeChange(name) },
                    label = { Text(name) },
                    leadingIcon = {
                        Box(
                            Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(color)
                        )
                    }
                )
            }
        }

        Text("Background Color", style = MaterialTheme.typography.titleMedium)
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(
                "White" to Color.White,
                "Light Gray" to Color.LightGray,
                "Yellow" to Color.Yellow,
                "Cyan" to Color.Cyan
            ).forEach { (name, color) ->
                InputChip(
                    selected = bgColor == name,
                    onClick = { onBgColorChange(name) },
                    label = { Text(name) },
                    leadingIcon = {
                        Box(
                            Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(color)
                                .border(1.dp, Color.Gray, CircleShape)
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun LogoFrameTab(
    showLogo: Boolean, onShowLogoChange: (Boolean) -> Unit,
    useSvgLogo: Boolean, onUseSvgLogoChange: (Boolean) -> Unit,
    logoSize: Float, onLogoSizeChange: (Float) -> Unit,
    frameStyle: FrameStyle, onFrameStyleChange: (FrameStyle) -> Unit,
    frameLabel: String, onFrameLabelChange: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Frame Settings", style = MaterialTheme.typography.titleMedium)
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FrameStyle.entries.forEach { style ->
                FilterChip(
                    selected = frameStyle == style,
                    onClick = { onFrameStyleChange(style) },
                    label = { Text(style.name) }
                )
            }
        }

        if (frameStyle != FrameStyle.NONE) {
            OutlinedTextField(
                value = frameLabel,
                onValueChange = onFrameLabelChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Frame Label") },
                shape = RoundedCornerShape(12.dp)
            )
        }

        HorizontalDivider()

        Text("Logo Settings", style = MaterialTheme.typography.titleMedium)
        ListItem(
            headlineContent = { Text("Enable Logo") },
            trailingContent = { Switch(checked = showLogo, onCheckedChange = onShowLogoChange) }
        )

        if (showLogo) {
            ListItem(
                headlineContent = { Text("Use Apple SVG Logo") },
                supportingContent = { Text("apple.svg from assets") },
                trailingContent = { Switch(checked = useSvgLogo, onCheckedChange = onUseSvgLogoChange) }
            )
            Column {
                Text("Logo Size: ${(logoSize * 100).toInt()}%", style = MaterialTheme.typography.bodyMedium)
                Slider(value = logoSize, onValueChange = onLogoSizeChange, valueRange = 0.1f..0.25f)
            }
        }
    }
}
