package com.example.logintest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.logintest.Utils.MobileSize;

public class RegisterSecondActivity extends AppCompatActivity {

    private static final int REQUEST_BACK = 1;

    TextView authTextView;
    EditText emailEditText;
    EditText phoneEditText;
    EditText authNumEditText;
    LinearLayout phoneLayout;
    LinearLayout authLayout;
    Button signUpButton;

    String userId;
    String userPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_second);

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
        signUpButton = findViewById(R.id.ac_register_sign_up_bt);

        mobileSize.setLayoutHeight(authTextView, (int) authImageSize);
        mobileSize.setLayoutHeight(emailEditText, (int) (displayYHeight-authImageSize) / 10);
        mobileSize.setLayoutHeight(phoneLayout, (int) (displayYHeight-authImageSize) / 10);
        mobileSize.setLayoutHeight(authLayout, (int) (displayYHeight-authImageSize) / 10);
        mobileSize.setLayoutHeight(signUpButton, (int) (displayYHeight-authImageSize) / 10);
        mobileSize.setLayputMargin(signUpButton, 0, (int) ((displayYHeight-authImageSize) / 10) * 2, 0, 0);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.ac_res_two_main_toolbar);
        mToolbar.setTitle(R.string.register_title);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        userId = intent.getExtras().getString("userId");
        userPwd = intent.getExtras().getString("userPwd");
        String userEmail = emailEditText.getText().toString();
        String userPhone = phoneEditText.getText().toString();
        String userAuthNum = authNumEditText.getText().toString();
        System.out.println("userId : " + userId);
        System.out.println("userPwd : " + userPwd);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), R.string.register_sign_up_complete, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                Intent registerActivity = new Intent(this, RegisterActivity.class);
                registerActivity.putExtra("userId", userId);
                registerActivity.putExtra("userPwd", userPwd);
                startActivity(registerActivity);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}