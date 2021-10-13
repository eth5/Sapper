package net.it_dev.sapper

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import net.it_dev.sapper.asset.AssetCopy
import net.it_dev.sapper.log.ILog
import net.it_dev.sapper.log.Log0
import java.io.File
import java.lang.annotation.Inherited
import javax.inject.Inject

@HiltAndroidApp
class SapperApplication:Application(){
    @Inject lateinit var log: ILog
    override fun onCreate() {
        super.onCreate()
        if (!File(baseContext.filesDir.path + "/imgs").exists()){
            log.i(this,"copy asset files")
            AssetCopy(baseContext).copy(File("imgs"),File(baseContext.filesDir.path))
        }
    }
}