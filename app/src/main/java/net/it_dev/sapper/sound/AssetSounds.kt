package net.it_dev.sapper.sound

import android.content.res.AssetManager
import android.media.SoundPool
import java.lang.IllegalStateException
import java.lang.NullPointerException

class AssetSounds(private val am:AssetManager, private val dir:String):ISoundPool {
    private val soundPool = SoundPool.Builder().setMaxStreams(3).build()
    private val map = mutableMapOf<String,Int>()


    override val isLoaded: Boolean
        get() = TODO("Not yet implemented")

    override fun load(path: String) {
        am.openFd("$dir/$path").use { afd ->
            if (map.containsKey(path))throw IllegalStateException("Double key in map! key=$path")
            map[path] = soundPool.load(afd,0)
        }
    }

    override fun unload(path: String) {
        val get = map.remove(path) ?: throw NullPointerException("Звук не загружен! path=$path")
        soundPool.unload(get)
    }

    override fun play(path: String, volume:Float) {
        val get = map[path] ?: throw NullPointerException("Звук не в map! path=$path")
        soundPool.play(get,volume,volume,1,0,1f)
    }


}