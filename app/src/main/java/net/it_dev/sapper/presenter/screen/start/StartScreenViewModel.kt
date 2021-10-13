package net.it_dev.sapper.presenter.screen.start

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartScreenViewModel @Inject constructor(

) : ViewModel() {
    val message = mutableStateOf<String?>(null)


    fun noImpl(){
        message.value = "Coming soon"
    }

}