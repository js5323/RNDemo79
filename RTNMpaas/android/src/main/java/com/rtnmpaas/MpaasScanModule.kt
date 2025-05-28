package com.rtnmpaas

import com.facebook.react.bridge.*
import com.facebook.react.bridge.ReactApplicationContext
import com.rtnmpaas.NativeRTNMpaasSpec

class MpaasScanModule(reactContext: ReactApplicationContext) : NativeRTNMpaasSpec(reactContext) {

  override fun getName() = NAME

  companion object {
    const val NAME = "RTNMpaasScan"
  }

  // 扫描状态
  private var isScanning: Boolean = false
  private var currentScanType: String? = null

  // 开始扫描方法实现
  override fun startScan(type: String, promise: Promise) {
    if (isScanning) {
      promise.reject("E_SCANNING_IN_PROGRESS", "已经有扫描在进行中")
      return
    }

    try {
      // 模拟扫描启动逻辑
      isScanning = true
      currentScanType = type
      
      try {
          Thread.sleep(2000) // 模拟2秒扫描时间
        
        // 模拟扫描成功结果
        val result = Arguments.createMap().apply {
          putString("type", type)
          putString("data", "SCAN_RESULT_123456")
          putDouble("timestamp", System.currentTimeMillis() / 1000.0)
        }
        
        promise.resolve(result)
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
}