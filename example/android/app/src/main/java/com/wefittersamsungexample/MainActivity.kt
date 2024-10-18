package com.wefittersamsungexample

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.app.ActivityManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.wefitter.shealth.service.SamsungHealthForegroundService
import com.google.gson.Gson


class NotificationConfig(
  var title: String = "WeFitter for Samsung Health",
  var text: String = "Synchronizing Samsung Health",
  var iconResourceId: Int = com.wefitter.shealth.R.mipmap.ic_notification,
  var channelId: String = "WeFitter for Samsung Health",
  var channelName: String = "WeFitter for Samsung Health",
)


class MainActivity : AppCompatActivity() {

  private var ACTIVITY_RECOGNITION: Boolean = false
  private var POST_NOTIFICATIONS: Boolean = false

  private var fgRunning: Boolean = false

  fun foregroundServiceRunning(): Boolean {
    val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
    for (service in activityManager.getRunningServices(Int.MAX_VALUE)) {
      val serviceName = "com.wefitter.shealth.service.SamsungHealthForegroundService"
      Log.d(TAG, "serviceName $serviceName")
      if (serviceName == service.service.className) {
        return true
      }
    }
    return false
  }

  private val healthConnectPermissionRequest = this.registerForActivityResult(
    ActivityResultContracts.RequestMultiplePermissions()
  ) { permissions ->
    Log.i(TAG, "permissions $permissions")

    when {
      permissions.getOrDefault(android.Manifest.permission.ACTIVITY_RECOGNITION, false) -> {
        ACTIVITY_RECOGNITION = true
        Log.d(TAG, "Start FGS - FG running = $fgRunning")
        if(!fgRunning) startFGS()
      }

      permissions.getOrDefault(android.Manifest.permission.POST_NOTIFICATIONS, false) -> {
        POST_NOTIFICATIONS = true
      }

      else -> {
        // No health connect access granted, service can't be started as it will crash
        Toast.makeText(this, "Health permission is required!", Toast.LENGTH_SHORT).show()
      }
    }

    val intent = Intent(this, MainRNActivity::class.java)
    val fgRunningString = if (fgRunning) "TRUE" else "FALSE"
    intent.putExtra("FOREGROUND_RUNNING", fgRunningString)
    startActivity(intent)
  }

  @RequiresApi(Build.VERSION_CODES.TIRAMISU)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    fgRunning = foregroundServiceRunning()
    Log.d(TAG, "Overridden OnCreate $savedInstanceState $this fgRunning: $fgRunning")

    // Apparently needed for Android 13 behaviour calling OnCreate twice
    if (savedInstanceState == null) {
      healthConnectPermissionRequest.launch(
        arrayOf(
          android.Manifest.permission.ACTIVITY_RECOGNITION,
          android.Manifest.permission.POST_NOTIFICATIONS
        )
      )
      Log.d(TAG, "After healthConnectPermissionRequest")
    }
    Log.d(TAG, "OnCreate finished  $this")
  }

  override fun onStop() {
    Log.d(TAG, "Overridden onStop $this")
    super.onStop()
  }

  override fun onRestart() {
    Log.d(TAG, "Overridden onRestart $this")
    super.onRestart()
  }

  override fun onResume() {
    Log.d(TAG, "Overridden onResume $this")
    super.onResume()
  }

  override fun onDestroy() {
    Log.d(TAG, "Overridden onDestroy $this")
    super.onDestroy()
  }

  fun startFGS() {
    Log.d(TAG, "MainActivity - onStart")
    val fgIntent = Intent(this, SamsungHealthForegroundService::class.java)
    val notificationConfig = NotificationConfig()
    val jsonString: String = Gson().toJson(notificationConfig)
    fgIntent.putExtra("notification", jsonString)
    startForegroundService(fgIntent)
  }

  companion object {
    const val TAG = "MainActivity"
  }

}
