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
    Log.i("DEBUG", "permissions $permissions")

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
    Log.i("DEBUG", "callingActivityName $callingActivityName")
    intent.putExtra("CALLING_ACTIVITY", callingActivityName)
    startActivity(intent)

  }

  @RequiresApi(Build.VERSION_CODES.TIRAMISU)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Log.d("DEBUG", "Overridden OnCreate $savedInstanceState")

    // Apparently needed for Android 13 behaviour calling OnCreate twice
    if (savedInstanceState == null) {
      healthConnectPermissionRequest.launch(
        arrayOf(
          android.Manifest.permission.ACTIVITY_RECOGNITION,
          android.Manifest.permission.POST_NOTIFICATIONS
        )
      )
      Log.d("DEBUG", "After healthConnectPermissionRequest")
    }
    Log.d("DEBUG", "OnCreate finished")
  }
}
