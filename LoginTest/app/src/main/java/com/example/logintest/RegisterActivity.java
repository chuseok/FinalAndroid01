package com.example.logintest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.logintest.Utils.MobileSize;
import com.example.logintest.domain.User;
import com.example.logintest.manager.SharedPrefManager;
import com.example.logintest.volley.URLs;
import com.example.logintest.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    TextView authTextView;
    TextView loginTextView;
    EditText userIdEditText;
    EditText userPwdEditText;
    EditText userPwdConfirmEditText;
    Button nextButton;

    String userId_Val;
    String userPwd_Val;
    String userPwdConfirm_Val;
    boolean Certification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        MobileSize mobileSize = new MobileSize();
        mobileSize.getStandardSize(this);
        float displayXHeight = mobileSize.getStandardSize_X();
        float displayYHeight = mobileSize.getStandardSize_Y();
        float authImageSize = displayYHeight/5;
        authTextView = findViewById(R.id.authTextView);
        loginTextView = findViewById(R.id.loginTextView);
        userIdEditText = findViewById(R.id.ac_register_userId_et);
        userPwdEditText = findViewById(R.id.ac_register_userPwd_et);
        userPwdConfirmEditText = findViewById(R.id.ac_register_userPwd_Confirm_et);
        nextButton = findViewById(R.id.ac_register_next_bt);

        mobileSize.setLayoutHeight(authTextView, (int) authImageSize);
        mobileSize.setLayputMargin(loginTextView, 0, (int) ((displayYHeight-authImageSize) / 20), 0, 0);
        mobileSize.setLayoutHeight(userIdEditText, (int) (displayYHeight-authImageSize) / 10);
        mobileSize.setLayoutHeight(userPwdEditText, (int) (displayYHeight-authImageSize) / 10);
        mobileSize.setLayoutHeight(userPwdConfirmEditText, (int) (displayYHeight-authImageSize) / 10);
        mobileSize.setLayoutHeight(nextButton, (int) (displayYHeight-authImageSize) / 10);
        mobileSize.setLayputMargin(nextButton, 0, (int) ((displayYHeight-authImageSize) / 10) * 2, 0, 0);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if(extras == null) {
            Log.d("EXTRAS", "NULL");
        } else if(extras != null) {
            String userId = intent.getExtras().getString("userId");
            userIdEditText.setText(userId);
            String userPwd = intent.getExtras().getString("userPwd");
            userPwdEditText.setText(userPwd);
        }

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userId_Val = userIdEditText.getText().toString();
                userPwd_Val = userPwdEditText.getText().toString();
                userPwdConfirm_Val = userPwdConfirmEditText.getText().toString();

                Certification = verifyUserInfo(userId_Val, userPwd_Val, userPwdConfirm_Val);

                String registerUrl = URLs.URL_MEMBER_GETLIST + "?userId=" + userId_Val;
                StringRequest stringRequest = new StringRequest(Request.Method.GET, registerUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray memberArray = new JSONArray(response);
                                    boolean idCheck = true;
                                    for (int i = 0; i < memberArray.length(); i++) {
                                        Log.d("RESPONSE_WEB", "response");
                                        Log.d("MEMBER_ID", memberArray.getJSONObject(i).getString("userId"));
                                        if (userId_Val.equalsIgnoreCase(memberArray.getJSONObject(i).getString("userId"))) {
                                            userIdEditText.setError("아이디가 이미 있습니다!");
                                            userIdEditText.requestFocus();
                                            idCheck = false;
                                            return;
                                        }
                                    }

                                    if(idCheck && Certification) {
                                            Intent SecondRegisterActivity = new Intent(getApplicationContext(), RegisterSecondActivity.class);
                                            SecondRegisterActivity.putExtra("userId", userIdEditText.getText().toString());
                                            SecondRegisterActivity.putExtra("userPwd", userPwdEditText.getText().toString());
                                            startActivity(SecondRegisterActivity);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("CONN_ERROR", error.getMessage());
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("userId", userId_Val);
                        return params;
                    }
                };

                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);








            }
        });


        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginActivity);
                finish();
            }
        });


        Toolbar mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mToolbar.setTitle(R.string.register_title);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean verifyUserInfo(String userId, String userPwd, String userPwdConfirm) {
        if(TextUtils.isEmpty(userId)) {
            userIdEditText.setError("아이디를 입력해주세요!");
            userIdEditText.requestFocus();
            return false;
        } else if(TextUtils.isEmpty(userPwd)) {
            userPwdEditText.setError("비밀번호를 입력해주세요!");
            userPwdEditText.requestFocus();
            return false;
        } else if(TextUtils.isEmpty(userPwdConfirm)) {
            userPwdConfirmEditText.setError("비밀번호 확인을 입력해주세요!");
            userPwdConfirmEditText.requestFocus();
            return false;
        } else if(!userPwd.equalsIgnoreCase(userPwdConfirm)) {
            userPwdConfirmEditText.setError("비밀번호가 맞지 않습니다!");
            userPwdConfirmEditText.requestFocus();
            return false;
        } else if(!validateUserId(userId)) {
            userIdEditText.setError("영문,숫자만 입력가능합니다!");
            userIdEditText.requestFocus();
            return false;
        } else if(!validateUserPwd(userPwd)) {
            userPwdEditText.setError("영문+숫자+특수문자 포함 8~16자로 입력해주세요!");
            userPwdEditText.requestFocus();
            return false;
        }

        return true;
    }

    private boolean validateUserId(String userId) {
        boolean idCheck = Pattern.matches("^[a-zA-Z0-9]+$", userId);
        if(!idCheck) {
            return false;
        }
        return true;
    }

    private boolean validateUserPwd(String userPwd) {
        boolean pwdCheck = Pattern.matches("^.*(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", userPwd);
        if(!pwdCheck) {
            return false;
        }
        return true;
    }
}