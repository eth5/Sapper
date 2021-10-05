package net.it_dev.sapper.presenter.screen.game

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import net.it_dev.sapper.domain.ItemField
import net.it_dev.sapper.domain.ItemFieldState
import net.it_dev.sapper.presenter.screen.game.top_line.TopLine
import net.it_dev.sapper.presenter.ui.theme.border
import net.it_dev.sapper.util.Resource
import kotlin.random.Random

@Composable
fun GameScreen(navController: NavController, viewModel: GameScreenViewModel = hiltViewModel()) {
	LaunchedEffect(Unit){
		viewModel.initial(10,10,5)
	}

	var offsetX by remember { mutableStateOf(0.dp) }
	var offsetY by remember { mutableStateOf(0.dp) }
	val animationX by animateDpAsState(
		targetValue = offsetX,
		animationSpec = spring(Spring.DampingRatioHighBouncy)
	)
	val animationY by animateDpAsState(
		targetValue = offsetY,
		animationSpec = spring(Spring.DampingRatioHighBouncy)
	)
	Column(
		modifier = Modifier
			.offset(animationX, animationY)
			.padding(5.dp)
			.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		TopLine(viewModel)
		Divider(modifier = Modifier.padding(5.dp))
		Box(
			modifier = Modifier
				.weight(1f)
				.fillMaxWidth()
				.border(2.dp, border)
				.shadow(2.dp),
			contentAlignment = Alignment.Center
		) {
			Box(
				modifier = Modifier
					.verticalScroll(rememberScrollState())
					.horizontalScroll(rememberScrollState()),
				contentAlignment = Alignment.Center
			) {
				val mineFieldState by remember { viewModel.mineField }
				MineFieldStateWrapper(resource = mineFieldState)
			}
		}

	}
	var boom  by viewModel.boom
	if (boom) {
		LaunchedEffect(boom) {
			offsetX = Random.nextInt(-100, 100).dp
			offsetY = Random.nextInt(-100, 100).dp
			delay(50)
			offsetX = 0.dp
			offsetY = 0.dp
			boom = false
		}
	}

}


@Composable
fun MineFieldStateWrapper(resource: Resource<MineField>) {
	when (resource) {
		is Resource.Error -> Text(text = resource.message ?: "Unknown error", color = Color.Red)
		is Resource.Undefine, is Resource.Loading -> CircularProgressIndicator()
		is Resource.Success -> {
			MineField(mineField = resource.data)
		}
	}
}

@Composable
fun MineField(mineField: MineField, viewModel: GameScreenViewModel = hiltViewModel()) {
	Column(
		modifier = Modifier
			.border(2.dp, border)
			.shadow(2.dp)

	) {
		for (lineNumb in mineField.field.indices) {
			Row {
				val line = mineField.field[lineNumb]
				for (positionInLine in line.indices) {
					ItemField(lineNumb, positionInLine, line[positionInLine], viewModel = viewModel)
				}
			}
		}
	}
}

@Composable
fun ItemField(line: Int, pos: Int, itemField: MutableState<ItemField>, viewModel: GameScreenViewModel) {
	val item by  itemField
	Box(
		modifier = Modifier
			.size(30.dp)
			.pointerInput(Unit) {
				detectTapGestures(
					onLongPress = {
						viewModel.longPressOn(line, pos)
					},
					onTap = {
						viewModel.checkOn(line, pos, item)
					},
				)
			},
		contentAlignment = Alignment.Center
	) {
		val img =
			when (val itemFieldState = item.itemFieldState) {
				ItemFieldState.Boom -> "mine.jpg"
				ItemFieldState.Closed -> "block.jpg"
				is ItemFieldState.Empty -> "empty_block.jpg"
				is ItemFieldState.Digit -> "${itemFieldState.value}.jpg"
				ItemFieldState.Deactivate -> "mine.jpg"
			}
		Image(
			bitmap = viewModel.imageBitmapFactory.getImageBitmap(img),
			contentDescription = null,
			contentScale = ContentScale.FillBounds,
			modifier = Modifier.fillMaxSize(),
			colorFilter = when (item.itemFieldState) {
				ItemFieldState.Boom -> ColorFilter.tint(
					Color.Red,
					BlendMode.Overlay
				)
				ItemFieldState.Deactivate -> ColorFilter.tint(
					Color.Green,
					BlendMode.Overlay
				)
				else -> null
			}
		)
		if (item.hasFlag) {
			Image(
				bitmap = viewModel.imageBitmapFactory.getImageBitmap("flag.png"),
				contentDescription = "Flag",
				contentScale = ContentScale.FillBounds,
				modifier = Modifier.fillMaxSize(),
			)
		}

	}
}

