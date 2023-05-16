package com.example.weather.utils

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import java.io.File

object CoilImageLoader {
    private const val CACHE_DIRECTORY_NAME = "coil_image_cache"
    private const val MEMORY_CACHE_SIZE_BYTES = 10 * 1024 * 1024 // 10MB
    private const val DISK_CACHE_SIZE_BYTES = 50 * 1024 * 1024L // 50MB

    fun create(context: Context): ImageLoader {
        val cacheDirectory = File(context.cacheDir, CACHE_DIRECTORY_NAME)
        val memoryCache = MemoryCache.Builder(context)
            .maxSizeBytes(MEMORY_CACHE_SIZE_BYTES)
            .build()
        val diskCache = DiskCache.Builder()
            .directory(cacheDirectory)
            .maxSizeBytes(DISK_CACHE_SIZE_BYTES)
            .build()

        return ImageLoader.Builder(context)
            .memoryCache(memoryCache)
            .diskCache(diskCache)
            .build()
    }
}
