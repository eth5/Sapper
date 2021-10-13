package net.it_dev.sapper.presenter.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState

@Composable
fun <T>EventState(state: MutableState<T?>, event: suspend (MutableState<T?>) -> Unit) {
    if (state.value != null) {
        LaunchedEffect(null) {
            event(state)
        }
    }
}