package net.it_dev.sapper.presenter.screen.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import net.it_dev.sapper.R
import net.it_dev.sapper.presenter.util.TextLabelEditField
import net.it_dev.sapper.presenter.util.WaitWrapper
import net.it_dev.sapper.presenter.util.getResString
import net.it_dev.sapper.setting.Config
import net.it_dev.sapper.setting.Setting

@Composable
fun SettingScreen(navController: NavController, viewModel: SettingViewModel = hiltViewModel()) {

    WaitWrapper(state = viewModel.config) {
        Cfg(it, viewModel = viewModel, navController = navController)
    }
}
@Preview
@Composable
fun CfgPreview(){
    Cfg(config = Config(), viewModel = SettingViewModel(Setting(LocalContext.current.getSharedPreferences("",0))), NavController(
        LocalContext.current))
}
@Composable
fun Cfg(config: Config, viewModel: SettingViewModel,navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = getResString(resId = R.string.settings))
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            TextLabelEditField(R.string.width, stateValue = viewModel.width, onChangeSetter = viewModel::setWidth)
            TextLabelEditField(R.string.height, stateValue = viewModel.height, onChangeSetter = viewModel::setHeight)
            TextLabelEditField(R.string.mines, stateValue = viewModel.mines, onChangeSetter = viewModel::setMines)

        }
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable { if (viewModel.saveConfig())navController.popBackStack()  },
                contentAlignment = Alignment.Center
            ) {
                Text(text = getResString(resId = R.string.save))
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable { navController.popBackStack()},
                contentAlignment = Alignment.Center
            ) {
                Text(text = getResString(resId = R.string.cancel))
            }
        }
    }
}
