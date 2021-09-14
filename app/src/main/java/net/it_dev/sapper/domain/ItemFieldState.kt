package net.it_dev.sapper.domain

sealed class ItemFieldState{
	object Closed:ItemFieldState()
	object Open:ItemFieldState()
	object Flagged:ItemFieldState()
	object Boom:ItemFieldState()
	class Digit(val value:Int):ItemFieldState()
}
