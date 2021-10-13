package net.it_dev.sapper.presenter.bitmap

import android.content.res.AssetManager
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

class AssetImageProvider():IBitmapFactory {
	private val map = mutableMapOf<String,ImageBitmap>()

	fun loadAllImagesFrom(dir:String, am:AssetManager){
		map.clear()
		am.list(dir)?.forEach {fileName->
			am.open("$dir/$fileName").use {
				val imageBitmap = BitmapFactory.decodeStream(it).asImageBitmap()
				if (map.containsKey(fileName)) throw IllegalArgumentException("double key")
				map[fileName] = imageBitmap
			}
		}
	}

	override fun getImageBitmap(file:String):ImageBitmap = map[file] ?: throw NullPointerException("file not found in map! file=$file")
}