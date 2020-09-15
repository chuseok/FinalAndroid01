package com.example.logintest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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
import com.example.logintest.Utils.ViewPagerIndicatorView;
import com.example.logintest.adapter.DragonCardViewAdapter;
import com.example.logintest.adapter.InventoryCardViewAdapter;
import com.example.logintest.domain.Dragon;
import com.example.logintest.domain.Inven;
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
    ViewPagerIndicatorView indicatorView;
    List<Inven> invenList;
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
        indicatorView = findViewById(R.id.indicator);
        invenList= new ArrayList<Inven>();
        MobileSize mobileSize = new MobileSize();
        mobileSize.getStandardSize(this);
        float displayYHeight = mobileSize.getStandardSize_Y();
        mobileSize.setLayoutParams(dragonPanel,(int)displayYHeight/10*6);

        String userId = SharedPrefManager.getInstance(getApplicationContext()).getUser().getUserId();

        //dragon value 값 받아오기
        String url = URLs.URL_DRAGON_GET+"?userId="+ userId +"&dragonId="+getIntent().getIntExtra("dragon",0);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            int levelValue = Integer.parseInt(object.getString("dragonLevelValue"));
                            int dragonLevel = Integer.parseInt(object.getString("dragonTotalLevel"));
                            int hungryValue = Integer.parseInt(object.getString("hungryValue"));
                            int coin = Integer.parseInt(object.getString("coin"));
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
                //params.put("userId", "111111");
                //params.put("dragonId", "3");
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        //dragon value 값 받아오기 end

        //inventory list 값 받아오기
        StringRequest invenStringRequest = new StringRequest(Request.Method.GET, URLs.URL_INVEN_LIST+"?userId="+userId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                String imagePath = array.getJSONObject(i).getString("productImage").replace("../",URLs.ROOT_URL);
                                int count = Integer.parseInt(array.getJSONObject(i).getString("count"));
                                invenList.add(new Inven(imagePath,count));
                                adapter.notifyDataSetChanged();

                            }
                            int iv_count = invenList.size()-2;
                            if(iv_count<0){
                                iv_count=invenList.size();
                            }

                            indicatorView.init(iv_count, R.drawable.default_dot, R.drawable.selected_dot,15);
                            indicatorView.setSelection(0);
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
                //params.put("userId", "111111");
                //params.put("dragonId", "3");
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(invenStringRequest);
        //inventory list 값 받아오기 end

        viewPager = findViewById(R.id.ac_dragonDetail_viewPager);
        adapter = new InventoryCardViewAdapter(invenList,getApplicationContext());
        viewPager.setAdapter(adapter);
        viewPager.setPadding(10,0,10,0);



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {

                indicatorView.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        indicatorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(view.getId());
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}