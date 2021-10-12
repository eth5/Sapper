package net.it_dev.sapper.setting

import net.it_dev.sapper.util.Resource

interface ISetting {
    fun getConfig():Resource<Config>
    fun saveConfig(config: Config):Resource<Boolean>
}