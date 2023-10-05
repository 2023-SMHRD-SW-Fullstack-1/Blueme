package com.example.exercise;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

// added by orthh 왠진 모르겠지만 kotlin 파일 적용이 안되서java로 생성
// 실제 사용 프래그먼트
public class LoginFragment extends Fragment {

    private EditText etLoginEmail, etLoginPw;
    private Button btnLogin;
    private RequestQueue reqQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etLoginEmail = view.findViewById(R.id.etLoginEmail);
        etLoginPw = view.findViewById(R.id.etLoginPw);
        btnLogin = view.findViewById(R.id.btnLogin);

        reqQueue = Volley.newRequestQueue(requireContext());

        final Context context = requireActivity();

        // SharedPreference 생성
        final SharedPreferences spf = context.getSharedPreferences("mySPF", Context.MODE_PRIVATE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputEmail = etLoginEmail.getText().toString();
                String inputPassword = etLoginPw.getText().toString();

                Log.d("inputEmail", inputEmail);
                Log.d("inputPw", inputPassword);

                JSONObject jsonBody = new JSONObject();

                try {
                    jsonBody.put("email", inputEmail);
                    jsonBody.put("password", inputPassword);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                StringRequest request = new StringRequest(
                        Request.Method.POST,
//                        "http://172.30.1.27:8104/login",
                        "http://3.39.192.60:8104/login",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.d("response", response);

                                if (!response.equals("-1")) {

                                    SharedPreferences.Editor editor = spf.edit();
                                    // editor를 통해 로그인한 회원의 이메일 저장.
                                    editor.putString("userEmail", inputEmail);
                                    editor.commit();

                                    NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.exerciseFragment);  // 로그인 성공 후 Fragment 이동.

                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Log.d("error", error.toString());

                            }
                        }){

                    @Override
                    public String getBodyContentType(){
                        //return "application/json; charset=utf-8";
                        //return "application/x-www-form-urlencoded";
                        return "application/json";
                    }

                    @Override
                    public byte[] getBody(){
                        return jsonBody.toString().getBytes(StandardCharsets.UTF_8);
                    }

                };

                reqQueue.add(request);
            }
        });
    }
}
