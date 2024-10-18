package com.wefittersamsungexample

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.facebook.react.ReactActivity
import com.facebook.react.ReactActivityDelegate

class MainRNActivity : ReactActivity() {

    private var fgRunningString: String = "FALSE"

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      Log.d(TAG, "Overridden")
      val intent = getIntent()
      val fgRunning: String? = intent.getStringExtra("FOREGROUND_RUNNING")
      if (fgRunning != null) fgRunningString = fgRunning
      Log.d(TAG, "MainActivity2-onCreate $intent $fgRunning")
    }

      /**
     * Returns the name of the main component registered from JavaScript. This is used to schedule
     * rendering of the component.
     */
    override fun getMainComponentName(): String {
        return "WeFitterSamsungExample"
    }

    /**
     * Returns the instance of the [ReactActivityDelegate]. Here we use a util class [ ] which allows you to easily enable Fabric and Concurrent React
     * (aka React 18) with two boolean flags.
     */
    override fun createReactActivityDelegate(): ReactActivityDelegate {
      Log.d(TAG, "createReactActivityDelegate - fgRunning $fgRunningString")
      return object : ReactActivityDelegate(
        this,
        mainComponentName,  // If you opted-in for the New Architecture, we enable the Fabric Renderer.
      ) {
        override fun getLaunchOptions(): Bundle {
          val initialProperties = Bundle().apply { putString("fgRunning", fgRunningString) }
          return initialProperties
        }
      }
    }

  override fun onStop() {
      Log.d(TAG, "Overridden onStop")
      super.onStop()
    }

    override fun onRestart() {
      Log.d(TAG, "Overridden onStop")
      super.onRestart()
    }

    override fun onResume() {
      Log.d(TAG, "Overridden onResume")
      super.onResume()
    }

    override fun onDestroy() {
      Log.d(TAG, "Overridden onDestroy")
      super.onDestroy()
    }

    companion object {
      const val TAG = "MainRNActivity"
    }

  }
