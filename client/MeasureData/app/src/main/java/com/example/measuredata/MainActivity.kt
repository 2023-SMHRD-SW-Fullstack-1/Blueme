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
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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

    private val viewModel: MainViewModel by viewModels()

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


    @SuppressLint("SuspiciousIndentation")
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_login)

        // 측정 데이터 모으기
        var avgHeartRate = mutableListOf<Double>()

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
                            // modified by orthh

                        }
                    }
                    false -> Log.i(TAG, "Body sensors permission not granted")
                }
            }

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
        permissionLauncher.launch(android.Manifest.permission.BODY_SENSORS)
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
