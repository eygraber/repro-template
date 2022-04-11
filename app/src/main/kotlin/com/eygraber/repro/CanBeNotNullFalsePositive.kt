package com.eygraber.repro

import android.util.Log

internal class CanBeNotNullFalsePositive(
  private val fullName: String?
) {
  private var isVisible: Boolean = false

  fun bind() {
    setField(fullName)
  }

  private fun setField(
    text: String?
  ) {
    isVisible = text?.also {
      Log.e("TEST", text)
    } != null
  }
}
