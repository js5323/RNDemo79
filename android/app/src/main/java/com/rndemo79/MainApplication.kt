package com.rndemo79

import android.app.Application
import android.util.Log
import com.facebook.react.PackageList
import com.facebook.react.ReactApplication
import com.facebook.react.ReactHost
import com.facebook.react.ReactNativeHost
import com.facebook.react.ReactPackage
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint.load
import com.facebook.react.defaults.DefaultReactHost.getDefaultReactHost
import com.facebook.react.defaults.DefaultReactNativeHost
import com.facebook.react.soloader.OpenSourceMergedSoMapping
import com.facebook.soloader.SoLoader
import com.mpaas.android.mPaaS
import com.mpaas.mriver.api.init.MriverInitParam


class MainApplication : Application(), ReactApplication {

  override val reactNativeHost: ReactNativeHost =
      object : DefaultReactNativeHost(this) {
        override fun getPackages(): List<ReactPackage> =
            PackageList(this).packages.apply {
              // Packages that cannot be autolinked yet can be added manually here, for example:
              // add(MyReactNativePackage())
            }

        override fun getJSMainModuleName(): String = "index"

        override fun getUseDeveloperSupport(): Boolean = BuildConfig.DEBUG

        override val isNewArchEnabled: Boolean = BuildConfig.IS_NEW_ARCHITECTURE_ENABLED
        override val isHermesEnabled: Boolean = BuildConfig.IS_HERMES_ENABLED
      }

  override val reactHost: ReactHost
    get() = getDefaultReactHost(applicationContext, reactNativeHost)

  override fun onCreate() {
      super.onCreate()
      SoLoader.init(this, OpenSourceMergedSoMapping)
      if (BuildConfig.IS_NEW_ARCHITECTURE_ENABLED) {
          // If you opted-in for the New Architecture, we load the native entry point for this app.
          load()
      }

//      MP.init(
//          this,
//          MPInitParam.obtain().setCallback { Log.d("TAG", "mPaaS Init finish") }
//      )

      //mPaas 初始化
      mPaaS(this){
//          mriver {
//              isAutoInitMriver = true
//              mriverInitCallback = object : MriverInitParam.MriverInitCallback {
//                  override fun onInit() {
//                      if (com.alibaba.ariver.kernel.common.utils.ProcessUtils.isMainProcess()) {
//                          // 小程序相关配置，比如自定义jsapi，titlebar等
//                          TODO("小程序相关配置，比如自定义jsapi，titlebar等")
//                      }
//                  }
//                  override fun onError(p0: Exception?) {
//                      TODO("Not yet implemented")
//                  }
//              }
//          }
          callback {
              //DO something
              Log.i("Framework","mPaaS 初始化完成")
          }
      }
  }
}
