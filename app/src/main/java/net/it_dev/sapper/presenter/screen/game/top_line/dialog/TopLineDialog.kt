package net.it_dev.sapper.presenter.screen.game.top_line.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import net.it_dev.sapper.presenter.ui.theme.bg
import net.it_dev.sapper.presenter.ui.theme.border
@Preview
@Composable
fun DialogPreview(){
	val buttons = listOf("Test" to {}, "Test2" to { })
	TopLineDialog(buttons = buttons) {

	}
}

@Composable
fun TopLineDialog(
	buttons: List<Pair<String,()->Unit>>,
	onDismissRequest: () -> Unit
) {
	Dialog(onDismissRequest = onDismissRequest) {
		Surface(
			modifier = Modifier,
			border = BorderStroke(2.dp, Color.Black),
			elevation = 10.dp,
			color = bg
		) {
			Column {
				buttons.forEach {
					TextButton(onClick = {
						onDismissRequest()
						it.second()
					}) {
						Text(
							text = it.first,
							fontSize = 25.sp
						)
					}
				}
			}
		}
	}
}