package net.it_dev.sapper.presenter.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import net.it_dev.sapper.ad.AdBanner
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
						Column(
							horizontalAlignment = Alignment.CenterHorizontally
						) {
							Box(
								modifier = Modifier
									.weight(1f)
									.fillMaxWidth(),
								contentAlignment = Alignment.Center
								) {
								Navigation()
							}
							AdBanner(asUnitId = "ca-app-pub-6127542757275882/1457275762")
						}
					}
				}
		}
	}
}