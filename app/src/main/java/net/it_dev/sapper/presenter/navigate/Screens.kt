package net.it_dev.sapper.presenter.navigate

sealed class Screens(val route: String){
	object Start:Screens("start_screen")
	object Game:Screens("game_screen")
	object Setting:Screens("setting_screen")
}
