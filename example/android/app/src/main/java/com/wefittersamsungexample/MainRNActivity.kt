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
      Log.d("DEBUG", "Overridden")
      val intent = getIntent()
      val callingActivity = intent.getStringExtra("CALLING_ACTIVITY")
      Log.d("DEBUG", "MainActivity2-onCreate $callingActivity")
      // val callerClass = Class.forName(callingActivity!!, false, classLoader)
      // Log.d("DEBUG", "MainActivity2-onCreate $callerClass")
      Log.d("DEBUG", "MainActivity2-onCreate $intent")
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
      Log.d("DEBUG", "createReactActivityDelegate")
        return DefaultReactActivityDelegate(
            this,
          mainComponentName,  // If you opted-in for the New Architecture, we enable the Fabric Renderer.
            fabricEnabled
        )
    }
}
