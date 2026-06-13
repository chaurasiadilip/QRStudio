package com.samayteck.core.cache

import android.graphics.Bitmap
import android.util.LruCache

internal class MemoryCache : BitmapCache {

    private val cache =
        LruCache<String, Bitmap>(
            (Runtime.getRuntime().maxMemory() / 1024 / 8).toInt()
        )

    override fun put(
        key: String,
        bitmap: Bitmap
    ) {
        cache.put(key, bitmap)
    }

    override fun get(
        key: String
    ): Bitmap? {
        return cache.get(key)
    }

    override fun clear() {
        cache.evictAll()
    }
}