package net.it_dev.sapper.presenter.screen.game

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import net.it_dev.sapper.domain.*
import net.it_dev.sapper.presenter.bitmap.IBitmapFactory
import net.it_dev.sapper.setting.ISetting
import net.it_dev.sapper.sound.IFxPlayer
import net.it_dev.sapper.sound.ISoundPool
import net.it_dev.sapper.util.Resource
import javax.inject.Inject

@HiltViewModel
class GameScreenViewModel @Inject constructor(
	val imageBitmapFactory: IBitmapFactory,
	private val setting:ISetting,
	private val fxPlayer: IFxPlayer,
	private val sp:ISoundPool
) : ViewModel() {
	companion object {
		private const val TAG = "GameScreenViewModel"
	}
	var snackebarText = mutableStateOf<String?>(null)
	var showDialog = mutableStateOf(false)

	private data class SessionData(val rows: Int, val columns: Int, val mines: Int)

	private val _field = mutableStateOf<Resource<MineField>>(Resource.Undefine())
	val mineField: State<Resource<MineField>> get() = _field
	private val internalField get() = _field.value.data!!

	val gameState = mutableStateOf(GameState.STOP)
	val boom = mutableStateOf(false)

	private var _flags: Flags = Flags()
	val flags:State<Int> get() = _flags.count
	val timer = Timer(sp, viewModelScope)

	private lateinit var sessionData:SessionData

	fun initial(){
		val resource = setting.getConfig()
		//todo show error msg
		if (resource is Resource.Success){
			val config = resource.data
			sessionData = SessionData(config.rows, config.columns, config.mines)
			startNewSession()
		}

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
				timer.start(0)
			}
		}
	}

	private fun generateField(rows: Int, columns: Int, mines: Int): MineField {
		val field = MineField(rows, columns)
		field.setMinesToField(mines = mines, mineField = field)
		return field
	}



	private fun startTimer() {

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
				if (it == -1){
					stopGame(GameState.LOSE)
					boom.value = true
				}else {
					fxPlayer.play("harp.ogg",null)
				}
			}
		}
	}

	fun clickToRestart() {
		startNewSession()
	}

	private fun stopGame(state: GameState) {
		fxPlayer.play("boom.ogg",null)
		gameState.value = state
		timer.stop()
		snackebarText.value = "Game Over"
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