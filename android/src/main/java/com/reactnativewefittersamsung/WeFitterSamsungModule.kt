package com.reactnativewefittersamsung

import com.facebook.react.bridge.*
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.wefitter.shealth.WeFitterSHealth
import com.wefitter.shealth.WeFitterSHealthError
import java.text.SimpleDateFormat
import java.util.*

class WeFitterSamsungModule(private val reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  private val weFitter by lazy { WeFitterSHealth(currentActivity!!) }
  private val errors = mutableMapOf<WeFitterSHealthError.Reason, WeFitterSHealthError>()

  override fun getName(): String {
    return "WeFitterSamsung"
  }

  @ReactMethod
  fun configure(config: ReadableMap) {
    val token = config.getString("token") ?: ""
    val apiUrl = config.getString("apiUrl") ?: ""
    val statusListener = object : WeFitterSHealth.StatusListener {
      override fun onConfigured(configured: Boolean) {
        sendEvent(
          reactContext,
          "onConfiguredWeFitterSamsung",
          Arguments.createMap().apply { putBoolean("configured", configured) })
      }

      override fun onConnected(connected: Boolean) {
        sendEvent(
          reactContext,
          "onConnectedWeFitterSamsung",
          Arguments.createMap().apply { putBoolean("connected", connected) })
      }

      override fun onError(error: WeFitterSHealthError) {
        // save error so we can find it again for `tryToResolve` function
        errors[error.reason] = error
        sendEvent(
          reactContext,
          "onErrorWeFitterSamsung",
          Arguments.createMap().apply {
            putString("error", error.reason.message)
            putBoolean("forUser", error.reason.forUser)
          })
      }
    }
    val notificationConfig = parseNotificationConfig(config)
    val startDate = parseStartDate(config)
    weFitter.configure(token, apiUrl, statusListener, notificationConfig, startDate)
  }

  @ReactMethod
  fun connect() {
    weFitter.connect()
  }

  @ReactMethod
  fun disconnect() {
    weFitter.disconnect()
  }

  @ReactMethod
  fun isConnected(callback: Callback) {
    callback(weFitter.isConnected())
  }

  @ReactMethod
  fun tryToResolveError(reason: String) {
    // reverse lookup enum by value
    val map = WeFitterSHealthError.Reason.values().associateBy(WeFitterSHealthError.Reason::message)
    val enum = map[reason]

    // try to resolve source error
    errors[enum]?.let {
      weFitter.tryToResolveError(it)
    }
  }

  private fun parseNotificationConfig(config: ReadableMap): WeFitterSHealth.NotificationConfig {
    return WeFitterSHealth.NotificationConfig().apply {
      config.getString("notificationTitle")?.let { title = it }
      config.getString("notificationText")?.let { text = it }
      config.getString("notificationIcon")?.let {
        val resourceId = getResourceId(it)
        if (resourceId != 0) iconResourceId = resourceId
      }
      config.getString("notificationChannelId")?.let { channelId = it }
      config.getString("notificationChannelName")?.let { channelName = it }
    }
  }

  private fun parseStartDate(config: ReadableMap): Date? {
    val startDateString = config.getString("startDate")
    if (startDateString != null) {
      val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
      sdf.timeZone = TimeZone.getTimeZone("UTC")
      return sdf.parse(startDateString)
    }
    return null
  }

  private fun getResourceId(resourceName: String): Int {
    val resources = reactContext.resources
    val packageName = reactContext.packageName
    var resourceId = resources.getIdentifier(resourceName, "mipmap", packageName)
    if (resourceId == 0) {
      resourceId = resources.getIdentifier(resourceName, "drawable", packageName)
    }
    if (resourceId == 0) {
      resourceId = resources.getIdentifier(resourceName, "raw", packageName)
    }
    return resourceId
  }

  private fun sendEvent(reactContext: ReactContext, eventName: String, params: WritableMap?) {
    reactContext
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
      .emit(eventName, params)
  }
}
