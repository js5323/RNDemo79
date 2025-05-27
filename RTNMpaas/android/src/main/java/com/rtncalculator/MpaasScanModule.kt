package com.rtnmpaas

import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.rtnmpaas.NativeRTNMpaasSpec

class MpaasScanModule(reactContext: ReactApplicationContext) : NativeRTNMpaasSpec(reactContext) {

  override fun getName() = NAME

  override fun add(a: Double, b: Double, promise: Promise) {
    promise.resolve(a + b)
  }

  companion object {
    const val NAME = "RTNMpaasScanModule"
  }
}