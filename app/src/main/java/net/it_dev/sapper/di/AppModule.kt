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

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {


	@ViewModelScoped
	@Provides
	fun provideBitmapFactory(@ApplicationContext context: Context): IBitmapFactory {
		val assetImageProvider = AssetImageProvider()
		assetImageProvider.loadNewImages(
			context.assets,
			"block.jpg", "flag.png","empty_block.jpg","1.jpg","2.jpg","3.jpg","4.jpg","5.jpg","6.jpg","7.jpg","8.jpg","mine.jpg"
		)
		return assetImageProvider
	}
}