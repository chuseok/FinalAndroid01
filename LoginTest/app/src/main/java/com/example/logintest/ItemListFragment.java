package com.example.logintest;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.logintest.TabFragment.DragonTabFragment;
import com.example.logintest.TabFragment.BackgroundTabFragment;
import com.example.logintest.TabFragment.ShopItemTabFragment;
import com.example.logintest.adapter.TabAdapter;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ItemListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private MainActivity mainContext;
    private int[] tabIcons = {
            R.drawable.ic_dragon_tab,
            R.drawable.ic_background_tab
    };

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ItemListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ItemListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ItemListFragment newInstance(String param1, String param2) {
        ItemListFragment fragment = new ItemListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager viewPager = view.findViewById(R.id.frag_itemList_viewPager);
        FragmentManager fragmentManager = mainContext.getSupportFragmentManager();
        TabAdapter adapter = new TabAdapter(fragmentManager);
        adapter.addFragment(new DragonTabFragment(),"DRAGON");
        adapter.addFragment(new BackgroundTabFragment(),"BACKGROUND");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        return view;
    }
    @Override
    public void onAttach(Activity activity) {
        mainContext=(MainActivity) activity;
        super.onAttach(activity);
    }
}