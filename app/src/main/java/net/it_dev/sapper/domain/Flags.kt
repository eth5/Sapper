package net.it_dev.sapper.domain

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class Flags() {
	private val _count = mutableStateOf(0)
	val count: State<Int> get() = _count
	fun setCount(value:Int){
		_count.value = value
	}


	fun isHasFlags() = _count.value > 0

	fun tryGetFlag():Boolean {
		return if (isHasFlags()){
			_count.value--
			true
		}else {
			false
		}
	}
	fun putFlag(){
		_count.value++
	}

}