package com.gocash.wallet.ui.shared.picture

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight

fun String.isAnUrl(): Boolean {
    val regexHttp =
        Regex("^https?:\\/\\/(?:www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b(?:[-a-zA-Z0-9()@:%_\\+.~#?&\\/=]*)$")
    val regexNoHttp =
        Regex("^[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b(?:[-a-zA-Z0-9()@:%_\\+.~#?&\\/=]*)$")

    return regexHttp.matches(this) || regexNoHttp.matches(this)
}

fun nameInitials(text: String): String {
    if (text.isEmpty())
        return "?"

    var result = ""

    var ascii = text

    val re = Regex("[^A-Za-z0-9 ]")
    ascii = re.replace(ascii, "")

    val list = ascii.split(" ")
    if (list.size > 1) {
        list.asSequence()
            .filterNot { it.isEmpty() }
            .map {

                return@map it.get(0).toString()

            }
            .forEach {
                result += it
            }

    } else {
        result = ascii
    }

    return if (result.length > 2) result.substring(0, 2)
        .uppercase() else result.uppercase()
}

@Composable
fun Z17PictureAvatar(
    modifier: Modifier = Modifier,
    source: Any?,
    placeholder: Any = Icons.Outlined.Person,
    colorFilter: ColorFilter? = null,
    description: String = "avatar image",
    filterQuality: FilterQuality = FilterQuality.High,
    size: Int? = null,
    canEdit: Boolean = false,
    customHeaders: Map<String, String>? = null,
    textPlaceholder: String = "",
    useTextPlaceholder: Boolean = false,
    defaultAvatarSize: Int = 256,
) {
    Box(
        modifier = modifier
            .aspectRatio(1F)
    ) {
        if ((source == null || (source is String && source.isBlank()) || (source is String && !source.isAnUrl())) && useTextPlaceholder)
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
                    .clip(CircleShape)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier
                        .clip(CircleShape),
                    text = nameInitials(textPlaceholder),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                    color = Color.White
                )
            }
//            Z17BasePicture(
//                modifier = Modifier
//                    .clip(CircleShape)
//                    .fillMaxSize(),
//                source = textPlaceholder.asBitmap(
//                    textColor = if (placeholder is Color) placeholder.toArgb()
//                        .toLong() else android.graphics.Color.parseColor(
//                        "#9c9c9c"
//                    ).toLong(),
//                    height = defaultAvatarSize,
//                    width = defaultAvatarSize,
//                    circle = true
//                ),
//                contentScale = ContentScale.Crop,
//                colorFilter = colorFilter,
//                filterQuality = filterQuality,
//                description = description,
//                blurHash = ""
//            )
        else
            Z17BasePicture(
                modifier = Modifier
                    .clip(CircleShape)
                    .fillMaxSize(),
                source = source,
                placeholder = placeholder,
                contentScale = ContentScale.Crop,
                colorFilter = colorFilter,
                filterQuality = filterQuality,
                description = description,
                customHeaders = customHeaders
            )
//
//        if (canEdit)
//            Z17BasePicture(
//                modifier = Modifier
//                    .clip(CircleShape)
//                    .fillMaxSize(),
//                source = Res.drawable.edit_profile_banner,
//                contentScale = ContentScale.Crop,
//                description = "edit profile banner"
//            )
    }
}