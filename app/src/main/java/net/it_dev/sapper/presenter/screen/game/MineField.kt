package net.it_dev.sapper.presenter.screen.game

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import net.it_dev.sapper.domain.ItemField
import net.it_dev.sapper.domain.ItemFieldState

class MineField(val rows:Int, val columns:Int) {
	private val default = ItemField(ItemFieldState.Closed, false)
	val field = Array(rows) {
		Array(columns) { mutableStateOf<ItemField>(default) }
	}

	fun get(line:Int, pos:Int):MutableState<ItemField>?{
		return field.getOrNull(line)?.getOrNull(pos)
	}
}