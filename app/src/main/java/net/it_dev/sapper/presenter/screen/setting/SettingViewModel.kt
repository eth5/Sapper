package net.it_dev.sapper.presenter.screen.setting

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.it_dev.sapper.setting.Config
import net.it_dev.sapper.setting.ISetting
import net.it_dev.sapper.util.Resource
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val setting:ISetting
):ViewModel() {
    private val _config = mutableStateOf<Resource<Config>>(Resource.Undefine())
    val config: State<Resource<Config>> get() = _config

    val width = mutableStateOf(0)
    val height = mutableStateOf(0)
    val mines = mutableStateOf(0)

    init {
        viewModelScope.launch {

            loadConfig()
        }
    }

    private fun loadConfig(){
        _config.value = setting.getConfig()
        if (_config.value is Resource.Success){
            setConfigStates(_config.value.data!!)
        }
    }
    private fun setConfigStates(config:Config){
        width.value = config.rows
        height.value = config.columns
        mines.value = config.mines
    }

    fun saveConfig():Boolean{
        val result = createConfig()
        if (result is Resource.Success) {
            setting.saveConfig(result.data)
            return true
        }

        return false;
    }

    fun setWidth(value:String) {
        width.value = stringToInt(value).coerceAtLeast(1)
    }
    fun setHeight(value:String){
        height.value = stringToInt(value).coerceAtLeast(1)
    }
    fun setMines(value: String){
        mines.value = stringToInt(value).coerceAtLeast(1)
    }

    private fun stringToInt(value: String):Int{
        val filteredValue = value.filter { it.isDigit() }
        return if (filteredValue.isEmpty()) 0
        else filteredValue.toInt()
    }
    private fun createConfig():Resource<Config>{
        val config = Config(
            width.value,
            height.value,
            mines.value
        )
        return Resource.Success(config)
    }
}