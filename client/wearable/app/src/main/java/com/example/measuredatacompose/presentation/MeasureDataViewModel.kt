/*
 * Copyright 2022 The Android Open Source Project
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
package com.example.measuredatacompose.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.health.services.client.data.DataTypeAvailability
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.measuredatacompose.data.HealthServicesRepository
import com.example.measuredatacompose.data.MeasureMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

class MeasureDataViewModel(
    private val healthServicesRepository: HealthServicesRepository
) : ViewModel() {
    val enabled: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val hr: MutableState<Double> = mutableStateOf(0.0)
    val availability: MutableState<DataTypeAvailability> =
        mutableStateOf(DataTypeAvailability.UNKNOWN)

    val uiState: MutableState<UiState> = mutableStateOf(UiState.Startup)

    interface HealthServiceApi {
        @POST("/health/hearthrate")
        suspend fun sendHeartRate(@Body body: HeartRateRequest): Response<ResponseBody>
    }

    data class HeartRateRequest(
        val user_id: String,
        val heart_rate: Double
    )
    // 서버로 보내는 코드 추가 (orthh)
    private suspend fun sendAverageToServer(averageHeartRate: Double) {
        val tempUserId = "smhrd"
        val url = "http://172.30.1.56:8104"

        // 테[스트용

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS) // 30초로 변경
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
        // Retrofit 객체 생성
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // API 인터페이스 생성
        val service = retrofit.create(HealthServiceApi::class.java)

        // 요청 본문 생성
        val request = HeartRateRequest(tempUserId, averageHeartRate)

        // 서버에 POST 요청 보내기 (비동기)
        try {
            val response = service.sendHeartRate(request)
            if (response.isSuccessful) {
                Log.d("Response", "Data sent successfully")
            } else {
                Log.e("Response", "Failed to send data")
            }
        } catch(e: Exception) {
            Log.e("Network error", e.toString())
        }

    }


    init {
        viewModelScope.launch {
            val supported = healthServicesRepository.hasHeartRateCapability()
            uiState.value = if (supported) {
                UiState.Supported
            } else {
                UiState.NotSupported
            }
        }

        // 평균 심박수측정후 서버로 보내기로 수정(orthh)
        viewModelScope.launch {
            val heartRates = mutableListOf<Double>() // 심박수 저장용 리스트
            enabled.collect {
                if (it) {
                    healthServicesRepository.heartRateMeasureFlow()
                        .takeWhile { enabled.value }
                        .collect { measureMessage ->
                            when (measureMessage) {
                                is MeasureMessage.MeasureData -> {
                                    hr.value = measureMessage.data.last().value
                                    Log.d("test333", hr.value.toString())
                                    if(hr.value != 0.0){
                                        heartRates.add(hr.value) // 측정값 저장
                                    }
                                }
                                is MeasureMessage.MeasureAvailability -> {
                                    availability.value = measureMessage.availability
                                }

                                else -> {}
                            }
                        }
                    // 측정 종료 후 평균 계산 및 서버 전송
                    val averageHeartRate = heartRates.average()
                    sendAverageToServer(averageHeartRate)
                    Log.d("test", averageHeartRate.toString())
                }
            }
        }

    }

    fun toggleEnabled() {
        enabled.value = !enabled.value
        if (!enabled.value) {
            availability.value = DataTypeAvailability.UNKNOWN
        }
    }
}

class MeasureDataViewModelFactory(
    private val healthServicesRepository: HealthServicesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MeasureDataViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MeasureDataViewModel(
                healthServicesRepository = healthServicesRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

sealed class UiState {
    object Startup : UiState()
    object NotSupported : UiState()
    object Supported : UiState()
}
