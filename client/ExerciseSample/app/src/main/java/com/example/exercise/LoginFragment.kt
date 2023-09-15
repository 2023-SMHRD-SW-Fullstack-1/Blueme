import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.exercise.R
import org.json.JSONObject

// 사용안함
class LoginFragment : Fragment() {

    lateinit var etLoginEmail: EditText
    lateinit var etLoginPw: EditText
    lateinit var btnLogin: Button

    lateinit var reqQueue : RequestQueue



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etLoginEmail = view.findViewById(R.id.etLoginEmail)
        etLoginPw = view.findViewById(R.id.etLoginPw)
        btnLogin = view.findViewById(R.id.btnLogin)

        reqQueue = Volley.newRequestQueue(requireContext())

        // SharedPreference 생성
        val spf = requireActivity().getSharedPreferences("mySPF", Context.MODE_PRIVATE)

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

                    } else{
                        val editor = spf.edit()
                        // editor를 통해 로그인한 회원의 이메일 저장
                        editor.putString("user", inputEmail)
                        editor.commit()
                        findNavController().navigate(R.id.exerciseFragment)  // 로그인 성공 후 Fragment 이동.
                        // MainActivity로 전환 (Intent)joinUser
                        //val it = Intent(this, MainActivity::class.java)
                        //startActivity(it)

                    }
                },
                Response.ErrorListener { error ->
                    Log.d("error", error.toString())
                    if (error.networkResponse != null && error.networkResponse.statusCode == 400) {
                        Log.d("response", "Status Code: ${error.networkResponse.statusCode}")
                        Toast.makeText(requireContext(), "아이디나 비밀번호가 일치하지 않습니다", Toast.LENGTH_LONG).show()
                    } else {
                        Log.d("error", error.toString())
                    }
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
