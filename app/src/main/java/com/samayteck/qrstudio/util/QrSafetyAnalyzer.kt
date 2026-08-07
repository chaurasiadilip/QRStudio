package com.samayteck.qrstudio.util

import android.net.Uri
import java.util.Locale

enum class SafetyLevel {
    SAFE,
    SUSPICIOUS,
    DANGEROUS,
    UNKNOWN
}

data class SafetyReport(
    val level: SafetyLevel,
    val title: String,
    val description: String,
    val details: List<String> = emptyList()
)

object QrSafetyAnalyzer {

    private val DANGEROUS_PROTOCOLS = setOf("javascript", "data", "jar", "file")
    private val SUSPICIOUS_TLDS = setOf(".zip", ".mov", ".top", ".xyz", ".club")
    private val SHORTENER_DOMAINS = setOf("bit.ly", "tinyurl.com", "t.co", "goo.gl", "ow.ly", "buff.ly")

    fun analyze(content: String): SafetyReport {
        if (content.isBlank()) return SafetyReport(SafetyLevel.UNKNOWN, "Empty Content", "The QR code contains no data.")

        val uri = try {
            Uri.parse(content)
        } catch (e: Exception) {
            null
        }

        if (uri == null || uri.scheme == null) {
            return SafetyReport(SafetyLevel.SAFE, "Plain Text", "This code contains non-URL text and is likely safe to view.")
        }

        val scheme = uri.scheme?.lowercase(Locale.ROOT)
        val host = uri.host?.lowercase(Locale.ROOT) ?: ""

        val details = mutableListOf<String>()

        // Check for dangerous protocols
        if (DANGEROUS_PROTOCOLS.contains(scheme)) {
            return SafetyReport(
                SafetyLevel.DANGEROUS,
                "Malicious Protocol",
                "This QR code uses a protocol ($scheme) commonly used for cross-site scripting or file-based attacks.",
                listOf("Dangerous protocol detected: $scheme")
            )
        }

        // Check for IP address as host
        if (isIPAddress(host)) {
            details.add("Uses numeric IP address instead of a domain name.")
        }

        // Check for shortened URLs
        if (SHORTENER_DOMAINS.contains(host)) {
            details.add("Link is masked by a URL shortener ($host). The final destination is hidden.")
        }

        // Check for suspicious TLDs
        if (SUSPICIOUS_TLDS.any { host.endsWith(it) }) {
            details.add("Uses a Top-Level Domain ($host) frequently associated with spam or malware.")
        }

        // Typosquatting (Very basic check)
        if (host.contains("g00gle") || host.contains("faceb00k") || host.contains("paypa1")) {
            details.add("Possible typosquatting detected (fake brand name).")
        }

        return when {
            details.size >= 2 -> SafetyReport(SafetyLevel.DANGEROUS, "High Risk Detected", "Multiple security red flags were found in this link.", details)
            details.isNotEmpty() -> SafetyReport(SafetyLevel.SUSPICIOUS, "Potential Risk", "This link has some suspicious characteristics.", details)
            else -> SafetyReport(SafetyLevel.SAFE, "Secure Link", "No immediate threats were detected in this URL.", listOf("Verified standard protocol: $scheme"))
        }
    }

    private fun isIPAddress(host: String): Boolean {
        return host.matches(Regex("""^(\d{1,3}\.){3}\d{1,3}$"""))
    }
}
