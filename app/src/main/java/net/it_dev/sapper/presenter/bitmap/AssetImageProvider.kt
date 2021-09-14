package net.it_dev.sapper.presenter.bitmap

import android.content.res.AssetManager
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.lang.IllegalArgumentException
import java.lang.NullPointerException

class AssetImageProvider():IBitmapFactory {
	private val map = mutableMapOf<String,ImageBitmap>()

	fun loadNewImages(am:AssetManager, vararg files:String){
		map.clear()
		files.forEach { file->
			am.open(file).use {
				val imageBitmap = BitmapFactory.decodeStream(it).asImageBitmap()
				if (map.containsKey(file)) throw IllegalArgumentException("double key")
				map[file] = imageBitmap
			}
		}
	}

	override fun getImageBitmap(file:String):ImageBitmap = map[file] ?: throw NullPointerException("file not found in map! file=$file")
}