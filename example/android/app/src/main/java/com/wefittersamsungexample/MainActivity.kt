package com.wefittersamsungexample

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.Manifest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.facebook.react.ReactActivity
import com.facebook.react.ReactActivityDelegate
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint.fabricEnabled
import com.facebook.react.defaults.DefaultReactActivityDelegate
import com.wefittersamsungexample.MainRNActivity


class MainActivity : AppCompatActivity() {

  private var ACTIVITY_RECOGNITION: Boolean = false
  private var POST_NOTIFICATIONS: Boolean = false

  private val healthConnectPermissionRequest = this.registerForActivityResult(
    ActivityResultContracts.RequestMultiplePermissions()
  ) { permissions ->
    Log.i(TAG, "permissions $permissions")

    when {
      permissions.getOrDefault(android.Manifest.permission.ACTIVITY_RECOGNITION, false) -> {
        ACTIVITY_RECOGNITION = true
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
    val callingActivityName = MainActivity::class.java.simpleName
    Log.i(TAG, "callingActivityName $callingActivityName")
    intent.putExtra("CALLING_ACTIVITY", callingActivityName)
    startActivity(intent)

  }

  @RequiresApi(Build.VERSION_CODES.TIRAMISU)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Log.d(TAG, "Overridden OnCreate $savedInstanceState  $this")

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

  companion object {
    const val TAG = "MainActivity"
  }

}
