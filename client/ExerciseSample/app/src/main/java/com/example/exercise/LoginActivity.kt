package com.example.exercise

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import com.google.gson.Gson

// added by orthh
class LoginActivity: AppCompatActivity() {

    lateinit var etLoginEmail : EditText
    lateinit var etLoginPw : EditText
    lateinit var btnLogin : Button

    lateinit var reqQueue : RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etLoginEmail = findViewById(R.id.etLoginEmail)
        etLoginPw = findViewById(R.id.etLoginPw)
        btnLogin = findViewById(R.id.btnLogin)

        reqQueue = Volley.newRequestQueue(this@LoginActivity)


        // SharedPreference 생성
        val spf = getSharedPreferences("mySPF", Context.MODE_PRIVATE)
        val user = spf.getString("user", " ")

        btnLogin.setOnClickListener{
            val inputEmail = etLoginEmail.text.toString()
            val inputPassword = etLoginPw.text.toString()

            Log.d("inputEmail" , inputEmail)
            Log.d("inputPw" , inputPassword)

            // Create a JSONObject with the email and password
            val jsonBody = JSONObject().apply {
                put("email", inputEmail)
                put("password", inputPassword)
            }

            val request = object : StringRequest(
                Method.POST,
                "http://172.30.1.27:8104/user/login",
                Response.Listener<String> { response ->
                    Log.d("response", response)

                    if(response.equals("-1")) {
                        Toast.makeText(this, "아이디나 비밀번호가 일치하지 않습니다", Toast.LENGTH_LONG).show()
                    } else{
                        val editor = spf.edit()
                        // editor를 통해 로그인한 회원의 이메일 저장
                        editor.putString("user", inputEmail) 
                        editor.commit()

                        // MainActivity로 전환 (Intent)joinUser
                        val it = Intent(this, MainActivity::class.java)
                        startActivity(it)
                    }
                },
                Response.ErrorListener { error ->
                    Log.d("error", error.toString())
                    Toast.makeText(this, "에러발생!", Toast.LENGTH_LONG).show()
                }
            ) {
                override fun getBodyContentType(): String {
                    return "application/json; charset=utf-8"
                }

                override fun getBody(): ByteArray? {
                    return jsonBody.toString().toByteArray(Charsets.UTF_8)
                }

            }

            reqQueue.add(request)
        }




    }
}