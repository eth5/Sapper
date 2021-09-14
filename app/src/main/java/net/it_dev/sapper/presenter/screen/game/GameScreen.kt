package net.it_dev.sapper.presenter.screen.game

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import net.it_dev.sapper.domain.ItemField
import net.it_dev.sapper.domain.ItemFieldState
import net.it_dev.sapper.util.Resource

@Composable
fun GameScreen(navController: NavController, viewModel: GameScreenViewModel = hiltViewModel()) {
	Box(
		modifier = Modifier
			.fillMaxSize()
			.verticalScroll(rememberScrollState())
			.horizontalScroll(rememberScrollState())
		,
		contentAlignment = Alignment.Center
	){
		val mineFieldState by remember{ viewModel.mineField }
		MineFieldStateWrapper(resource = mineFieldState)
	}

}

@Composable
fun MineFieldStateWrapper(resource:Resource<MineField>) {
	when(resource){
		is Resource.Error -> Text(text = resource.message ?: "Unknown error", color = Color.Red)
		is Resource.Undefine, is Resource.Loading -> CircularProgressIndicator()
		is Resource.Success -> MineField(mineField = resource.data)
	}
}

@Composable
fun MineField(mineField:MineField, viewModel: GameScreenViewModel = hiltViewModel()) {
	Row (modifier = Modifier.border(1.dp, Color.Gray)){
		for (lineNumb in mineField.field.indices){
			Column {
				val line = remember { mineField.field[lineNumb] }
				for (positionInLine in line.indices){
					ItemField(lineNumb, positionInLine, line[positionInLine], viewModel = viewModel)
				}

			}
		}
	}
}

@Composable
fun ItemField(line:Int, pos:Int, itemField: MutableState<ItemField>, viewModel: GameScreenViewModel) {
	val item by remember { itemField }
	var bitmap by remember { mutableStateOf<ImageBitmap?>(null) }

	LaunchedEffect(item){
		val img = when(item.itemFieldState){
			ItemFieldState.Boom -> TODO()
			ItemFieldState.Closed -> "block.jpg"
			ItemFieldState.Flagged -> "block_flag.jpg"
			is ItemFieldState.Open -> TODO()
		}
		bitmap = viewModel.imageBitmapFactory.getImageBitmap(img)
	}
	if (bitmap!=null){
		Box(modifier = Modifier
			.size(30.dp)
			.clickable { viewModel.clickOn(line, pos, itemField) }
			,
			contentAlignment = Alignment.Center
		){
			Image(
				bitmap = bitmap!!,
				contentDescription = null,
				contentScale = ContentScale.FillBounds,
				modifier = Modifier.fillMaxSize()

			)
		}
	}

}