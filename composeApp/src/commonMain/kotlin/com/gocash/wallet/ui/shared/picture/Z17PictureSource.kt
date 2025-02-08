package com.gocash.wallet.ui.shared.picture

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import coil3.Uri
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.network.httpHeaders
import coil3.request.CachePolicy
import coil3.request.ErrorResult
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Scale
import com.gocash.wallet.di.z17Singledi.SinglediException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Composable
fun PictureFromUri(
    modifier: Modifier,
    uri: Uri,
    contentScale: ContentScale,
    description: String,
    filterQuality: FilterQuality
) {
    AsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(LocalPlatformContext.current).apply {
            this.data(uri)
            this.memoryCacheKey(uri.path)
            this.diskCacheKey(uri.path)
            this.memoryCachePolicy(CachePolicy.DISABLED)
            this.diskCachePolicy(CachePolicy.ENABLED)
            this.dispatcher(Dispatchers.IO)
            this.interceptorDispatcher(Dispatchers.IO)
            this.crossfade(true)
            this.size(1920, 1080)
            this.scale(scale = Scale.FILL)

            this.dispatcher(Dispatchers.IO)
            this.interceptorDispatcher(Dispatchers.IO)
        }.build(),
        filterQuality = filterQuality,
        contentDescription = description,
        contentScale = contentScale,
        imageLoader = Z17CoilDecoders.getInstance().getImageLoader(LocalPlatformContext.current)
    )
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun PictureFromUrl(
    modifier: Modifier,
    url: String,
    placeholder: Any,
    colorFilter: ColorFilter? = null,
    contentScale: ContentScale,
    description: String,
    filterQuality: FilterQuality,
    customHeaders: Map<String, String>? = null,
) {
    AsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(LocalPlatformContext.current).apply {
            this.data(url)
            this.memoryCacheKey(url)
            this.diskCacheKey(url)
            this.memoryCachePolicy(CachePolicy.DISABLED)
            this.diskCachePolicy(CachePolicy.ENABLED)
            this.dispatcher(Dispatchers.IO)
            this.interceptorDispatcher(Dispatchers.IO)
            this.crossfade(true)
            this.size(1920, 1080)
            this.scale(scale = Scale.FILL)
            this.listener(object : ImageRequest.Listener {
                override fun onError(request: ImageRequest, result: ErrorResult) {
                    println("COIL request: ${request.data}, result: ${result.throwable}")
                }
            })

            // Setting placeholder
            // TODO
//            if (placeholder is DrawableResource) {
//                this.placeholder(placeholder)
//                this.error(placeholder)
//            }
//
//            if (placeholder is Color) {
//                val shapeDrawable = ShapeDrawable(RectShape())
//                shapeDrawable.intrinsicWidth = 200
//                shapeDrawable.intrinsicHeight = 200
//                shapeDrawable.paint.color = placeholder.hashCode()
//
//                this.placeholder(shapeDrawable)
//                this.error(shapeDrawable)
//            }

            // Adding headers
            if (Z17CoilDecoders.getInstanceOrNull()?.needsHeader?.invoke(url) == true)
                if (customHeaders != null) {
                    println("https://s3.todus.cu/todus/profile/7340b66949a41f7c38a49a21ef62e829b122fba942226d7b4c160d35c860ab42 asnldjakjshdsa")
                    this.httpHeaders(Z17BasePictureHeaders.fromMapToHeaders(customHeaders)!!)
                }
                else try {
                    if (Z17BasePictureHeaders.getInstance().thereAreHeaders()) {
                        this.httpHeaders(Z17BasePictureHeaders.getInstance().getHeaders()!!)
                    }
                } catch (e: SinglediException) {
                    e.printStackTrace()
                }

        }.build(),
        filterQuality = filterQuality,
        contentDescription = description,
        contentScale = contentScale,
        colorFilter = colorFilter,
        imageLoader = Z17CoilDecoders.getInstance().getImageLoader(LocalPlatformContext.current)
    )
}