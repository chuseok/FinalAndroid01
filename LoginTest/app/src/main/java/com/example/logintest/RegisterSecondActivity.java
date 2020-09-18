package com.example.logintest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RegisterSecondActivity extends AppCompatActivity {

    private static final int REQUEST_BACK = 1;

    TextView authTextView;
    EditText emailEditText;
    EditText phoneEditText;
    EditText authNumEditText;
    LinearLayout phoneLayout;
    LinearLayout authLayout;
    Button phoneConfirmButton;
    Button authConfirmButton;
    LinearLayout dayLayout;
    TextView birthTextView;
    DatePicker datePicker;
    RelativeLayout signUpLayout;
    Button signUpButton;

    String userName;
    String userId;
    String userPwd;
    FirebaseAuth firebaseAuth;
    String mVerificationId;
    PhoneAuthProvider.ForceResendingToken mResendToken;

    String userEmail_Val;
    String userPhone_Val;
    String authNum_Val;
    String userBirth_Val;

    boolean Certification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_second);

        firebaseAuth = FirebaseAuth.getInstance();

        //ONCODESENT

        MobileSize mobileSize = new MobileSize();
        mobileSize.getStandardSize(this);
        float displayXHeight = mobileSize.getStandardSize_X();
        float displayYHeight = mobileSize.getStandardSize_Y();
        float authImageSize = displayYHeight/5;

        authTextView = findViewById(R.id.authTextView);
        emailEditText = findViewById(R.id.ac_register_email_et);
        phoneEditText = findViewById(R.id.ac_register_phone_et);
        authNumEditText = findViewById(R.id.ac_register_auth_et);
        phoneLayout = findViewById(R.id.ac_register_phone_layout);
        authLayout = findViewById(R.id.ac_register_auth_layout);
        phoneConfirmButton = findViewById(R.id.ac_register_phone_confirm_bt);
        authConfirmButton = findViewById(R.id.ac_register_auth_confirm_bt);
        dayLayout = findViewById(R.id.ac_register_day_layout);
        birthTextView = findViewById(R.id.ac_register_birth);
        datePicker = findViewById(R.id.ac_register_date_picker);
        signUpLayout = findViewById(R.id.ac_register_sign_up_layout);
        signUpButton = findViewById(R.id.ac_register_sign_up_bt);

        mobileSize.setLayoutHeight(authTextView, (int) authImageSize);
        mobileSize.setLayoutHeight(emailEditText, (int) (displayYHeight-authImageSize) / 10);
        mobileSize.setLayoutHeight(phoneLayout, (int) (displayYHeight-authImageSize) / 10);
        mobileSize.setLayoutHeight(authLayout, (int) (displayYHeight-authImageSize) / 10);
        mobileSize.setLayoutHeight(dayLayout, (int) ((displayYHeight-authImageSize) / 10) * 4);
        mobileSize.setLayoutHeight(birthTextView, (int) ((int) ((displayYHeight-authImageSize) / 10) * 1.5));
        mobileSize.setLayoutWidth(birthTextView, (int) (displayXHeight) / 5);
        mobileSize.setLayoutHeight(datePicker, (int) ((int) ((displayYHeight-authImageSize) / 10) * 1.5));
        mobileSize.setLayoutWidth(datePicker, (int) ((displayXHeight) / 5) * 4);
        mobileSize.setLayoutHeight(signUpButton, (int) (displayYHeight-authImageSize) / 10);
        mobileSize.setLayoutHeight(signUpLayout, (int) (displayYHeight-authImageSize) / 10);
        mobileSize.setLayoutMargin(signUpLayout, 0, 0, 0, (int) ((int) ((displayYHeight-authImageSize) / 10) + (displayYHeight-authImageSize) /20));

        Toolbar mToolbar = (Toolbar) findViewById(R.id.ac_res_two_main_toolbar);
        mToolbar.setTitle(R.string.register_title);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        userName = intent.getExtras().getString("userName");
        userId = intent.getExtras().getString("userId");
        userPwd = intent.getExtras().getString("userPwd");
        String userEmail = emailEditText.getText().toString();

        System.out.println("userName : " + userName);
        System.out.println("userId : " + userId);
        System.out.println("userPwd : " + userPwd);

