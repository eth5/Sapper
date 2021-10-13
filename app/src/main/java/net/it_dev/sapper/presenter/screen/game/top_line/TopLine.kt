package net.it_dev.sapper.presenter.screen.game.top_line

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import net.it_dev.sapper.R
import net.it_dev.sapper.domain.GameState
import net.it_dev.sapper.presenter.screen.game.GameScreenViewModel
import net.it_dev.sapper.presenter.screen.game.top_line.dialog.TopLineDialog
import net.it_dev.sapper.presenter.ui.theme.bg
import net.it_dev.sapper.presenter.ui.theme.border
import net.it_dev.sapper.presenter.util.getResString

@Composable
fun TopLine(viewModel: GameScreenViewModel = hiltViewModel()) {
	Row(
		modifier = Modifier
			.border(1.dp, border)
			.background(bg)
			.shadow(2.dp)
			.height(50.dp)
			.fillMaxWidth(),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.Center
	) {
		val flags by remember { viewModel.flags }
		Text(
			text = "$flags",
			color = Color.Red,
			maxLines = 1,
			textAlign = TextAlign.Center,
			fontSize = 30.sp,
			modifier = Modifier
				.weight(1f, false)
				.width(100.dp)
				.background(Color.Black)
				.border(1.dp, border)
				.shadow(2.dp)
		)
		val gameState by remember { viewModel.gameState }
		var icon by remember { mutableStateOf(Icons.Default.SentimentVeryDissatisfied) }

		LaunchedEffect(gameState){
			icon = when (gameState) {
				GameState.WIN -> Icons.Default.SentimentVerySatisfied
				GameState.LOSE -> Icons.Default.SentimentVeryDissatisfied
				GameState.IN_PLAY -> Icons.Default.SentimentSatisfied
				GameState.STOP -> Icons.Default.SentimentNeutral
			}
		}

		var saveIcon = remember { mutableStateOf<ImageVector?>(null) }
		Icon(
			imageVector = icon,
			contentDescription = "Face",
			tint = Color.Yellow,
			modifier = Modifier
				.fillMaxHeight()
				.size(50.dp)
				.padding(5.dp)
				.clickable {
					if (gameState == GameState.IN_PLAY) {
						saveIcon.value = icon
						icon = Icons.Default.Sick
						viewModel.showDialog.value = true
					} else {
						viewModel.clickToRestart()
					}
				}
		)
		ShowDialog(state = viewModel.showDialog, viewModel = viewModel) {
			icon = saveIcon.value!!
			viewModel.showDialog.value = false
		}
		Timer(viewModel.timer.time, Modifier.weight(1f,false))
	}
}

@Composable
fun ShowDialog(state:State<Boolean>, viewModel: GameScreenViewModel, onDismiss:()->Unit) {
	if (state.value){
		TopLineDialog(
			buttons = listOf(
				getResString(resId = R.string.open_fields) to viewModel::openAll,
				getResString(resId = R.string.reset_game) to viewModel::clickToRestart
			),
			onDismissRequest = onDismiss
		)
	}
}

@Composable
fun Timer(timer:State<Int>, modifier: Modifier = Modifier) {
	val h = timer.value / 60
	val s = timer.value % 60
	Text(
		text = "${if (h < 10) "0$h" else h}:${if (s < 10) "0$s" else s}",
		color = Color.Red,
		maxLines = 1,
		textAlign = TextAlign.Center,
		fontSize = 30.sp,
		modifier = modifier
			.width(100.dp)
			.background(Color.Black)
			.border(1.dp, border)
			.shadow(2.dp)
	)
}