package com.example.logintest;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.logintest.domain.Model;
import com.example.logintest.domain.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WordCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WordCardFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "word";
    private static final String ARG_PARAM2 = "position";


    // TODO: Rename and change types of parameters
    private ArrayList<Word> mParam1;
    private int mParam2;

    private TextView wordTextView;

    FragmentManager fragmentManager;

    public WordCardFragment() {
        // Required empty public constructor
    }

    public static WordCardFragment newInstance(ArrayList<Word> param1, int param2) {
        WordCardFragment fragment = new WordCardFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelableArrayList(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
        }

        if (savedInstanceState == null) {
            getChildFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_word_card_container, CardFrontFragment.newInstance(mParam1, mParam2))
                    .commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_word_card, container, false);


        return rootView;
    }

/*
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        wordTextView = view.findViewById(R.id.fragment_word_card_word_front);

        ArrayList<Word> wordArray = getArguments().getParcelableArrayList("word");
        int position = getArguments().getInt("position");

        wordTextView.setText(wordArray.get(position).getWord());

    }
*/

}


