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

package com.example.exercise

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.health.services.client.data.DataPointContainer
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.ExerciseState
import androidx.health.services.client.data.ExerciseUpdate
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.wear.ambient.AmbientModeSupport
import com.example.exercise.databinding.FragmentExerciseBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.Instant
import javax.inject.Inject
import kotlin.math.roundToInt

/**
 * Fragment showing the exercise controls and current exercise metrics.
 */
@AndroidEntryPoint
class ExerciseFragment : Fragment() {

    @Inject
    lateinit var healthServicesManager: HealthServicesManager

    private val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentExerciseBinding? = null
    private val binding get() = _binding!!

    private var serviceConnection = ExerciseServiceConnection()

    private var cachedExerciseState = ExerciseState.ENDED
    private var activeDurationCheckpoint =
        ExerciseUpdate.ActiveDurationCheckpoint(Instant.now(), Duration.ZERO)
    private var chronoTickJob: Job? = null
    private var uiBindingJob: Job? = null

    private lateinit var ambientController: AmbientModeSupport.AmbientController
    private lateinit var ambientModeHandler: AmbientModeHandler

    // 정보 저장용 변수
    private var saveHeartRate = mutableListOf<Int>()
    private var saveSpeed = mutableListOf<Double>()
    private var saveCalorie = 0
    private var saveStep = mutableListOf<Int>()

