package net.it_dev.sapper.presenter.screen.start

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import net.it_dev.sapper.presenter.navigate.Screens

@Preview
@Composable
fun StartScreenPreview(){

}

@Composable
fun StartScreen(navController: NavController, viewModel: StartScreenViewModel = hiltViewModel()) {
	Box(
		modifier = Modifier.fillMaxSize(),
		contentAlignment = Alignment.Center
	){
		Column (
			horizontalAlignment = Alignment.CenterHorizontally
		){
			ButtonsColumn(navController = navController)
		}
	}

}



private class Button(val text:String, val action:()->Unit, val stateEnable: State<Boolean> = mutableStateOf(true))
@Composable
fun ButtonsColumn(navController: NavController) {
	val buttons = remember{
		listOf(
			Button("Новая игра", {navController.navigate(Screens.Game.route)},),
			Button("Настройка", {},),
			Button("Герои", {},),
			Button("Sign In", {},),
		)
	}
	Column (
		modifier = Modifier
			.border(1.dp, Color.Gray, MaterialTheme.shapes.medium)
		,
		horizontalAlignment = Alignment.CenterHorizontally
	){
		buttons.forEach {
			val enableState by remember { it.stateEnable }
			Box(modifier = Modifier.padding(10.dp)){
				TextButton(
					onClick = it.action,
					enabled = enableState,
					modifier = Modifier
						.background(MaterialTheme.colors.secondary, MaterialTheme.shapes.medium)
						.border(
							2.dp,
							MaterialTheme.colors.onSecondary,
							MaterialTheme.shapes.medium
						),
				) {
					Text(
						text = it.text,
						fontSize = 30.sp,
						fontWeight = FontWeight.Bold
					)
				}
			}

			Spacer(modifier = Modifier.height(5.dp))
		}
	}
}