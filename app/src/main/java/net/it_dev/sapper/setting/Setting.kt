package net.it_dev.sapper.setting

import android.content.SharedPreferences
import net.it_dev.sapper.util.Resource

class Setting(private val preferences: SharedPreferences):ISetting {
    companion object{
        private const val ROWS = "rows"
        private const val COLUMNS = "columns"
        private const val MINE = "mine"
    }
    override fun getConfig(): Resource<Config> {
            val config = Config(
                preferences.getInt(ROWS, 10),
                preferences.getInt(COLUMNS, 10),
                preferences.getInt(MINE, 5)
            )
        return Resource.Success(config)
    }

    override fun saveConfig(config: Config):Resource<Boolean> {
        preferences.edit().run{
            putInt(ROWS, config.rows)
            putInt(COLUMNS, config.columns)
            putInt(MINE, config.mines)
            apply()
        }
        return Resource.Success(true)
    }


}