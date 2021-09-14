package net.it_dev.sapper.presenter.screen.game

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch
import net.it_dev.sapper.domain.ItemField
import net.it_dev.sapper.domain.ItemFieldState
import net.it_dev.sapper.presenter.bitmap.IBitmapFactory
import net.it_dev.sapper.util.Resource
import javax.inject.Inject

@HiltViewModel
class GameScreenViewModel @Inject constructor(
	val imageBitmapFactory: IBitmapFactory
) : ViewModel() {
	companion object{
		private const val TAG = "GameScreenViewModel"
	}

	private val _field = mutableStateOf<Resource<MineField>>(Resource.Undefine())
	val mineField: State<Resource<MineField>> get() = _field


	init {
		generateField(10,10, 5)
	}

	fun generateField(rows: Int, columns: Int, mines:Int) {
		_field.value = Resource.Loading()
		viewModelScope.launch (Dispatchers.Default){
			val field = MineField(rows, columns)
			setMinesToField(mines = mines,mineField = field)
			(Dispatchers.Main){ _field.value = Resource.Success(field) }
		}

	}

	private fun setMinesToField(mines: Int, mineField: MineField) {
		if (mineField.rows == 0 || mineField.columns == 0) throw IllegalStateException()
		val maxMines = mineField.rows * mineField.columns

		var mines = if (mines > maxMines){
			Log.e(TAG, "mines > maxMines")
			mines.coerceAtMost(maxMines)
		} else mines

		while (mines > 0) {
			val itemField = mineField.field.random().random()
			if (itemField.value.hasMine) continue
			itemField.value = ItemField(ItemFieldState.Closed,true)
			mines--
		}
	}

	fun clickOn(line:Int, pos:Int, itemField: MutableState<ItemField>){

		val state = when(itemField.value.itemFieldState){
			ItemFieldState.Closed -> ItemFieldState.Flagged
			ItemFieldState.Flagged -> ItemFieldState.Closed
			else -> return
		}

		val item = itemField.value.copy(itemFieldState = state)
		itemField.value = item
	}

	private fun check(line:Int, pos:Int, field: MineField){
		//val itemField = field.get(line,pos)
		//if (itemField.hasMine) return

	}

}