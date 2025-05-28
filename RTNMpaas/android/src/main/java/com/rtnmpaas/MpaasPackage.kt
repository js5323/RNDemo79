package com.rtnmpaas;

import com.facebook.react.BaseReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.model.ReactModuleInfo
import com.facebook.react.module.model.ReactModuleInfoProvider

class MpaasPackage : BaseReactPackage() {
 override fun getModule(name: String, reactContext: ReactApplicationContext): NativeModule? =
   when (name) {
        MpaasScanModule.NAME -> MpaasScanModule(reactContext)
        else -> null
    }

 override fun getReactModuleInfoProvider() = ReactModuleInfoProvider {
   mapOf(
     MpaasScanModule.NAME to ReactModuleInfo(
      MpaasScanModule.NAME,
       MpaasScanModule.NAME,
       false, // canOverrideExistingModule
       false, // needsEagerInit
       false, // isCxxModule
       true // isTurboModule
     )
   )
 }
}