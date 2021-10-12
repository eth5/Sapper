package net.it_dev.sapper.presenter.util

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntSizeAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun Modifier.moveModifier(startX:Int = 0, endX:Int = 0, startY:Int = 0, endY:Int = 0):Modifier {
    //bad idea :)
    var offsetX by remember { mutableStateOf(startX.dp) }
    val animateX by animateDpAsState(
        targetValue = offsetX,
        animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
    )
    var offsetY by remember { mutableStateOf(startX.dp) }
    val animateY by animateDpAsState(
        targetValue = offsetY,
        animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
    )
    if (startX != endX) LaunchedEffect(Unit) { offsetX = endX.dp }
    if (startY != endY) LaunchedEffect(Unit) { offsetY = endY.dp }

    offset(x = animateX, y = animateY)
    return this
}
@Composable
fun Modifier.moveModifie2r(startX:Int = 0, endX:Int = 0, startY:Int = 0, endY:Int = 0, ):Modifier {
    //bad idea :)

    var offsetX by remember { mutableStateOf(startX.dp) }
    val animateX by animateDpAsState(
        targetValue = offsetX,
        animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
    )
    var offsetY by remember { mutableStateOf(startX.dp) }
    val animateY by animateDpAsState(
        targetValue = offsetY,
        animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
    )
    if (startX != endX) LaunchedEffect(Unit) { offsetX = endX.dp }
    if (startY != endY) LaunchedEffect(Unit) { offsetY = endY.dp }

    offset(x = animateX, y = animateY)
    return this
}