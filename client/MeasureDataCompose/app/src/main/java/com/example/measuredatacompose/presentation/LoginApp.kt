package com.example.measuredatacompose.presentation

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Scaffold
import com.example.measuredatacompose.R
import com.example.measuredatacompose.theme.MeasureDataTheme

@Preview
@Composable
fun LoginApp() {
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
                    modifier = Modifier.size(80.dp)  // 이미지 크기 조절
                )

                val emailState = remember { mutableStateOf("") }
                Row(horizontalArrangement = Arrangement.Center, modifier=Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value=emailState.value,
                        onValueChange={emailState.value=it},
                        label={Text("이메일 입력",color=Color.White)},
                        singleLine=true,
                        textStyle = TextStyle(color = Color.White),
                        shape=RoundedCornerShape(12.dp),
                        modifier=Modifier.fillMaxWidth(0.8f).height(20.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = Color.White,
                            cursorColor = Color.White,
                            focusedBorderColor= Color.White,
                            unfocusedBorderColor=Color.Gray
                        )

                    )
                }

                Spacer(modifier=Modifier.height(16.dp))

                val passwordState = remember { mutableStateOf("") }
                Row(horizontalArrangement = Arrangement.Center, modifier=Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value=passwordState.value,
                        onValueChange={passwordState.value=it},
                        label={Text("패스워드 입력")},
                        singleLine=true,
                        textStyle = TextStyle(color = Color.White),
                        shape=RoundedCornerShape(12.dp),
                        modifier=Modifier.fillMaxWidth(0.8f).height(20.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = Color.White,
                            cursorColor = Color.White,
                            focusedBorderColor= Color.White,
                            unfocusedBorderColor=Color.Gray
                        )
                    )
                }


                Spacer(modifier=Modifier.height(16.dp))

                Button(onClick={}){
                    Text("로그인")
                }
            }
        }
    }
}
