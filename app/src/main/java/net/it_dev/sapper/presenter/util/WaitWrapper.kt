package net.it_dev.sapper.presenter.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import net.it_dev.sapper.util.Resource

@Composable
fun <T> WaitWrapper(state: State<Resource<T>>, content: @Composable (T) -> Unit) {
    val value by state
    when (value) {
        is Resource.Loading, is Resource.Undefine -> ProgressIndicatorInMaxBox()
        is Resource.Success -> content(value.data!!)
        is Resource.Error -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value.message!!,
                color = Color.Red,
            )
        }
    }
}