    // 시작했는지 체크하는 변수
    private var isStart = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startEndButton.setOnClickListener {
            // App could take a perceptible amount of time to transition between states; put button into
            // an intermediary "disabled" state to provide UI feedback.

            // modified by orthh
             it.isEnabled = false
            startEndExercise()
//            it.isEnabled = false
//            viewLifecycleOwner.lifecycleScope.launch {
//                serviceConnection.repeatWhenConnected { exerciseService ->
//                    if (exerciseService.isExerciseInProgress()) {
//                        exerciseService.endExercise()
//                    } else {
//                        startEndExercise()
//                    }
//                }
//            }
        }
        binding.pauseResumeButton.setOnClickListener {
            // App could take a perceptible amount of time to transition between states; put button into
            // an intermediary "disabled" state to provide UI feedback.
            it.isEnabled = false
            pauseResumeExercise()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                val capabilities =
                    healthServicesManager.getExerciseCapabilities() ?: return@repeatOnLifecycle
                val supportedTypes = capabilities.supportedDataTypes

                // Set enabled state for relevant text elements.
                binding.heartRateText.isEnabled = DataType.HEART_RATE_BPM in supportedTypes
                binding.caloriesText.isEnabled = DataType.CALORIES_TOTAL in supportedTypes
                binding.distanceText.isEnabled = DataType.DISTANCE in supportedTypes
                // added by orthh
                binding.speedText.isEnabled = DataType.SPEED in supportedTypes
                binding.stepsText.isEnabled = DataType.STEPS_PER_MINUTE in supportedTypes
                binding.lapsText.isEnabled = true
            }
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.keyPressFlow.collect {
                    healthServicesManager.markLap()
                }
            }
        }

        // Ambient Mode
        ambientModeHandler = AmbientModeHandler()
        ambientController = AmbientModeSupport.attach(requireActivity())
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.ambientEventFlow.collect {
                    ambientModeHandler.onAmbientEvent(it)
                }
            }
        }

        // Bind to our service. Views will only update once we are connected to it.
        ExerciseService.bindService(requireContext().applicationContext, serviceConnection)
        bindViewsToService()

        //시작할떄 이미 시작되어있으면 종료.
        //checkNotNull(serviceConnection.exerciseService) {
          //  "Failed to achieve ExerciseService instance"
       //}.endExercise()

        //viewLifecycleOwner.lifecycleScope.launch {
          //  serviceConnection.repeatWhenConnected { exerciseService ->
            //    exerciseService.endExercise()
            //}
        //}

    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Unbind from the service.
        ExerciseService.unbindService(requireContext().applicationContext, serviceConnection)
        _binding = null
    }
    
    // 커스텀 스타트바
    private fun customStartExcecise(){
        tryStartExercise()
        val animator = ObjectAnimator.ofInt(binding.progressStart, "progress", 0, 100).apply {
            duration = 40000 // 30 seconds in milliseconds
            start()
        }

        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {
                TODO("Not yet implemented")
            }

            override fun onAnimationEnd(p0: Animator) {
                // 일시정지
                //tryPauseExercise()
                pauseResumeExercise()
                // 전송 완료 표시
                Toast.makeText(requireContext(), "데이터 전송완료", Toast.LENGTH_SHORT).show()
                // 서버로 데이터 전송
                // 데이터 확인
                Log.d("평균심박수", saveHeartRate.average().toString())
                Log.d("칼로리", saveCalorie.toString())
                Log.d("평균속도", saveSpeed.average().toString())
                Log.d("걸음수", saveStep.average().toString())

                // 종료하기( 실제 기기로 테스트해보기 팅기나)
//                    checkNotNull(serviceConnection.exerciseService) {
//                        "Failed to achieve ExerciseService instance"
//                    }.endExercise()

                // 프로그래스바 초기화, 상태 초기화
                binding.progressStart.setProgress(0)
                saveHeartRate.clear()
                saveCalorie = 0
                saveSpeed.clear()
                saveStep.clear()
                resetDisplayedFields()
            }

            override fun onAnimationCancel(p0: Animator) {
                TODO("Not yet implemented")
            }

            override fun onAnimationRepeat(p0: Animator) {
                TODO("Not yet implemented")
            }
        })
    }

    // modified by orthh
    private fun startEndExercise() {

        if (cachedExerciseState.isEnded) {
            // 시작 버튼 누를시
            tryStartExercise()

            isStart = true

            // 기본상태 초기화
            binding.progressStart.setProgress(0)
            saveHeartRate.clear()
            saveCalorie = 0
            saveSpeed.clear()
            saveStep.clear()
            resetDisplayedFields()
            // 프로그래스바 증가(일단 30초)
            val animator = ObjectAnimator.ofInt(binding.progressStart, "progress", 0, 100).apply {
                duration = 40000 // 30 seconds in milliseconds
                start()
            }

            animator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator) {
                    TODO("Not yet implemented")
                }

                override fun onAnimationEnd(p0: Animator) {
                    // 일시정지
                    //tryPauseExercise()
                    pauseResumeExercise()
                    // 전송 완료 표시
                    Toast.makeText(requireContext(), "데이터 전송완료", Toast.LENGTH_SHORT).show()
                    // 서버로 데이터 전송
                    // 데이터 확인
                    Log.d("평균심박수", saveHeartRate.average().toString())
                    Log.d("칼로리", saveCalorie.toString())
                    Log.d("평균속도", saveSpeed.average().toString())
                    Log.d("걸음수", saveStep.toString())

                    // 종료하기( 실제 기기로 테스트해보기 팅기나)
//                    checkNotNull(serviceConnection.exerciseService) {
//                        "Failed to achieve ExerciseService instance"
//                    }.endExercise()

                    // 프로그래스바 초기화, 상태 초기화
                    binding.progressStart.setProgress(0)
                    saveHeartRate.clear()
                    saveCalorie = 0
                    saveSpeed.clear()
                    saveStep.clear()
                    resetDisplayedFields()
                    isStart = false
                }

                override fun onAnimationCancel(p0: Animator) {
                    TODO("Not yet implemented")
                }

                override fun onAnimationRepeat(p0: Animator) {
                    TODO("Not yet implemented")
                }
            })
            
            
        } else {
            // 종료
//            checkNotNull(serviceConnection.exerciseService) {
//                "Failed to achieve ExerciseService instance"
//            }.endExercise()
        }
    }

    // modified by orthh
    private fun tryStartExercise() {
        viewLifecycleOwner.lifecycleScope.launch {
            if (healthServicesManager.isTrackingExerciseInAnotherApp()) {
                // Show the user a confirmation screen.
                //findNavController().navigate(R.id.to_newExerciseConfirmation)
            } else if (!healthServicesManager.isExerciseInProgress()) {
                checkNotNull(serviceConnection.exerciseService) {
                    "Failed to achieve ExerciseService instance"
                }.startExercise()
            }
        }
    }

    private fun pauseResumeExercise() {
        val service = checkNotNull(serviceConnection.exerciseService) {
            "Failed to achieve ExerciseService instance"
        }
        if (cachedExerciseState.isPaused) {
            service.resumeExercise()
        } else {
            service.pauseExercise()
        }
    }

    private fun bindViewsToService() {
        if (uiBindingJob != null) return

        uiBindingJob = viewLifecycleOwner.lifecycleScope.launch {
            serviceConnection.repeatWhenConnected { service ->
                // Use separate launch blocks because each .collect executes indefinitely.
                launch {
                    service.exerciseState.collect {
                        updateExerciseStatus(it)
                    }
                }
                launch {
                    service.latestMetrics.collect {
                        it?.let { updateMetrics(it) }
                    }
                }
                launch {
                    service.exerciseLaps.collect {
                        updateLaps(it)
                    }
                }
                launch {
                    service.activeDurationCheckpoint.collect {
                        // We don't update the chronometer here since these updates come at irregular
                        // intervals. Instead we store the duration and update the chronometer with
                        // our own regularly-timed intervals.
                        activeDurationCheckpoint = it
                    }
                }
            }
        }
    }

    private fun unbindViewsFromService() {
        uiBindingJob?.cancel()
        uiBindingJob = null
    }

    private fun updateExerciseStatus(state: ExerciseState) {
        val previousStatus = cachedExerciseState
        if (previousStatus.isEnded && !state.isEnded) {
            // We're starting a new exercise. Clear metrics from any prior exercise.
            resetDisplayedFields()
        }

        if (state == ExerciseState.ACTIVE && !ambientController.isAmbient) {
            startChronometer()
        } else {
            stopChronometer()
        }

        updateButtons(state)
        cachedExerciseState = state
    }

    private fun updateButtons(state: ExerciseState) {
        binding.startEndButton.setText(if (state.isEnded) R.string.start else R.string.end)
        binding.startEndButton.isEnabled = true
        binding.pauseResumeButton.setText(if (state.isPaused) R.string.resume else R.string.pause)
        binding.pauseResumeButton.isEnabled = !state.isEnded
    }

    private fun updateMetrics(latestMetrics: DataPointContainer) {
        latestMetrics.getData(DataType.HEART_RATE_BPM).let {
            if (it.isNotEmpty()) {
                if(isStart && it.last().value.roundToInt() != 0){
                    saveHeartRate.add(it.last().value.roundToInt())
                }
                binding.heartRateText.text = it.last().value.roundToInt().toString()
            }
        }
        latestMetrics.getData(DataType.DISTANCE_TOTAL)?.let {
            binding.distanceText.text = formatDistanceKm(it.total)
        }
        latestMetrics.getData(DataType.CALORIES_TOTAL)?.let {
            binding.caloriesText.text = formatCalories(it.total)
            if(isStart){
                saveCalorie = it.total.roundToInt()
            }
        }
        // added by orthh
        latestMetrics.getData(DataType.SPEED).let{
            if (it.isNotEmpty()) {
                val speedInKmPerHour = it.last().value * 3.6
                Log.d("speed-it = ", speedInKmPerHour.toString())
                binding.speedText.text = formatSpeed(speedInKmPerHour.toString())
//                Log.d("speed-it = ", it.last().value.toString())
//                binding.speedText.text = it.last().value.toString()
                //binding.speedText.text = it.last().value.roundToInt().toString()
                if((it.last().value*3.6) != 0.0){
                    saveSpeed.add(it.last().value*3.6)
                }
            }
        }
        latestMetrics.getData(DataType.STEPS_PER_MINUTE).let{
            if (isStart && it.isNotEmpty()) {
                Log.d("step-it = ", it.last().value.toString())
                binding.stepsText.text = formatSteps(it.last().value.toString())
                //binding.speedText.text = it.last().value.roundToInt().toString()
                if(it.last().value.toInt() != 0){
                    saveStep.add(it.last().value.toInt())
                }
            }
        }
    }

    private fun updateLaps(laps: Int) {
        binding.lapsText.text = laps.toString()
    }

    private fun startChronometer() {
        if (chronoTickJob == null) {
            chronoTickJob = viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    while (true) {
                        delay(CHRONO_TICK_MS)
                        updateChronometer()
                    }
                }
            }
        }
    }

    private fun stopChronometer() {
        chronoTickJob?.cancel()
        chronoTickJob = null
    }

    private fun updateChronometer() {
        // We update the chronometer on our own regular intervals independent of the exercise
        // duration value received. If the exercise is still active, add the difference between
        // the last duration update and now.
        val duration = activeDurationCheckpoint.displayDuration(Instant.now(), cachedExerciseState)
        binding.elapsedTime.text = formatElapsedTime(duration, !ambientController.isAmbient)
    }

    private fun resetDisplayedFields() {
        getString(R.string.empty_metric).let {
            binding.heartRateText.text = it
            binding.caloriesText.text = it
            binding.distanceText.text = it
            binding.lapsText.text = it
            // added by orthh
            binding.speedText.text = it
            binding.stepsText.text = it
        }
        binding.elapsedTime.text = formatElapsedTime(Duration.ZERO, true)
    }

    // -- Ambient Mode support

    private fun setAmbientUiState(isAmbient: Boolean) {
        // Change icons to white while in ambient mode.
        val iconTint = if (isAmbient) {
            Color.WHITE
        } else {
            resources.getColor(R.color.primary_orange, null)
        }
        ColorStateList.valueOf(iconTint).let {
            binding.clockIcon.imageTintList = it
            binding.heartRateIcon.imageTintList = it
            binding.caloriesIcon.imageTintList = it
            binding.distanceIcon.imageTintList = it
            binding.lapsIcon.imageTintList = it
        }

        // Hide the buttons in ambient mode.
        val buttonVisibility = if (isAmbient) View.INVISIBLE else View.VISIBLE
        buttonVisibility.let {
            binding.startEndButton.visibility = it
            binding.pauseResumeButton.visibility = it
        }
    }

    private fun performOneTimeUiUpdate() {
        val service = checkNotNull(serviceConnection.exerciseService) {
            "Failed to achieve ExerciseService instance"
        }
        updateExerciseStatus(service.exerciseState.value)
        updateLaps(service.exerciseLaps.value)

        service.latestMetrics.value?.let { updateMetrics(it) }

        activeDurationCheckpoint = service.activeDurationCheckpoint.value
        updateChronometer()
    }

    inner class AmbientModeHandler {
        internal fun onAmbientEvent(event: AmbientEvent) {
            when (event) {
                is AmbientEvent.Enter -> onEnterAmbient()
                is AmbientEvent.Exit -> onExitAmbient()
                is AmbientEvent.Update -> onUpdateAmbient()
            }
        }

        private fun onEnterAmbient() {
            // Note: Apps should also handle low-bit ambient and burn-in protection.
            unbindViewsFromService()
            setAmbientUiState(true)
            performOneTimeUiUpdate()
        }

        private fun onExitAmbient() {
            performOneTimeUiUpdate()
            setAmbientUiState(false)
            bindViewsToService()
        }

        private fun onUpdateAmbient() {
            performOneTimeUiUpdate()
        }
    }

    private companion object {
        const val CHRONO_TICK_MS = 200L
    }
}