//        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
//                new DatePicker.OnDateChangedListener() {
//
//                    @Override
//                    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
//
//                    }
//                });
        phoneConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerificationCodeToUser("+821032315052");
            }
        });

        authConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String userPhone = phoneEditText.getText().toString().substring(1, 11);
//                System.out.println("userPhone : " + userPhone);
                String userAuthNum = authNumEditText.getText().toString();

                Log.d("userAuthNum", "userAuthNum : " + userAuthNum);
//                verifyPhoneNumberWithCode(userAuthNum);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userEmail_Val = emailEditText.getText().toString();
                userPhone_Val = phoneEditText.getText().toString();
                authNum_Val = authNumEditText.getText().toString();

                Calendar cal = Calendar.getInstance();
                cal.set(datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(("yyyy-MM-dd HH:mm:ss"));
                userBirth_Val = simpleDateFormat.format(cal.getTime());

                Certification = verifyUserInfo(userEmail_Val, userPhone_Val, authNum_Val, userBirth_Val);



                StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_MEMBER_SIGNUP,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    if(response != null && response.length() >0) {
                                        JSONObject resultObj = new JSONObject(response);
                                        Log.d("INSERT_RESULT", "insertResult : " + resultObj.getString("insertResult"));
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
                        Log.d("USER_NAME", "userName : " + userName);
                        Log.d("userId", "userId : " + userId);
                        Log.d("userPwd", "userPwd : " + userPwd);
                        Log.d("userEmail", "userEmail : " + userEmail_Val);
                        Log.d("userPhone", "userPhone : " + userPhone_Val);
                        Log.d("userBirth", "userBirth : " + userBirth_Val);

                        params.put("userName", userName);
                        params.put("userId", userId);
                        params.put("userPwd", userPwd);
                        params.put("userEmail", userEmail_Val);
                        params.put("userPhone", userPhone_Val);
                        params.put("userBirth", String.valueOf(userBirth_Val));
                        return params;
                    }
                };

                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

                Toast.makeText(getApplicationContext(), R.string.register_sign_up_complete, Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                Intent registerActivity = new Intent(this, RegisterActivity.class);
                registerActivity.putExtra("userName", userName);
                registerActivity.putExtra("userId", userId);
                registerActivity.putExtra("userPwd", userPwd);
                startActivity(registerActivity);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendVerificationCodeToUser(String phoneNo) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNo,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,   // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    Log.d("ONVerificationCompleted", "onVerificationCompleted:" + phoneAuthCredential.getSmsCode());

                    String code = phoneAuthCredential.getSmsCode();

                    if(code != null) {

                        Log.d("code", "code : " + code);
                        Log.d("VERIFICATION", "mVerificationId : " + mVerificationId);
                        Log.d("RESEND_TOKEN", "mResendToken : " + mResendToken);

                    }

                    signInWithPhoneAuthCredential(phoneAuthCredential);

                    // 확인 완료
                }



                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Log.w("ONVerificationFailed", "onVerificationFailed", e);

                    //확인 실패
                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                        // Invalid request
                        // ...
                    } else if (e instanceof FirebaseTooManyRequestsException) {
                        // The SMS quota for the project has been exceeded
                        // ...
                    }

                    // Show a message and update the UI
                    // ...
                }

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    Log.d("ONCODESENT", "onCodeSent : " + s);
                    Log.d("forceResendingToken", "forceResendingToken:" + forceResendingToken);

                    mVerificationId = s;
                    mResendToken = forceResendingToken;

                    //코드 전송
                }


    };

    private void verifyPhoneNumberWithCode(String code) {
        // [START verify_with_code]

        //코드 확인
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }

    //자격 증명으로 사용자 로그인인
   private void signInWithPhoneAuthCredential(final PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "signInWithCredential:success", Toast.LENGTH_SHORT).show();
                        } else {
                            // Sign in failed, display a message and update the UI
                            Toast.makeText(getApplicationContext(), "signInWithCredential:failure", Toast.LENGTH_SHORT).show();

                            Log.w("signInWithCredential:failure", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    private boolean verifyUserInfo(String email, String phoneNum, String authNum, String birth) {
        if(TextUtils.isEmpty(email)) {
            emailEditText.setError("이메일은 필수입력입니다!");
            emailEditText.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("이메일 형식이 아닙니다!");
            emailEditText.requestFocus();
            return false;
        }

        return true;
    }
}