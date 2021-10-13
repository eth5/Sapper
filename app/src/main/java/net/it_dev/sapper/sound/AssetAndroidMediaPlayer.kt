package net.it_dev.sapper.sound

import android.content.res.AssetManager
import android.media.MediaPlayer

class AssetAndroidMediaPlayer(private val am: AssetManager, private val baseDir:String): IFxPlayer {
	private val mediaPlayer: MediaPlayer = MediaPlayer().also {
		it.setOnPreparedListener { mp->
			mp.start()
		}
		it.setOnCompletionListener {
			endPlayCallback?.invoke()
		}
	}
	private var endPlayCallback:(()->Unit)? = null
	private var isDisposed = false

	override val isPlaying: Boolean
		get() = mediaPlayer.isPlaying

	override fun setVolume(volume: Float) {
		mediaPlayer.setVolume(volume,volume)
	}

	override fun play(file: String, onEndPlay:(()->Unit)?) {
		if (isDisposed) throw IllegalStateException("MediaPlayer isDisposed!")

		mediaPlayer.reset()
		endPlayCallback = onEndPlay

		am.openFd("$baseDir/$file").use {
			mediaPlayer.setDataSource(it.fileDescriptor, it.startOffset, it.declaredLength)
			mediaPlayer.prepare()
		}
	}

	override fun stop() {
		if (mediaPlayer.isPlaying) mediaPlayer.stop()
	}

	override fun dispose() {
		isDisposed = true
		mediaPlayer.reset()
		mediaPlayer.release()
	}
}