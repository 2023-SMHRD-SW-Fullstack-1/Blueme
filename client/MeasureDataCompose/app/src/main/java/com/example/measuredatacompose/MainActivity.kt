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
package com.example.measuredatacompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.measuredatacompose.presentation.LoginApp
import com.example.measuredatacompose.presentation.MeasureDataApp
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val healthServicesRepository = (application as MainApplication).healthServicesRepository

//        setContent {
//            //MeasureDataApp(healthServicesRepository = healthServicesRepository)
//            LoginApp()
//        }

        // 추가(orthh)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "login") {
                composable("login") { LoginApp(navController) }
                composable("measure/{email}", arguments = listOf(navArgument("email") { type = NavType.StringType })) { backStackEntry ->
                    MeasureDataApp(healthServicesRepository, backStackEntry.arguments?.getString("email"))
                }
            }
        }
    }
}
