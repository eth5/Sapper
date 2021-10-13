package net.it_dev.sapper.sound

interface IFxPlayer {
	val isPlaying:Boolean
	fun play(file: String, onEndPlay:(()->Unit)?)
	fun setVolume(volume: Float)
	fun stop()
	fun dispose()
}