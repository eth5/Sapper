package net.it_dev.sapper.presenter.screen.game

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import net.it_dev.sapper.domain.Flags
import net.it_dev.sapper.domain.GameState
import net.it_dev.sapper.domain.ItemField
import net.it_dev.sapper.domain.ItemFieldState
import net.it_dev.sapper.presenter.bitmap.IBitmapFactory
import net.it_dev.sapper.util.Resource
import javax.inject.Inject

@HiltViewModel
class GameScreenViewModel @Inject constructor(
	val imageBitmapFactory: IBitmapFactory
) : ViewModel() {
	companion object {
		private const val TAG = "GameScreenViewModel"
	}
	var showDialog = mutableStateOf(false)

	private data class SessionData(val rows: Int, val columns: Int, val mines: Int)

	private val _field = mutableStateOf<Resource<MineField>>(Resource.Undefine())
	val mineField: State<Resource<MineField>> get() = _field
	private val internalField get() = _field.value.data!!

	val gameState = mutableStateOf(GameState.STOP)
	val boom = mutableStateOf(false)

	private var _flags: Flags = Flags()
	val flags:State<Int> get() = _flags.count
	private val _time = mutableStateOf(0)
	val time:State<Int> get() = _time

	private lateinit var sessionData:SessionData
	fun initial(rows: Int, columns: Int, mines: Int){
		if (this::sessionData.isInitialized) return
		sessionData = SessionData(rows, columns, mines)
		startNewSession()
	}

	private fun startNewSession() {
		_field.value = Resource.Loading()
		viewModelScope.launch(Dispatchers.Default) {
			val mineField = Resource.Success(generateField(sessionData.rows,sessionData.columns,sessionData.mines))
			(Dispatchers.Main) {
				_field.value = mineField
				_flags.setCount(sessionData.mines)
				startTimer()
				gameState.value = GameState.IN_PLAY
			}
		}
	}

	private fun generateField(rows: Int, columns: Int, mines: Int): MineField {
		val field = MineField(rows, columns)
		field.setMinesToField(mines = mines, mineField = field)
		return field
	}


	private var timerJob: Job? = null
	private fun startTimer() {
		runBlocking { timerJob?.cancelAndJoin() }
		timerJob = viewModelScope.launch {
			_time.value = 0
			while (isActive) {
				delay(1000)
				_time.value++
			}
		}
	}


	fun openAll() {
		var boom = false
		internalField.field.forEach {
			it.forEach {
				internalField.open(it)
				if (it.value.itemFieldState == ItemFieldState.Boom) boom = true
			}
		}
		if (boom){
			this.boom.value = true
			stopGame(GameState.LOSE)
		}else{
			stopGame(GameState.WIN)
		}
	}





	fun checkOn(line: Int, pos: Int, itemField: ItemField) {
		if (gameState.value == GameState.IN_PLAY && !itemField.hasFlag){
			internalField.checkAt(line, pos){
				stopGame(GameState.LOSE)
				boom.value = true
			}
		}
	}

	fun clickToRestart() {
		startNewSession()
	}

	private fun stopGame(state: GameState) {
		gameState.value = state
		timerJob?.cancel()
	}

	fun longPressOn(line: Int, pos: Int) {
		if (gameState.value != GameState.IN_PLAY) return
		val mineField = _field.value.data ?: return
		val itemFieldState = mineField.get(line, pos) ?: return

		val item = itemFieldState.value
		if (item.itemFieldState == ItemFieldState.Closed){
			if (!item.hasFlag){
				if (_flags.tryGetFlag()) itemFieldState.value = item.copy(hasFlag = true)
			}else{
				itemFieldState.value = item.copy(hasFlag = false)
				_flags.putFlag()
			}
		}
	}
}