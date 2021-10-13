package net.it_dev.sapper.setting

data class Config (
    val rows:Int = 10,
    val columns:Int = 10,
    val mines:Int = 5,

    val volume:Float = 1f
)