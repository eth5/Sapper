package net.it_dev.sapper.presenter.navigate

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.it_dev.sapper.presenter.screen.game.GameScreen
import net.it_dev.sapper.presenter.screen.setting.SettingScreen
import net.it_dev.sapper.presenter.screen.start.StartScreen

@Composable
fun Navigation() {
	val navController = rememberNavController()
	NavHost(navController = navController, startDestination = Screens.Start.route ){
		composable(Screens.Start.route){
			StartScreen(navController = navController)
		}
		composable(Screens.Game.route){
			GameScreen(navController = navController)
		}
		composable(Screens.Setting.route){
			SettingScreen(navController = navController)
		}

	}

}