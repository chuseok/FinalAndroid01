package com.example.logintest;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        progressBar = findViewById(R.id.progressBar);
        userId = findViewById(R.id.userId);
        userPwd = findViewById(R.id.userPwd);
        loginBtn = findViewById(R.id.loginButton);
        progressBar.setVisibility(View.GONE);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
    }

    private void userLogin() {
        final String userId_editText = userId.getText().toString();
        final String userPwd_editText = userPwd.getText().toString();

        if (TextUtils.isEmpty(userId_editText)) {
            userId.setError("아이디를 입력해주세요!");
            userId.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(userPwd_editText)) {
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
                                System.out.println(array.getJSONObject(i).getString("userId"));
                                if (userId_editText.equalsIgnoreCase(array.getJSONObject(i).getString("userId")) &&
                                        userPwd_editText.equalsIgnoreCase(array.getJSONObject(i).getString("userPwd"))) {
                                    User user = new User(userId_editText, userPwd_editText);
                                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
                params.put("userId", userId_editText);
                params.put("userPwd", userPwd_editText);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
