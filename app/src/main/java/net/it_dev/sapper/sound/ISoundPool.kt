package net.it_dev.sapper.sound

interface ISoundPool {
    val isLoaded:Boolean
    fun play(path:String, volume:Float)
}