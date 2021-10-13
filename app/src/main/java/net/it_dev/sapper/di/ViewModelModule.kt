package net.it_dev.sapper.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import net.it_dev.sapper.presenter.bitmap.AssetImageProvider
import net.it_dev.sapper.presenter.bitmap.IBitmapFactory
import net.it_dev.sapper.setting.ISetting
import net.it_dev.sapper.setting.Setting
import net.it_dev.sapper.sound.AssetAndroidMediaPlayer
import net.it_dev.sapper.sound.IFxPlayer

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {


	@ViewModelScoped
	@Provides
	fun provideBitmapFactory(@ApplicationContext context: Context): IBitmapFactory {
		val assetImageProvider = AssetImageProvider()
		assetImageProvider.loadAllImagesFrom(
			"imgs",
			context.assets
		)
		return assetImageProvider
	}
	@ViewModelScoped
	@Provides
	fun getSetting(@ApplicationContext ctx: Context):ISetting{
		return Setting(ctx.getSharedPreferences("settings", Context.MODE_PRIVATE))
	}
	@ViewModelScoped
	@Provides
	fun getFxPlayer(@ApplicationContext ctx: Context):IFxPlayer{
		return AssetAndroidMediaPlayer(ctx.assets, "sounds")
	}

}