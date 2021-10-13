package net.it_dev.sapper.sound

interface ISoundPool {
    val isLoaded:Boolean
    fun load (path:String)
    fun unload(path:String)
    fun play(path:String, volume:Float)
}