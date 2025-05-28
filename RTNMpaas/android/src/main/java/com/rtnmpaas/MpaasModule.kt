package com.rtnmpaas

import android.content.Context
import com.alipay.android.phone.scancode.export.ScanRequest
import com.alipay.android.phone.scancode.export.adapter.MPScan
import com.alipay.android.phone.scancode.export.adapter.MPScanCallbackAdapter
import com.alipay.android.phone.scancode.export.adapter.MPScanError
import com.alipay.android.phone.scancode.export.adapter.MPScanResult
import com.alipay.android.phone.scancode.export.adapter.MPScanStarter
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext


class MpaasModule(reactContext: ReactApplicationContext) : NativeRTNMpaasSpec(reactContext) {

  override fun getName() = NAME

  // 扫描状态
  private var isScanning: Boolean = false
  private var currentScanType: String? = null

  // 开始扫描方法实现
  override fun startScan(type: String, promise: Promise) {
    if (isScanning) {
      promise.reject("E_SCANNING_IN_PROGRESS", "已经有扫描在进行中")
      return
    }

    val activity = reactApplicationContext.currentActivity
    if (activity == null) {
      promise.reject("E_ACTIVITY_NOT_AVAILABLE", "当前 Activity 不可用")
      return
    }

    try {
      // 模拟扫描启动逻辑
      isScanning = true
      currentScanType = type
      
      try {
        val scanRequest: ScanRequest = ScanRequest();
        scanRequest.setScanType(ScanRequest.ScanType.QRCODE);

        MPScan.startMPaasScanFullScreenActivity(
          activity,
          scanRequest,
          object : MPScanCallbackAdapter() {
            override fun onScanFinish(
              context: Context,
              mpScanResult: MPScanResult,
              mpScanStarter: MPScanStarter
            ): Boolean {
              // 模拟扫描成功结果
              val result = Arguments.createMap().apply {
                putString("type", type)
                putString("data", mpScanResult.text)
                putDouble("timestamp", System.currentTimeMillis() / 1000.0)
              }

              promise.resolve(result)
              // 返回 true 表示该回调已消费，不需要再次回调
              return true
            }

            override fun onScanError(context: Context?, error: MPScanError?): Boolean {
              // 识别错误
              promise.reject("E_SCAN_FAILED", error?.msg)
              return true;
            }
          })
        

      } catch (e: Exception) {
        promise.reject("E_SCAN_FAILED", "扫描过程中发生错误", e)
      } finally {
        isScanning = false
      }
    } catch (e: Exception) {
      isScanning = false
      promise.reject("E_SCAN_INIT_FAILED", "扫描初始化失败", e)
    }
  }

  // 停止扫描方法实现
  override fun stopScan(promise: Promise) {
    if (!isScanning) {
      promise.resolve(null)
      return
    }

    try {
      // 停止扫描逻辑
      isScanning = false
      currentScanType = null
      promise.resolve(null)
    } catch (e: Exception) {
      promise.reject("E_STOP_SCAN_FAILED", "停止扫描失败", e)
    }
  }

  // 检查扫描状态方法实现
  override fun isScanning(): Boolean = isScanning

  // 实现 RTNMpaasScanModule.Spec 接口中的其他方法
  override fun getConstants(): MutableMap<String, Any>? = mutableMapOf(
    "SUPPORTED_TYPES" to listOf("qrCode", "barcode", "image")
  )


  companion object {
    const val NAME = "RTNMpaas"
  }
}