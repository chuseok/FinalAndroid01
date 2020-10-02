package com.example.logintest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
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
import com.example.logintest.manager.SharedPrefManager;
import com.example.logintest.receiver.AlarmReceiver;
import com.example.logintest.receiver.DeviceBootReceiver;
import com.example.logintest.volley.URLs;
import com.example.logintest.volley.VolleySingleton;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MAIN_ACTIVITY";

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private NavigationView leftNavigationView;
    private NavigationView rightNavigationView;
    FragmentManager fragmentManager;
    private TextView notificationText;
    int notificationCount = 0;

    //알람


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        ArrayList<String> wordIdArray = intent.getStringArrayListExtra("wordId");
        ArrayList<String> wordTitleArray = intent.getStringArrayListExtra("wordTitle");
        ArrayList<String> learningRateArray = intent.getStringArrayListExtra("learningRate");

        Log.d(TAG, "wordTitleArray : " + wordTitleArray.size());

        fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final MainFragment mainFragment = MainFragment.newInstance(wordTitleArray, learningRateArray,wordIdArray);
        fragmentTransaction.replace(R.id.frag_nav, mainFragment);
        fragmentTransaction.commit();

        toolbar = findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.Open, R.string.Close);
        drawer.addDrawerListener(toggle);//hamburger icon create
        toggle.syncState();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        leftNavigationView = (NavigationView)findViewById(R.id.activity_main_nav_view_left);
        leftNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {

                    case R.id.home:
                        final FragmentTransaction mainFt = fragmentManager.beginTransaction();
                        mainFt.replace(R.id.frag_nav,mainFragment);
                        mainFt.commit(); break;
                    case R.id.dragonList:
                        final FragmentTransaction dragonListFt = fragmentManager.beginTransaction();
                        dragonListFt.replace(R.id.frag_nav,new DragonListFragment());
                        dragonListFt.commit(); break;
                    case R.id.itemList:
                        final FragmentTransaction itemListFt = fragmentManager.beginTransaction();
                        itemListFt.replace(R.id.frag_nav,new ItemListFragment());
                        itemListFt.commit(); break;
                    case R.id.shop:
                        final FragmentTransaction shopFt = fragmentManager.beginTransaction();
                        shopFt.replace(R.id.frag_nav,new ShopFragment());
                        shopFt.commit(); break;
                    default:
                        return true;
                }

                drawer.closeDrawer(GravityCompat.START);
                return true;

            }
        });

        rightNavigationView = findViewById(R.id.activity_main_nav_view_right);

        final Menu m = rightNavigationView.getMenu();
        final SubMenu alertMenu = m.addSubMenu("알림");


        final String userId = SharedPrefManager.getInstance(getApplicationContext()).getUser().getUserId();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_MEMBER_LASTCONN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            String lastConn = object.getString("lastConn");

                            String connectionAlert = object.getString("connectionAlert");
                            String hungryAlert = object.getString("hungryAlert");
                            alertMenu.add(connectionAlert).setCheckable(true);
                            //m.add(R.id.nav_right_group,0,Menu.NONE,connectionAlert).setCheckable(true);
                            notificationCount++;
                            if(hungryAlert!="null" || hungryAlert!=null){
                                alertMenu.add(hungryAlert).setCheckable(true);
                                //m.add(R.id.nav_right_group,1,Menu.NONE,hungryAlert).setCheckable(true);
                                notificationCount++;
                            }

                            //시간계산
                            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            int hour = transFormat.parse(lastConn).getHours();
                            int min = transFormat.parse(lastConn).getMinutes();

                            setupBadge();

                            settingNofitication(hour,min);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                params.put("userId", userId);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);



        rightNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                System.out.println(item.isChecked());
                for(int i=0;i<alertMenu.size();i++){
                    MenuItem m = alertMenu.getItem(i);
                    m.setChecked(true);
                }

                if(notificationCount>0){
                    notificationCount=0;
                    notificationText.setText(notificationCount+"");
                }

                drawer.closeDrawer(GravityCompat.END);
                return true;
            }
        });




        Button setting = findViewById(R.id.nav_left_setting_bt);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
            }
        });

        //navigation header
        View headerView = leftNavigationView.getHeaderView(0);
        TextView userId_nav = (TextView)headerView.findViewById(R.id.nav_left_userId_tv);
        TextView email_nav = (TextView)headerView.findViewById(R.id.nav_left_email_tv);
        userId_nav.setText(SharedPrefManager.getInstance(getApplicationContext()).getUser().getUserId());
        email_nav.setText(SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail());



        //알림




    }
    void settingNofitication(int hour, int min){
        SharedPreferences sharedPreferences = getSharedPreferences("daily alarm", MODE_PRIVATE);
        long millis = sharedPreferences.getLong("nextNotifyTime", Calendar.getInstance().getTimeInMillis());

        Calendar nextNotifyTime = new GregorianCalendar();
        nextNotifyTime.setTimeInMillis(millis);

        Date nextDate = nextNotifyTime.getTime();
        String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(nextDate);
        Toast.makeText(getApplicationContext(),"[처음 실행시] 다음 알람은 " + date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();



        // 현재 지정된 시간으로 알람 시간 설정
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 0);

        // 이미 지난 시간을 지정했다면 1일뒤 설정
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        Date currentDateTime = calendar.getTime();
        date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(currentDateTime);
        Toast.makeText(getApplicationContext(),date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();

        //  Preference에 설정한 값 저장
        SharedPreferences.Editor editor = getSharedPreferences("daily alarm", MODE_PRIVATE).edit();
        editor.putLong("nextNotifyTime", (long)calendar.getTimeInMillis());
        editor.apply();


        diaryNotification(calendar);
    }


    void diaryNotification(Calendar calendar)
    {
//        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
//        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
//        Boolean dailyNotify = sharedPref.getBoolean(SettingsActivity.KEY_PREF_DAILY_NOTIFICATION, true);
        Boolean dailyNotify = true; // 무조건 알람을 사용

        PackageManager pm = this.getPackageManager();
        ComponentName receiver = new ComponentName(this, DeviceBootReceiver.class);
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);


        // 사용자가 매일 알람을 허용했다면
        if (dailyNotify) {


            if (alarmManager != null) {

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        10*1000, pendingIntent);
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            }

            // 부팅 후 실행되는 리시버 사용가능하게 설정
            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);

        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_openRight) {
            drawer.openDrawer(GravityCompat.END);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed(){//뒤로가기 클릭시 hamburger 열려있으면 닫히게 하기
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {  /*Closes the Appropriate Drawer*/
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//toolbar에 버튼 추가
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.app_bar_ic_notification, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_openRight);
        View actionView = menuItem.getActionView();
        notificationText = actionView.findViewById(R.id.notification_badge);
        
        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;
    }
    @Override
    protected void onDestroy() {//세션 데이터 삭제
        super.onDestroy();
        SharedPrefManager session = SharedPrefManager.getInstance(getApplicationContext());
        session.destorySession();
    }
    public void setActionBarTitle(String title){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle(title);
        }
    }
    private void setupBadge() {

        if (notificationText != null) {
            if (notificationCount == 0) {
                if (notificationText.getVisibility() != View.GONE) {
                    notificationText.setVisibility(View.GONE);
                }
            } else {
                notificationText.setText(String.valueOf(Math.min(notificationCount, 99)));
                if (notificationText.getVisibility() != View.VISIBLE) {
                    notificationText.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}