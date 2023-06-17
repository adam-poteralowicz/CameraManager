package com.apap.cameraManager.di

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.internal.cookieToString
import okhttp3.internal.delimiterOffset
import okhttp3.internal.platform.Platform
import okhttp3.internal.trimSubstring
import java.io.IOException
import java.net.CookieHandler
import java.util.*

/* Implementation taken from:
https://github.com/square/okhttp/blob/master/okhttp-urlconnection/src/main/kotlin/okhttp3/JavaNetCookieJar.kt */
class MyCookieJar(private val cookieHandler: CookieHandler) : CookieJar {
    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val cookieHeaders = try {
            // The RI passes all headers. We don't have 'em, so we don't pass 'em!
            cookieHandler.get(url.toUri(), emptyMap<String, List<String>>())
        } catch (e: IOException) {
            Platform.get().log("Loading cookies failed for " + url.resolve("/...")!!,
                Platform.WARN, e)
            return emptyList()
        }

        var cookies: MutableList<Cookie>? = null
        for ((key, value) in cookieHeaders) {
            if (("Cookie".equals(key, ignoreCase = true) || "Cookie2".equals(key, ignoreCase = true)) &&
                value.isNotEmpty()) {
                for (header in value) {
                    if (cookies == null) cookies = mutableListOf()
                    cookies.addAll(decodeHeaderAsJavaNetCookies(url, header))
                }
            }
        }

        return if (cookies != null) {
            Collections.unmodifiableList(cookies)
        } else {
            emptyList()
        }
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        val cookieStrings = mutableListOf<String>()
        for (cookie in cookies) {
            cookieStrings.add(cookieToString(cookie, true))
        }
        val multimap = mapOf("Set-Cookie" to cookieStrings)
        try {
            cookieHandler.put(url.toUri(), multimap)
        } catch (e: IOException) {
            Platform.get().log("Saving cookies failed for " + url.resolve("/...")!!,
                Platform.WARN, e)
        }
    }

    private fun decodeHeaderAsJavaNetCookies(url: HttpUrl, header: String): List<Cookie> {
        val result = mutableListOf<Cookie>()
        var pos = 0
        val limit = header.length
        var pairEnd: Int
        while (pos < limit) {
            pairEnd = header.delimiterOffset(";,", pos, limit)
            val equalsSign = header.delimiterOffset('=', pos, pairEnd)
            val name = header.trimSubstring(pos, equalsSign)
            if (name.startsWith("$")) {
                pos = pairEnd + 1
                continue
            }

            // We have either name=value or just a name.
            var value = if (equalsSign < pairEnd) {
                header.trimSubstring(equalsSign + 1, pairEnd)
            } else {
                ""
            }

            // If the value is "quoted", drop the quotes.
            if (value.startsWith("\"") && value.endsWith("\"")) {
                value = value.substring(1, value.length - 1)
            }

            result.add(Cookie.Builder()
                .name(name)
                .value(value)
                .domain(url.host)
                .build())
            pos = pairEnd + 1
        }
        return result
    }
}