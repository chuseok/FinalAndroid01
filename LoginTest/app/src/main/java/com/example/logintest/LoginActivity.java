package com.example.logintest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.logintest.domain.User;
import com.example.logintest.manager.SharedPrefManager;
import com.example.logintest.volley.URLs;
import com.example.logintest.volley.VolleySingleton;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText userId;
    EditText userPwd;
    ProgressBar progressBar;
    CheckBox idCheck;
    Button loginBtn;
    TextView registerTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        /* 자동 로그인 설정...*/
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            startActivity(new Intent(this, SplashActivity.class));
            finish();
        }

        progressBar = findViewById(R.id.progressBar);
        userId = findViewById(R.id.activity_login_et_userId);
        userPwd = findViewById(R.id.activity_login_et_userPwd);
        idCheck = findViewById(R.id.activity_login_cb_idcheck);
        loginBtn = findViewById(R.id.activity_login_bt_loginBtn);
        registerTextView = findViewById(R.id.registerTextView);
        progressBar.setVisibility(View.GONE);

        if(SharedPrefManager.getInstance(this).getUser().isIdCheck()){
            idCheck.setChecked(true);
            userId.setText(SharedPrefManager.getInstance(this).getUser().getUserId());
        }
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerActivity = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(registerActivity);
                finish();
            }
        });
    }

    private void userLogin() {
        final String et_userId = userId.getText().toString();
        final String et_userPwd = userPwd.getText().toString();

        if (TextUtils.isEmpty(et_userId)) {
            userId.setError("아이디를 입력해주세요!");
            userId.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(et_userPwd)) {
            userPwd.setError("비밀번호를 입력해주세요!");
            userPwd.requestFocus();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                if (et_userId.equalsIgnoreCase(array.getJSONObject(i).getString("userId")) &&
                                        et_userPwd.equalsIgnoreCase(array.getJSONObject(i).getString("userPwd"))) {

                                    User user = new User(et_userId, et_userPwd, array.getJSONObject(i).getString("userName"),
                                            array.getJSONObject(i).getString("email"), idCheck.isChecked());
                                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), SplashActivity.class));
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", et_userId);
                params.put("userPwd", et_userPwd);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
