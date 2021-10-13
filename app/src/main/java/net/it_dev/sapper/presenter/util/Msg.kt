package net.it_dev.sapper.presenter.util

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SnackbarSimpleMessage(textState: MutableState<String?>, scaffoldState: ScaffoldState, scope: CoroutineScope, duration: SnackbarDuration) {
    if (textState.value == null) return
    val text = textState.value!!
    LaunchedEffect(key1 = textState.value){
        textState.value = null
        scope.launch {
            scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
            scaffoldState.snackbarHostState.showSnackbar(text, duration = duration)

        }
    }
}