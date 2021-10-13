package net.it_dev.sapper.presenter.screen.game

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.*
import net.it_dev.sapper.sound.Fx
import net.it_dev.sapper.sound.ISoundPool

class Timer(private val sp:ISoundPool, private val scope: CoroutineScope) {

    private var timerJob: Job? = null
    private val _time = mutableStateOf(0)
    val time: State<Int> get() = _time

    fun start(initialValue:Int){
        stop()
        _time.value = initialValue
        timerJob = scope.launch {
            _time.value = 0
            while (isActive) {
                delay(1000)
                sp.play(Fx.PIC, 1f)//todo добавить регулятор громкости
                _time.value++
            }
        }
    }
    fun stop(){
        runBlocking { timerJob?.cancelAndJoin() }
    }
}