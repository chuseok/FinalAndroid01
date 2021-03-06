package com.example.logintest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.logintest.adapter.CardViewAdapter;
import com.example.logintest.domain.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "title";
    private static final String ARG_PARAM2 = "learningRate";
    private static final String ARG_PARAM3 = "id";

    ViewPager viewPager;
    CardViewAdapter adapter;
    List<Model> models;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(ArrayList<String> param1, ArrayList<String> param2, ArrayList<String> param3) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_PARAM1, param1);
        args.putStringArrayList(ARG_PARAM2, param2);
        args.putStringArrayList(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_main,null);

        ArrayList<String> titleArray = getArguments().getStringArrayList("title");
        ArrayList<String> learningRateArray = getArguments().getStringArrayList("learningRate");
        ArrayList<String> idArray = getArguments().getStringArrayList("id");

        models = new ArrayList<>();

        for(int i=0; i<titleArray.size(); i++) {
            models.add(new Model(titleArray.get(i),Integer.parseInt(learningRateArray.get(i)), idArray.get(i)));

        }

        adapter = new CardViewAdapter(models,mainView.getContext());
        viewPager = mainView.findViewById(R.id.activity_main_viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130,0,130,0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return mainView;
    }

    @Override
    public void onResume(){
        super.onResume();
        FragmentActivity activity = getActivity();
        if(activity!=null){
            ((MainActivity)activity).setActionBarTitle("암기용");
        }
    }
}