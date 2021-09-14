package net.it_dev.sapper.presenter.bitmap

import androidx.compose.ui.graphics.ImageBitmap

interface IBitmapFactory {
	fun getImageBitmap(file: String): ImageBitmap
}