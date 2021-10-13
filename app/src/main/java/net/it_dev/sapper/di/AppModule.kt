package net.it_dev.sapper.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.it_dev.sapper.log.ILog
import net.it_dev.sapper.log.Log
import net.it_dev.sapper.sound.ISoundPool
import net.it_dev.sapper.sound.AssetSounds
import net.it_dev.sapper.sound.Fx
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getLog(): ILog {
        return Log("SAPPER_")
    }


    @Singleton
    @Provides
    fun getSoundPool(@ApplicationContext ctx: Context):ISoundPool{
        val assetSounds =  AssetSounds(ctx.assets, "fx")

        assetSounds.load(Fx.PIC, Fx.FLAG_PUT, Fx.FLAG_TAKE)

        return assetSounds;
    }
}