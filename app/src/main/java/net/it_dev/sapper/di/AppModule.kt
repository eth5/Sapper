package net.it_dev.sapper.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.it_dev.sapper.log.ILog
import net.it_dev.sapper.log.Log
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getLog(): ILog {
        return Log("SAPPER_")
    }
}