package com.example.measuredatacompose

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.health.services.client.HealthServicesClient

class MeasureActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_BODY_SENSORS = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_measure)

        // BODY_SENSORS 권한 확인 및 요청
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.BODY_SENSORS
            ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.BODY_SENSORS),
                PERMISSION_REQUEST_BODY_SENSORS)
        } else {
            // 권한이 이미 허용된 경우 스트레스 지수 가져오기 시작
            startStressIndexFetching()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_BODY_SENSORS) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한이 허용된 경우 스트레스 지수 가져오기 시작
                startStressIndexFetching()
            } else {
                // 권한이 거부된 경우 처리할 로직 추가 가능
            }
        }
    }

    private fun startStressIndexFetching() {
        val client = HealthServicesClient(this)
        val request = Builder().setDataType("stress_index").build()

        client.readData(request).addOnSuccessListener { result ->
            val stressIndex = result.getDouble("stress_index")
            // 서버로 스트레스 지수 전송하는 로직 추가하기
            sendStressIndexToServer(stressIndex)
        }.addOnFailureListener { exception ->
            // 데이터 읽기 실패 시 처리할 로직 추가하기
        }
    }

    private fun sendStressIndexToServer(stressIndex: Double) {
        // 서버로 스트레스 지수를 전송하는 로직을 구현합니다.
        // 이 부분은 앱의 백엔드와 통신하여 데이터를 전송하는 방식에 따라 구현되어야 합니다.
    }
}
