package net.it_dev.sapper.domain

sealed class ItemFieldState{
	object Closed:ItemFieldState()
	object Empty:ItemFieldState()
	//object Flagged:ItemFieldState()
	object Boom:ItemFieldState()
	object Deactivate:ItemFieldState()
	class Digit(val value:Int):ItemFieldState()
}
