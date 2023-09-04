package com.example.measuredatacompose.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.Scaffold
import com.example.measuredatacompose.R
import com.example.measuredatacompose.theme.MeasureDataTheme
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

// 로그인 화면 추가(orthh)
interface LoginApi {
    @POST("/user/login")
    suspend fun userLogin(@Body body: UserLoginRequest): Response<ResponseBody>
}

data class UserLoginRequest(
    val email: String,
    val password: String
)

private suspend fun sendLoginToServer(email: String, password: String, navController: NavController) {
    //val url = "http://172.30.1.56:8104"
    val url = "http://172.30.1.56:8104"

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

    val service = retrofit.create(LoginApi::class.java)

    val request = UserLoginRequest(email,password)

    try {
        val response = service.userLogin(request)
        if (response.isSuccessful) {
            Log.d("Response", "Data sent successfully")
            val responseBody = response.body()
            if (responseBody != null) {
                val bodyString = responseBody.string()
                Log.d("test", bodyString)
                if(bodyString == "-1"){

                }else{
                    navController.navigate("measure/$email")
                }
            } else {
                Log.d("test", "Response body is null")
            }

        } else {
            Log.e("Response", "Failed to send data")
        }
    } catch(e: Exception) {
        Log.e("Network error", e.toString())
    }
}


//@Preview
@Composable
fun LoginApp(navController: NavController) {
    MeasureDataTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(0.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter= painterResource(R.drawable.blueme_logo),
                    contentDescription=null, // 필수 param
                    modifier = Modifier.size(50.dp)  // 이미지 크기 조절
                )

                val emailState = remember { mutableStateOf("") }
                Row(horizontalArrangement = Arrangement.Center, modifier=Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value=emailState.value,
                        onValueChange={emailState.value=it},
                        label={Text("이메일 입력",color=Color.White)},
                        singleLine=true,
                        textStyle = TextStyle(color = Color.White, fontSize=12.sp),
                        shape=RoundedCornerShape(12.dp),
                        modifier=Modifier.fillMaxWidth(0.8f).height(55.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = Color.White,
                            cursorColor = Color.White,
                            focusedBorderColor= Color.White,
                            unfocusedBorderColor=Color.Gray
                        )

                    )
                }

                Spacer(modifier=Modifier.height(4.dp))

                val passwordState = remember { mutableStateOf("") }
                Row(horizontalArrangement = Arrangement.Center, modifier=Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value=passwordState.value,
                        onValueChange={passwordState.value=it},
                        label={Text("패스워드 입력", color = Color.White)},
                        singleLine=true,
                        textStyle = TextStyle(color = Color.White),
                        shape=RoundedCornerShape(12.dp),
                        modifier=Modifier.fillMaxWidth(0.8f).height(55.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = Color.White,
                            cursorColor = Color.White,
                            focusedBorderColor= Color.White,
                            unfocusedBorderColor=Color.Gray
                        )
                    )
                }


                Spacer(modifier=Modifier.height(4.dp))

                val coroutineScope = rememberCoroutineScope()

                Button(onClick={
                    coroutineScope.launch {
                        sendLoginToServer(emailState.value, passwordState.value, navController)
                    }
                }) {
                    Text("로그인")
                }

            }
        }
    }
}
