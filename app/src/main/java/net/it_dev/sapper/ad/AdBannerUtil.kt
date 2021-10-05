package net.it_dev.sapper.ad

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import net.it_dev.sapper.BuildConfig


@Composable
fun AdBanner(asUnitId: String) {
    AndroidView(
        factory = { ctx ->
            val adUtil = AdBannerUtil()
            val adView = adUtil.createBanner(
                context = ctx,
                adUnitId = if (BuildConfig.DEBUG) "ca-app-pub-3940256099942544/6300978111" else asUnitId
            )
            adUtil.initialBanner(ctx, adView)
            adView
        },
        modifier = Modifier
    )
}

private class AdBannerUtil(){
    fun initialBanner(context: Context, adView:AdView){
        MobileAds.initialize(context) {}
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }
    fun createBanner(context: Context, adUnitId:String):AdView{
        val adView = AdView(context)
        adView.adSize = AdSize.BANNER
        adView.adUnitId = adUnitId
        return adView
    }
}
