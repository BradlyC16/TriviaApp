package com.example.bradleycockrell.triviaapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TakeQuiz extends Fragment {

    @BindView(R.id.question_editText)
    protected TextView quizQuestion;

    @BindView(R.id.answer_button_1)
    protected Button answerOneButton;
    @BindView(R.id.answer_button_2)
    protected Button answerTwoButton;
    @BindView(R.id.answer_button_3)
    protected Button answerThreeButton;
    @BindView(R.id.answer_button_4)
    protected Button answerFourButton;

    @Nullable
    @NonNull
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_take_quiz, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    public static TakeQuiz newInstance() {

        Bundle args = new Bundle();

        TakeQuiz fragment = new TakeQuiz();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.answer_button_1)
    protected void buttonOneClicked() {

    }

    @OnClick(R.id.answer_button_2)
    protected void buttonTwoClicked(){

    }

    @OnClick(R.id.answer_button_3)
    protected void buttonThreeClicked(){

    }

    @OnClick(R.id.answer_button_4)
    protected void buttonFourClicked(){

    }

    @OnClick(R.id.submit_button)
    protected void buttonSubmitClicked(){

    }
}

