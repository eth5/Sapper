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
			//"imgs/block.jpg", "imgs/flag.png","imgs/empty_block.jpg","imgs/1.jpg","imgs/2.jpg","imgs/3.jpg","imgs/4.jpg","imgs/5.jpg","imgs/6.jpg","imgs/7.jpg","imgs/8.jpg","imgs/mine.jpg"
		)
		return assetImageProvider
	}
	@ViewModelScoped
	@Provides
	fun getSetting(@ApplicationContext ctx: Context):ISetting{
		return Setting(ctx.getSharedPreferences("settings", Context.MODE_PRIVATE))
	}

}