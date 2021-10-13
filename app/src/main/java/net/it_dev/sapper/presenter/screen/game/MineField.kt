package net.it_dev.sapper.presenter.screen.game

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import net.it_dev.sapper.domain.ItemField
import net.it_dev.sapper.domain.ItemFieldState

class MineField(val rows: Int, val columns: Int) {
	private val default = ItemField(ItemFieldState.Closed, hasFlag = false, hasMine = false)
	val field = Array(rows) {
		Array(columns) { mutableStateOf<ItemField>(default) }
	}


	fun get(line: Int, pos: Int): MutableState<ItemField>? {
		return field.getOrNull(line)?.getOrNull(pos)
	}

	fun setMinesToField(mines: Int, mineField: MineField) {
		if (mineField.rows == 0 || mineField.columns == 0) throw IllegalStateException()
		val maxMines = mineField.rows * mineField.columns

		var mines = if (mines > maxMines) {
			Log.e("MineField", "mines > maxMines")
			mines.coerceAtMost(maxMines)
		} else mines

		while (mines > 0) {
			val itemField = mineField.field.random().random()
			if (itemField.value.hasMine) continue
			itemField.value = ItemField(ItemFieldState.Closed, hasFlag = false, hasMine = true)
			mines--
		}
	}


	fun open(line: Int, pos: Int) {
		val itemField = get(line, pos) ?: return
		open(itemField = itemField)
	}

	fun open(itemField:MutableState<ItemField>) {
		val item = itemField.value

		val state = when {
			item.hasMine && item.hasFlag -> ItemFieldState.Deactivate
			item.hasMine -> ItemFieldState.Boom
			else -> ItemFieldState.Empty
		}
		itemField.value = item.copy(itemFieldState = state)
	}

	fun checkAt(line: Int, pos: Int, onOpen: ((Int) -> Unit)? = null) {

		val itemField = get(line, pos) ?: return

		val field = itemField.value

		if (field.hasMine) {
			itemField.value = field.copy(itemFieldState = ItemFieldState.Boom)
			if (onOpen == null) throw IllegalStateException("boom not can be null in this state!!!")
			onOpen(-1)
			return
		}

		if (field.itemFieldState != ItemFieldState.Closed || field.hasFlag) return

		val mines = minesAround(line, pos)

		if (mines == 0) {

			onOpen?.invoke(1)
			itemField.value = field.copy(itemFieldState = ItemFieldState.Empty)
			checkAt(line - 1, pos - 1)
			checkAt(line - 1, pos)
			checkAt(line - 1, pos + 1)
			checkAt(line, pos + 1)
			checkAt(line + 1, pos + 1)
			checkAt(line + 1, pos)
			checkAt(line + 1, pos - 1)
			checkAt(line, pos - 1)

		} else {
			itemField.value = field.copy(itemFieldState = ItemFieldState.Digit(mines))
		}
	}

	fun minesAround(line: Int, pos: Int): Int {
		var mines = 0
		if (get(line - 1, pos - 1)?.value?.hasMine == true) mines++
		if (get(line - 1, pos)?.value?.hasMine == true) mines++
		if (get(line - 1, pos + 1)?.value?.hasMine == true) mines++
		if (get(line, pos + 1)?.value?.hasMine == true) mines++
		if (get(line + 1, pos + 1)?.value?.hasMine == true) mines++
		if (get(line + 1, pos)?.value?.hasMine == true) mines++
		if (get(line + 1, pos - 1)?.value?.hasMine == true) mines++
		if (get(line, pos - 1)?.value?.hasMine == true) mines++
		return mines
	}
}