package net.it_dev.sapper.asset

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * Класс копирует данные из ассет дирректории в дирректорию приложения
 */

class AssetCopy(private val ctx:Context) {
    fun copy(source:File, target:File){
        if (!target.exists()) target.mkdir()
        try {
            val open = ctx.assets.open(source.path)
            open.copyTo(target, source.name)
        }catch (i:IOException){

            val list = ctx.assets.list(source.path) ?: return
            for (file in list) {
                copy(File(source, file), File(target,source.name))
            }
        }
    }
    private fun InputStream.copyTo(targetDir: File, fileName:String) {
        val file = File(targetDir, fileName)
        val outputStream = FileOutputStream(file)
        this.copyTo(outputStream, 4096)
        outputStream.close()
    }

}