package com.gocash.wallet.ui.shared.picture

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.gocash.wallet.di.z17Singledi.SingletonInitializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

class Z17CoilDecoders(
    val needsHeader: (String) -> Boolean = { true },
) {
    companion object : SingletonInitializer<Z17CoilDecoders>("decoders")

    private var imageLoader: ImageLoader? = null

    fun getImageLoader(application: PlatformContext): ImageLoader {
        if (imageLoader != null) return imageLoader!!

        imageLoader = ImageLoader.Builder(application)
            .decoderDispatcher(Dispatchers.IO)
            .fetcherDispatcher(Dispatchers.IO)
            .memoryCachePolicy(CachePolicy.DISABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .crossfade(true)
            .components {
//                if (Build.VERSION.SDK_INT >= 28) {
//                    add(ImageDecoderDecoder.Factory())
//                } else {
//                    add(GifDecoder.Factory())
//                }
//                add(VideoFrameDecoder.Factory())
            }
            .logger(DebugLogger())
            .build()

        return imageLoader!!
    }


    fun clearCacheFor(url: String) {
        imageLoader?.memoryCache?.remove(MemoryCache.Key(url))
        imageLoader?.diskCache?.remove(url)

        //Log.d("COIL_CACHE", "Cache clear for $url")
    }

//    fun getInCache(
//        url: String,
//        customHeaders: Map<String, String>? = null,
//    ): Bitmap? {
//        val imageRequest = ImageRequest.Builder(context).apply {
//            this.data(url)
//            this.memoryCacheKey(url)
//            this.diskCacheKey(url)
//            this.memoryCachePolicy(CachePolicy.DISABLED)
//            this.diskCachePolicy(CachePolicy.ENABLED)
//            this.dispatcher(Dispatchers.IO)
//            this.interceptorDispatcher(Dispatchers.IO)
//            this.crossfade(true)
//            this.size(1920, 1080)
//            this.scale(scale = Scale.FILL)
//            this.listener(object : ImageRequest.Listener {
//                override fun onError(request: ImageRequest, result: ErrorResult) {
//                    Log.d("COIL", "request: ${request.data}, result: ${result.throwable}")
//                }
//            })
//            // Adding headers
//            if (needsHeader(url))
//                if (customHeaders != null)
//                    this.headers(Z17BasePictureHeaders.fromMapToHeaders(customHeaders)!!)
//                else try {
//                    if (Z17BasePictureHeaders.getInstance().thereAreHeaders()) {
//                        this.headers(Z17BasePictureHeaders.getInstance().getHeaders()!!)
//                    }
//                } catch (e: SinglediException) {
//                    e.printStackTrace()
//                }
//        }.build()
//
//        val loader = Z17CoilDecoders.getInstanceOrNull()?.imageLoader
//
//        return if (loader != null) {
//            try {
//                val result = (loader.executeBlocking(imageRequest) as SuccessResult).drawable
//                val bitmap = (result as BitmapDrawable).bitmap
//
//                bitmap
//            } catch (e: Exception) {
//                null
//            }
//        } else null
//    }
}