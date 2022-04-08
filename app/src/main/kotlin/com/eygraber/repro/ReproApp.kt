package com.eygraber.repro

import android.app.Application
import android.content.Context
import com.google.android.play.core.splitcompat.SplitCompat

class ReproApp : Application() {
  override fun attachBaseContext(base: Context) {
    super.attachBaseContext(base)
    val isDebuggable = BuildConfig.DEBUG
    if(isDebuggable) {
      SplitCompat.install(this)
    }
  }
}
