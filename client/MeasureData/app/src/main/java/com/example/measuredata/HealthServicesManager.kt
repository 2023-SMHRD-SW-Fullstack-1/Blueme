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

import android.util.Log
import androidx.concurrent.futures.await
import androidx.health.services.client.HealthServicesClient
import androidx.health.services.client.MeasureCallback
import androidx.health.services.client.data.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

/**
 * Entry point for [HealthServicesClient] APIs, wrapping them in coroutine-friendly APIs.
 */

// modified by orthh
class HealthServicesManager @Inject constructor(
    healthServicesClient: HealthServicesClient
) {
    // measureClient : 기기에서 건강 데이터를 측정하는 방법을 제공하는 클라이언트
    private val measureClient = healthServicesClient.measureClient


    suspend fun hasHeartRateCapability(): Boolean {
        // 이 기기가 사용 가능한 종류 반환
        val capabilities = measureClient.getCapabilitiesAsync().await()
        Log.d("fff", capabilities.supportedDataTypesMeasure.toString())
        return (DataType.HEART_RATE_BPM in capabilities.supportedDataTypesMeasure)
    }
    suspend fun hasSpeedCapability(): Boolean {
        val capabilities = measureClient.getCapabilitiesAsync().await()
        return (DataType.SPEED in capabilities.supportedDataTypesMeasure)
    }

    /**
     * Returns a cold flow. When activated, the flow will register a callback for heart rate data
     * and start to emit messages. When the consuming coroutine is cancelled, the measure callback
     * is unregistered.
     *
     * [callbackFlow] is used to bridge between a callback-based API and Kotlin flows.
     */

    // modified by orthh
    @ExperimentalCoroutinesApi
    fun heartRateMeasureFlow() = callbackFlow {
        val callback = object : MeasureCallback {
            override fun onAvailabilityChanged(dataType: DeltaDataType<*, *>, availability: Availability) {
                if (availability is DataTypeAvailability) {
                    trySendBlocking(MeasureMessage.MeasureAvailability(availability))
                }
            }

            override fun onDataReceived(data: DataPointContainer) {
                val heartRateBpm = data.getData(DataType.HEART_RATE_BPM)
                if (heartRateBpm.isNotEmpty()) {
                    trySendBlocking(MeasureMessage.MeasureData(heartRateBpm))
                }
            }
        }

        Log.d(TAG, "Registering for heart rate data")
        measureClient.registerMeasureCallback(DataType.HEART_RATE_BPM, callback)

        awaitClose {
            Log.d(TAG, "Unregistering for heart rate data")
            runBlocking { measureClient.unregisterMeasureCallbackAsync(DataType.HEART_RATE_BPM, callback) }
        }
    }


    @ExperimentalCoroutinesApi
    fun speedMeasureFlow() = callbackFlow {
        val callback = object : MeasureCallback {
            override fun onAvailabilityChanged(dataType: DeltaDataType<*, *>, availability2: Availability) {
                if (availability2 is DataTypeAvailability) {
                    trySendBlocking(MeasureMessage.SpeedAvailability(availability2))
                }
            }

            override fun onDataReceived(data: DataPointContainer) {
                val speedData = data.getData(DataType.SPEED)
                if (speedData.isNotEmpty()) {
                    trySendBlocking(MeasureMessage.SpeedData(speedData))
                }
            }
        }

        Log.d(TAG, "Registering for speed data")
        measureClient.registerMeasureCallback(DataType.SPEED, callback)

        awaitClose {
            Log.d(TAG, "Unregistering for speed data")
            runBlocking { measureClient.unregisterMeasureCallbackAsync(DataType.SPEED, callback) }
        }
    }


    // added by orthh
    // 속도 가져오는 flow
    /*@ExperimentalCoroutinesApi
    fun speedMeasureFlow() = callbackFlow {
        val callback = object : MeasureCallback {
            override fun onAvailabilityChanged(dataType: DeltaDataType<*, *>, availability: Availability) {
                if (availability is DataTypeAvailability) {
                    trySendBlocking(MeaseureMessageSpeed.MeasureAvailabilitySpeed(availability))
                }
            }

            override fun onDataReceived(data: DataPointContainer) {
                val speedData = data.getData(DataType.SPEED)
                trySendBlocking(MeaseureMessageSpeed.MeasureDataSpeed(speedData))
            }
        }

        Log.d(TAG, "Registering for data")
        measureClient.registerMeasureCallback(DataType.SPEED, callback)

        awaitClose {
            Log.d(TAG, "Unregistering for data")
            runBlocking {
                measureClient.unregisterMeasureCallbackAsync(DataType.SPEED, callback)
            }
        }
    }*/


}

sealed class MeasureMessage {
    class MeasureAvailability(val availability: DataTypeAvailability) : MeasureMessage()
    class MeasureData(val data: List<SampleDataPoint<Double>>): MeasureMessage()
    // added by orthh
    class SpeedAvailability(val availability2: DataTypeAvailability) : MeasureMessage()
    class SpeedData(val data2: List<SampleDataPoint<Double>>): MeasureMessage()

}
/*
// added by orthh
sealed class MeaseureMessageSpeed{
    // added by orthh
    class MeasureAvailabilitySpeed(val availability: DataTypeAvailability) : MeasureMessage()
    class MeasureDataSpeed(val data: List<SampleDataPoint<Double>>): MeasureMessage()
}
*/