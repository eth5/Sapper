package net.it_dev.sapper.presenter.util

import androidx.compose.foundation.Image
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProduceStateScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale

@Composable
fun AsyncImage(
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    producer: suspend ProduceStateScope<ImageBitmap?>.() -> Unit
){
    val imageBitmap by produceState<ImageBitmap?>(initialValue = null, producer = producer)
    if (imageBitmap==null){
        CircularProgressIndicator()
    }else{
        Image(imageBitmap!!, contentDescription, modifier, alignment, contentScale, alpha, colorFilter)
    }
}