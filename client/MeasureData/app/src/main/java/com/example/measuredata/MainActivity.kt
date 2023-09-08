/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.measuredata

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.measuredata.databinding.ActivityLoginBinding
import com.example.measuredata.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import org.json.JSONObject

/**
 * Activity displaying the app UI. Notably, this binds data from [MainViewModel] to views on screen,
 * and performs the permission check when enabling measure data.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    //private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    private val viewModel: MainViewModel by viewModels()

    private lateinit var btnSend : Button



    // added by orthh
    private fun sendAverageToServer(averageHeartRate: Double) {
        val url = "http://172.30.1.27:8104/health/heartrate"
        val tempUserId = 1

        val requestQueue = Volley.newRequestQueue(this)

        val jsonBody = JSONObject()
        jsonBody.put("userId", tempUserId)
        jsonBody.put("heart_rate", averageHeartRate)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonBody,
            Response.Listener { response ->
                Log.d("Volley", "Response: $response")
            },
            Response.ErrorListener { error ->
                Log.e("Volley", "Error: ${error.localizedMessage}")
            }
        )

        requestQueue.add(jsonObjectRequest)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_login)

        btnSend = findViewById(R.id.btnSend)

        // permissin Check
        btnSend.setOnClickListener {
            val permission = android.Manifest.permission.BODY_SENSORS
            val permission2 = android.Manifest.permission.ACTIVITY_RECOGNITION
            val permission3 = android.Manifest.permission.ACCESS_FINE_LOCATION
            val permission4 = android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
            val grant = ContextCompat.checkSelfPermission(this, permission)
            val grant2 = ContextCompat.checkSelfPermission(this, permission2)
            val grant3 = ContextCompat.checkSelfPermission(this, permission3)
            val grant4 = ContextCompat.checkSelfPermission(this, permission4)

            if (grant == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted, you can perform your operation here.
                Log.d("permissionCheck", "BODY_SENSORS permission granted!")
            } else {
                // Permission is not granted. Request for the permission.
                Log.d("permissionCheck", "BODY_SENSORS permission not granted ㅠㅠ")
            }
            if (grant2 == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted, you can perform your operation here.
                Log.d("permissionCheck", "ACTIVITY_RECOGNITION permission granted!")
            } else {
                // Permission is not granted. Request for the permission.
                Log.d("permissionCheck", "ACTIVITY_RECOGNITION permission not granted ㅠㅠ")
            }
            if (grant3 == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted, you can perform your operation here.
                Log.d("permissionCheck", "ACCESS_FINE_LOCATION permission granted!")
            } else {
                // Permission is not granted. Request for the permission.
                Log.d("permissionCheck", "ACCESS_FINE_LOCATION permission not granted ㅠㅠ")
            }
            if (grant4 == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted, you can perform your operation here.
                Log.d("permissionCheck", "ACCESS_BACKGROUND_LOCATION permission granted!")
            } else {
                // Permission is not granted. Request for the permission.
                Log.d("permissionCheck", "ACCESS_BACKGROUND_LOCATION permission not granted ㅠㅠ")
            }
        }


        // 측정 데이터 모으기
        var avgHeartRate = mutableListOf<Double>()

        /*permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            // This callback is called when the user responds to the permission request dialog.
            permissions.entries.forEach {
                if (it.value) {
                    Log.i(TAG, "Body sensors permission granted")

                } else {
                    Log.i(TAG, "Body sensors permission not granted")
                }

                // Only measure while the activity is at least in STARTED state.
                // MeasureClient provides frequent updates, which requires increasing the
                // sampling rate of device sensors, so we must be careful not to remain
                // registered any longer than necessary.
            }
            lifecycleScope.launchWhenStarted {
                viewModel.measureHeartRate()
                // modified by orthh

            }
        }*/

        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
                when (result) {
                    true -> {
                        Log.i(TAG, "Body sensors permission granted")
                        // Only measure while the activity is at least in STARTED state.
                        // MeasureClient provides frequent updates, which requires increasing the
                        // sampling rate of device sensors, so we must be careful not to remain
                        // registered any longer than necessary.
                        lifecycleScope.launchWhenStarted {
                            viewModel.measureHeartRate()
                            viewModel.measureSpeedRate()
                            // modified by orthh

                        }
                    }
                    false -> Log.i(TAG, "Body sensors permission not granted")
                }
            }

        /*permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())  { result ->
                if (result[android.Manifest.permission.BODY_SENSORS] == true) {
                    Log.d("bodysensor", "bodysensor 권한있음")
                } else {
                    Log.d("bodysensor", "bodysensor 권한없음")
                }
                if(result[android.Manifest.permission.ACTIVITY_RECOGNITION] == true) {
                    Log.d("ACTIVITY_RECOGNITION", "ACTIVITY_RECOGNITION 권한있음")
                } else {
                    Log.d("ACTIVITY_RECOGNITION", "ACTIVITY_RECOGNITION 권한없음")
                }
                if(result[android.Manifest.permission.ACCESS_FINE_LOCATION] == true){
                    Log.d("ACCESS_FINE_LOCATION", "ACCESS_FINE_LOCATION 권한있음")
                }else {
                    Log.d("ACCESS_FINE_LOCATION", "ACCESS_FINE_LOCATION 권한없음")
                }
                if(result[android.Manifest.permission.ACCESS_BACKGROUND_LOCATION] == true){
                    Log.d("ACCESS_BACKGROUND_LOCATION", "ACCESS_BACKGROUND_LOCATION 권한있음")
                }else{
                    Log.d("ACCESS_BACKGROUND_LOCATION", "ACCESS_BACKGROUND_LOCATION 권한없음")
                }
            }*/

        // Bind viewmodel state to the UI.
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                updateViewVisiblity(it)
            }
        }
        // modify : orthh
        lifecycleScope.launchWhenStarted {
            viewModel.heartRateAvailable.collect {
                Log.d("test", getString(R.string.measure_status, it))
                if(getString(R.string.measure_status, it) == "Status: UNKNOWN"){
                    binding.statusText.text = "알수없는 오류"
                }else if(getString(R.string.measure_status, it) == "Status: AVAILABLE"){
                    binding.statusText.text = "측정중..."
                }else if(getString(R.string.measure_status, it) == "Status: ACQUIRING"){
                    binding.statusText.text = "설정중..."
                }else if(getString(R.string.measure_status, it) == "Status: UNAVAILABLE"){
                    binding.statusText.text = "Unavailable"
                }else if(getString(R.string.measure_status, it) == "Status: UNAVAILABLE_DEVICE_OFF_BODY"){
                    binding.statusText.text = "신체에 접촉해주세요"
                }else{
                    binding.statusText.text = "test"
                }
                //binding.statusText.text = getString(R.string.measure_status, it)
            }
        }

        // modified by orthh
        lifecycleScope.launchWhenStarted {

            viewModel.heartRateBpm.collect {
		        binding.lastMeasuredValue.text = String.format("%.1f", it)
                if(it != 0.0){
                    avgHeartRate.add(it)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        /*permissionLauncher.launch(arrayOf(android.Manifest.permission.BODY_SENSORS
            , android.Manifest.permission.ACCESS_FINE_LOCATION
            , android.Manifest.permission.ACTIVITY_RECOGNITION
            , android.Manifest.permission.ACCESS_BACKGROUND_LOCATION))*/
        permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        permissionLauncher.launch(android.Manifest.permission.BODY_SENSORS)
        permissionLauncher.launch(android.Manifest.permission.ACTIVITY_RECOGNITION)

        permissionLauncher.launch(android.Manifest.permission.ACCESS_BACKGROUND_LOCATION)

        /*permissionLauncher.launch(arrayOf(
            android.Manifest.permission.BODY_SENSORS,
            android.Manifest.permission.ACTIVITY_RECOGNITION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
        ))*/
    }

    private fun updateViewVisiblity(uiState: UiState) {
        (uiState is UiState.Startup).let {
            binding.progress.isVisible = it
        }
        // These views are visible when heart rate capability is not available.
        (uiState is UiState.HeartRateNotAvailable).let {
            binding.brokenHeart.isVisible = it
            binding.notAvailable.isVisible = it
        }
        // These views are visible when the capability is available.
        // modify : orthh
        (uiState is UiState.HeartRateAvailable).let {
            binding.statusText.isVisible = it
            //binding.lastMeasuredLabel.isVisible = it
            binding.lastMeasuredValue.isVisible = it
            binding.heart.isVisible = it
        }
    }


}
