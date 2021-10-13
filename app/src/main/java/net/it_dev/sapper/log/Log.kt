package net.it_dev.sapper.log

import android.util.Log

class Log(private val appTag:String):ILog {

    override fun d(tag: String, msg: String) {
        Log.d("$appTag $tag",msg)
    }

    override fun e(tag: String, msg: String) {
        Log.e("$appTag $tag",msg)
    }

    override fun i(tag: String, msg: String) {
        Log.i("$appTag $tag",msg)
    }

    override fun d(tag: Any, msg: String) {
        i(getClassName(tag), msg)
    }

    override fun e(tag: Any, msg: String) {
        e(getClassName(tag), msg)
    }

    override fun i(tag: Any, msg: String) {
        i(getClassName(tag), msg)
    }

    private inline fun getClassName(any: Any) = "[${any.javaClass.simpleName}]"
}