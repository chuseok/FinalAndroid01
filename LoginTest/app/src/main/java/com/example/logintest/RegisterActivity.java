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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.example.logintest.Utils.MobileSize;
import com.example.logintest.domain.User;
import com.example.logintest.manager.SharedPrefManager;
import com.example.logintest.volley.URLs;
import com.example.logintest.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    TextView authTextView;
    TextView loginTextView;
    EditText userNameEditText;
    EditText userIdEditText;
    EditText userPwdEditText;
    EditText userPwdConfirmEditText;
    Button nextButton;
    RelativeLayout nextLayout;

    String userName_Val;
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
        userNameEditText = findViewById(R.id.ac_register_userName_et);
        userIdEditText = findViewById(R.id.ac_register_userId_et);
        userPwdEditText = findViewById(R.id.ac_register_userPwd_et);
        userPwdConfirmEditText = findViewById(R.id.ac_register_userPwd_Confirm_et);
        nextButton = findViewById(R.id.ac_register_next_bt);
        nextLayout = findViewById(R.id.ac_register_next_layout);

        mobileSize.setLayoutHeight(authTextView, (int) authImageSize);



        mobileSize.setLayoutHeight(loginTextView, (int) (int) (displayYHeight-authImageSize) / 20);

        mobileSize.setLayoutHeight(userNameEditText, (int) (displayYHeight-authImageSize) / 10);
        mobileSize.setLayoutHeight(userIdEditText, (int) (displayYHeight-authImageSize) / 10);
        mobileSize.setLayoutHeight(userPwdEditText, (int) (displayYHeight-authImageSize) / 10);
        mobileSize.setLayoutHeight(userPwdConfirmEditText, (int) (displayYHeight-authImageSize) / 10);
        mobileSize.setLayoutHeight(nextButton, (int) (displayYHeight-authImageSize) / 10);
        mobileSize.setLayoutMargin(nextLayout, 0, 0, 0, (int) (displayYHeight-authImageSize) / 10);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if(extras == null) {
            Log.d("EXTRAS", "NULL");
        } else if(extras != null) {
            String userName = intent.getExtras().getString("userName");
            userNameEditText.setText(userName);
            String userId = intent.getExtras().getString("userId");
            userIdEditText.setText(userId);
            String userPwd = intent.getExtras().getString("userPwd");
            userPwdEditText.setText(userPwd);
        }

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName_Val = userNameEditText.getText().toString();
                userId_Val = userIdEditText.getText().toString();
                userPwd_Val = userPwdEditText.getText().toString();
                userPwdConfirm_Val = userPwdConfirmEditText.getText().toString();

                Certification = verifyUserInfo(userName_Val, userId_Val, userPwd_Val, userPwdConfirm_Val);

                String registerUrl = URLs.URL_MEMBER_GETLIST + "?userId=" + userId_Val + "&userName=" + userName_Val ;
                StringRequest stringRequest = new StringRequest(Request.Method.GET, registerUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {

                                    JSONArray memberArray = new JSONArray(response);
                                    boolean idCheck = true;
                                    boolean nameCheck = true;
                                    for (int i = 0; i < memberArray.length(); i++) {
                                        Log.d("RESPONSE_WEB", "response");
                                        Log.d("MEMBER_NAME", memberArray.getJSONObject(i).getString("userName"));
                                        Log.d("MEMBER_ID", memberArray.getJSONObject(i).getString("userId"));

                                        if (userName_Val.equalsIgnoreCase(memberArray.getJSONObject(i).getString("userName"))) {
                                            userNameEditText.setError("이름이 이미 있습니다!");
                                            userNameEditText.requestFocus();
                                            nameCheck = false;
                                            return;
                                        }
                                        if (userId_Val.equalsIgnoreCase(memberArray.getJSONObject(i).getString("userId"))) {
                                            userIdEditText.setError("아이디가 이미 있습니다!");
                                            userIdEditText.requestFocus();
                                            idCheck = false;
                                            return;
                                        }
                                    }

                                    if(nameCheck && idCheck && Certification) {
                                        Intent SecondRegisterActivity = new Intent(getApplicationContext(), RegisterSecondActivity.class);
                                        SecondRegisterActivity.putExtra("userName", userName_Val);
                                        SecondRegisterActivity.putExtra("userId", userId_Val);
                                        SecondRegisterActivity.putExtra("userPwd", userPwd_Val);
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
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String character = null;
                        try {
                            character = new String(response.data, "UTF-8");
                            return Response.success(character, HttpHeaderParser.parseCacheHeaders(response));
                        } catch (UnsupportedEncodingException e) {
                            return Response.error(new ParseError(e));
                        } catch (Exception e) {
                            // log error
                            return Response.error(new ParseError(e));
                        }
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("userName", userName_Val);
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

    private boolean verifyUserInfo(String userName, String userId, String userPwd, String userPwdConfirm) {
        if(TextUtils.isEmpty(userName)) {
            userNameEditText.setError("이름은 필수입력입니다!");
            userNameEditText.requestFocus();
            return false;
        }
        if(TextUtils.isEmpty(userId)) {
            userIdEditText.setError("아이디는 필수입력입니다!");
            userIdEditText.requestFocus();
            return false;
        } else if(TextUtils.isEmpty(userPwd)) {
            userPwdEditText.setError("비밀번호는 필수입력입니다!");
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
        } else if(!validateUserName(userName)) {
            userNameEditText.setError("한글로만 입력해주세요!");
            userNameEditText.requestFocus();
            return false;



        } else if(!validateUserId(userId)) {
            userIdEditText.setError("영문,숫자만 입력가능합니다!");
            userIdEditText.requestFocus();
            return false;
        } else if(!validateUserPwd(userPwd)) {
            userPwdEditText.setError("영문+숫자+특수문자 포함 8~16자로 입력해주세요!");
            userPwdEditText.requestFocus();
            return false;
        } else if(!checkNameLength(userName)) {





        } else if(!checkIdLength(userId)) {
            userIdEditText.setError("6자이상 12자이하로 입력해주세요!");
            userIdEditText.requestFocus();
            return false;
        } else if(!checkPwdLength(userPwd)) {
            userPwdEditText.setError("8자이상 16자이하로 입력해주세요!");
            userPwdEditText.requestFocus();
            return false;
        }



        return true;
    }

    private boolean validateUserName(String userName) {
        boolean nameCheck = Pattern.matches("^[\\uAC00-\\uD7A3]+$", userName);
        if(!nameCheck) {
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

    private boolean checkNameLength(String userName) {
        boolean nameCheck = false;

        if(userName.length() >=2 && userName.length() <=5) {
            nameCheck = true;
        }
        Log.d("USER_NAME_LENGTH", String.valueOf(userName.length()));
        return nameCheck;
    }

    private boolean checkIdLength(String userId) {
        boolean idCheck = false;

        if(userId.length() >=6 && userId.length() <=12) {
            idCheck = true;
        }
        Log.d("USER_ID_LENGTH", String.valueOf(userId.length()));
        return idCheck;
    }

    private boolean checkPwdLength(String userPwd) {
        boolean pwdCheck = false;

        if(userPwd.length() >=8 && userPwd.length() <=16) {
            pwdCheck = true;
        }
        Log.d("USER_PWD_LENGTH", String.valueOf(userPwd.length()));
        return pwdCheck;
    }
}