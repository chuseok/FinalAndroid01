package com.example.logintest;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.logintest.domain.Word;

import java.util.ArrayList;

/**
 * A fragment representing the back of the card.
 */
public class CardBackFragment extends Fragment {
    private static final String ARG_PARAM1 = "word";
    private static final String ARG_PARAM2 = "position";
    private ArrayList<Word> mParam1;
    private int mParam2;

    boolean showingBack;

    public static CardBackFragment newInstance(ArrayList<Word> param1, int param2) {
        CardBackFragment fragment = new CardBackFragment();
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

        View rootView = inflater.inflate(R.layout.fragment_word_card_back, container, false);

        TextView wordTextView;
        wordTextView = rootView.findViewById(R.id.fragment_word_card_word_back);

        ArrayList<Word> wordArray = getArguments().getParcelableArrayList("word");
        int position = getArguments().getInt("position");

        wordTextView.setText(position+1+"");

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
                .replace(R.id.fragment_word_card_container, CardFrontFragment.newInstance(mParam1, mParam2))
                .addToBackStack(null)

                .commit();
    }
}

