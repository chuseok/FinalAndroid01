package com.example.logintest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dinuscxj.progressbar.CircleProgressBar;
import com.example.logintest.Utils.MobileSize;
import com.example.logintest.adapter.DragonCardViewAdapter;
import com.example.logintest.adapter.InventoryCardViewAdapter;
import com.example.logintest.domain.Dragon;
import com.example.logintest.domain.Model;
import com.example.logintest.manager.SharedPrefManager;
import com.example.logintest.volley.URLs;
import com.example.logintest.volley.VolleySingleton;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DragonDetailActivity extends AppCompatActivity {

    TextView levelText;
    ImageView dragonImage;
    ProgressBar hungryProgress;
    CircleProgressBar circleProgressBar;
    LinearLayout dragonPanel;
    ViewPager viewPager;
    InventoryCardViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dragon_detail);
        final Toolbar toolbar = findViewById(R.id.ac_dragonDetail_toolbar);

        toolbar.setTitle("암기용");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//헤더 back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        levelText = findViewById(R.id.ac_dragonDetail_Level_tv);
        dragonImage = findViewById(R.id.ac_dragonDetail_dragonImage_iv);
        circleProgressBar = findViewById(R.id.cpb_circlebar);
        hungryProgress = findViewById(R.id.ac_dragonDetail_hungry_pb);
        dragonPanel = findViewById(R.id.ac_dragonDetail_dragonPanel_ll);
        MobileSize mobileSize = new MobileSize();
        mobileSize.getStandardSize(this);
        float displayYHeight = mobileSize.getStandardSize_Y();
        mobileSize.setLayoutParams(dragonPanel,(int)displayYHeight/10*6);

        String url = URLs.URL_DRAGON_GET+"?userId="+ SharedPrefManager.getInstance(getApplicationContext()).getUser().getUserId()+"&dragonId="+getIntent().getIntExtra("dragon",0);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            int levelValue = Integer.parseInt(object.getString("dragonLevelValue"));
                            int dragonLevel = Integer.parseInt(object.getString("dragonTotalLevel"));
                            int hungryValue = Integer.parseInt(object.getString("hungryValue"));
                            circleProgressBar.setProgress(levelValue);
                            levelText.setText(dragonLevel+"");
                            hungryProgress.setProgress(hungryValue);
                            String replaceUrl = object.getString("dragonImage").replace("../",URLs.ROOT_URL);
                            GlideToVectorYou
                                    .init()
                                    .with(getApplicationContext())
                                    .withListener(new GlideToVectorYouListener() {
                                        @Override
                                        public void onLoadFailed() {
                                            System.out.println("Image Failed");
                                        }

                                        @Override
                                        public void onResourceReady() {
                                            System.out.println("Image Ready");
                                        }
                                    })
                                    .load(Uri.parse(replaceUrl), dragonImage);

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
                params.put("userId", "111111");
                params.put("dragonId", "3");
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        viewPager = findViewById(R.id.ac_dragonDetail_viewPager);
        List<Model> test = new ArrayList<Model>();
        test.add(new Model("aaaa","aaaaa"));
        test.add(new Model("aaaa","aaaaa"));
        test.add(new Model("aaaa","aaaaa"));
        test.add(new Model("aaaa","aaaaa"));
        adapter = new InventoryCardViewAdapter(test,getApplicationContext());
        viewPager.setAdapter(adapter);
        viewPager.setPadding(10,0,10,0);
        TabLayout tabLayout = findViewById(R.id.ac_dragonDetail_tabDots);
        tabLayout.setupWithViewPager(viewPager,true);
        
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}