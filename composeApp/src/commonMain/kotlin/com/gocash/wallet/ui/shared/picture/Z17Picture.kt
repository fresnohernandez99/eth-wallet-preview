package com.gocash.wallet.ui.shared.picture

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import coil3.Uri
import gowallet.composeapp.generated.resources.Res
import gowallet.composeapp.generated.resources.placeholder
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun Z17BasePicture(
    modifier: Modifier,
    source: Any?,
    placeholder: Any = Icons.Outlined.Image,
    colorFilter: ColorFilter? = null,
    contentScale: ContentScale = ContentScale.Fit,
    description: String = "",
    filterQuality: FilterQuality = FilterQuality.High,
    customHeaders: Map<String, String>? = null
) {
    Box(modifier) {
        when {
            source is String && source.isNotBlank() && source.isNotEmpty() -> {
                PictureFromUrl(
                    modifier = Modifier.fillMaxSize(),
                    url = source,
                    placeholder = if (placeholder is Int || placeholder is Color) placeholder else Res.drawable.placeholder,
                    contentScale = contentScale,
                    description = description,
                    colorFilter = colorFilter,
                    filterQuality = filterQuality,
                    customHeaders = customHeaders
                )
            }

            source is Uri -> {
                PictureFromUri(
                    uri = source,
                    modifier = Modifier.fillMaxSize(),
                    description = description,
                    filterQuality = filterQuality,
                    contentScale = contentScale,
                )
            }

            source is DrawableResource -> {
                Image(
                    painter = painterResource(source),
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = description,
                    colorFilter = colorFilter,
                    contentScale = contentScale,
                    //filterQuality = filterQuality,
                )
            }

            source is ImageVector -> {
                Image(
                    imageVector = source,
                    contentDescription = description,
                    colorFilter = colorFilter,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = contentScale
                )
            }

            source is Color -> {
                Canvas(
                    modifier = Modifier.fillMaxSize()
                ) {
                    drawRect(
                        color = source,
                    )
                }
            }

            else -> {
                Z17BasePicture(
                    modifier = Modifier.fillMaxSize(),
                    source = placeholder,
                    colorFilter = colorFilter,
                    contentScale = contentScale,
                    description = description,
                    filterQuality = filterQuality
                )
            }
        }
    }
}