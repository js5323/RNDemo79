package com.rtnmpaas;

import com.facebook.react.BaseReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.model.ReactModuleInfo
import com.facebook.react.module.model.ReactModuleInfoProvider

class MpaasPackage : BaseReactPackage() {
 override fun getModule(name: String, reactContext: ReactApplicationContext): NativeModule? =
    if (name == MpaasModule.NAME) {
        MpaasModule(reactContext)
    } else {
        null
    }

 override fun getReactModuleInfoProvider() = ReactModuleInfoProvider {
   mapOf(
     MpaasModule.NAME to ReactModuleInfo(
      MpaasModule.NAME,
       MpaasModule.NAME,
       false, // canOverrideExistingModule
       false, // needsEagerInit
       false, // isCxxModule
       true // isTurboModule
     )
   )
 }
}