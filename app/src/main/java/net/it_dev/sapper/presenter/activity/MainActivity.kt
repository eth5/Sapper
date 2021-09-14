package net.it_dev.sapper.presenter.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import net.it_dev.sapper.presenter.navigate.Navigation
import net.it_dev.sapper.presenter.ui.theme.SapperTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
				SapperTheme {
					Surface(
						color = MaterialTheme.colors.background,
						modifier = Modifier.fillMaxSize()
					) {
						Navigation()
					}
				}

		}
	}
}