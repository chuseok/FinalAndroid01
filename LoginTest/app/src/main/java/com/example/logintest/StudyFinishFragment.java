package com.example.logintest;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.logintest.Utils.MobileSize;
import com.example.logintest.domain.Model;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudyFinishFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudyFinishFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "model";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Model mParam1;

    private LinearLayout layout;
    private TextView titleTextView;
    private Button restartButton;
    private Button endButton;

    public StudyFinishFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment StudyFinishFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudyFinishFragment newInstance(Model param1) {
        StudyFinishFragment fragment = new StudyFinishFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelable(ARG_PARAM1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_study_finish, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        layout = view.findViewById(R.id.fragment_study_finish_layout);
        titleTextView = view.findViewById(R.id.fragment_study_finish_title);
        restartButton = view.findViewById(R.id.fragment_study_finish_restart_bt);
        endButton = view.findViewById(R.id.fragment_study_finish_end_bt);

        MobileSize mobileSize = new MobileSize();
        mobileSize.getStandardSize(getActivity());
        float displayXHeight = mobileSize.getStandardSize_X();
        float displayYHeight = mobileSize.getStandardSize_Y();

        mobileSize.setLayoutMargin(layout, (int)displayXHeight/20, (int)displayYHeight/10, (int)displayXHeight/20, (int)displayYHeight/10);
        mobileSize.setLayoutMargin(titleTextView, 0,0, 0, (int)displayYHeight/20);

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent WordCardActivity = new Intent(getContext(), WordCardActivity.class);
                WordCardActivity.putExtra("userId", mParam1.getId());
                WordCardActivity.putExtra("bookTitle", mParam1.getTitle());
                startActivity(WordCardActivity);
                getActivity().finish();

            }
        });
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }
}