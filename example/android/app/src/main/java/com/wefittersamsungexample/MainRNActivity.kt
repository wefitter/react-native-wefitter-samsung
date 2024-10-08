package com.wefittersamsungexample

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.facebook.react.ReactActivity
import com.facebook.react.ReactActivityDelegate
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint.fabricEnabled
import com.facebook.react.defaults.DefaultReactActivityDelegate


  class MainRNActivity : ReactActivity()  {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      Log.d(TAG, "Overridden")
      val intent = getIntent()
      val callingActivity = intent.getStringExtra("CALLING_ACTIVITY")
      Log.d(TAG, "MainActivity2-onCreate $callingActivity")
      // val callerClass = Class.forName(callingActivity!!, false, classLoader)
      // Log.d("DEBUG", "MainActivity2-onCreate $callerClass")
      Log.d(TAG, "MainActivity2-onCreate $intent")
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
      Log.d(TAG, "createReactActivityDelegate")
        return DefaultReactActivityDelegate(
            this,
          mainComponentName,  // If you opted-in for the New Architecture, we enable the Fabric Renderer.
            fabricEnabled
        )
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
