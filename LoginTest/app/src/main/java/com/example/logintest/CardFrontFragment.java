/*
package com.example.logintest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.logintest.domain.Word;

import java.util.ArrayList;

public class CardFrontFragment extends Fragment {

    private static final String ARG_PARAM1 = "word";
    private static final String ARG_PARAM2 = "position";

    private ArrayList<Word> mParam1;
    private int mParam2;

    boolean showingBack;

    public static CardFrontFragment newInstance(ArrayList<Word> param1, int param2) {
        CardFrontFragment fragment = new CardFrontFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelableArrayList(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_word_card_front, container, false);

        TextView wordTextView;
        wordTextView = rootView.findViewById(R.id.fragment_word_card_word_front);

        ArrayList<Word> wordArray = getArguments().getParcelableArrayList("word");
        int position = getArguments().getInt("position");

        wordTextView.setText(wordArray.get(position).getWord());

        wordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard();
            }
        });



        return rootView;
    }

    private void flipCard() {
        if (showingBack) {
            getActivity().getSupportFragmentManager().popBackStack();
            return;
        }
        showingBack = true;

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
            R.animator.card_flip_right_in,
            R.animator.card_flip_right_out,
            R.animator.card_flip_left_in,
            R.animator.card_flip_left_out)
                .replace(R.id.fragment_word_card_container, CardBackFragment.newInstance(mParam1, mParam2))
            .addToBackStack(null)

                .commit();
}
}*/
