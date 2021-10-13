package net.it_dev.sapper.log

interface ILog {
    fun d(tag: Any, msg: String)
    fun e(tag: Any, msg: String)
    fun i(tag: Any, msg: String)
    fun d(tag: String, msg: String)
    fun e(tag: String, msg: String)
    fun i(tag: String, msg: String)
}