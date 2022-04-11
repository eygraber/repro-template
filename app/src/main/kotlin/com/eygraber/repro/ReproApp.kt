package com.eygraber.repro

import android.app.Application
import android.content.Context
import com.google.android.play.core.splitcompat.SplitCompat

class ReproApp : Application() {
  override fun attachBaseContext(base: Context) {
    super.attachBaseContext(base)
    SplitCompat.install(this)
    CanBeNotNullFalsePositive(null).bind()
  }
}
