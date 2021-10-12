package net.it_dev.sapper.presenter.util

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
inline fun getResString(@StringRes resId:Int):String  = LocalContext.current.getString(resId)
