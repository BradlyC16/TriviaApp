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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.bradleycockrell.triviaapp.MainActivity.QUESTIONS_LIST;

public class QuizFragment extends Fragment {

    @BindView(R.id.question_textView)
    protected TextView quizQuestion;

    @BindView(R.id.answer_button_1)
    protected Button answerOneButton;
    @BindView(R.id.answer_button_2)
    protected Button answerTwoButton;
    @BindView(R.id.answer_button_3)
    protected Button answerThreeButton;
    @BindView(R.id.answer_button_4)
    protected Button answerFourButton;


    private List<Question> questionList;
    private Question question;
    private int questionListPosition = 0;
    private int correctAnswers = 0;
    private QuizCallback quizCallback;


    @Nullable
    @NonNull
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_take_quiz, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    public static QuizFragment newInstance() {

        Bundle args = new Bundle();

        QuizFragment fragment = new QuizFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();

        questionList = getArguments().getParcelableArrayList(QUESTIONS_LIST);

        populateQuizContent();
    }

    private void populateQuizContent() {
        question = questionList.get(questionListPosition);
        quizQuestion.setText(question.getQuestion());

        List<Button> buttonList = new ArrayList<>();
        buttonList.add(answerOneButton);
        buttonList.add(answerTwoButton);
        buttonList.add(answerThreeButton);
        buttonList.add(answerFourButton);

        List<String> possibleAnswerList = new ArrayList<>();
        possibleAnswerList.add(question.getCorrectAnswer());
        possibleAnswerList.add(question.getWrongAnswerOne());
        possibleAnswerList.add(question.getWrongAnswerTwo());
        possibleAnswerList.add(question.getWrongAnswerThree());


        for (Button button : buttonList) {


            int random = (int) Math.ceil(Math.random() * (possibleAnswerList.size() - 1));

//            Using the random number above we will set the text of the button by getting that item from the possible answers list.
            button.setText(possibleAnswerList.get(random));

            possibleAnswerList.remove(random);
        }
    }

    private void checkAnswer(String answer){
        questionListPosition++;

        if(question.getCorrectAnswer().equals(answer)) {
            quizQuestion.setText("Correct.");
            correctAnswers++;
        } else {
            quizQuestion.setText(getString(R.string.wrong_answer_text, question.getCorrectAnswer()));
        }

    }

    @OnClick(R.id.answer_button_1)
    protected void buttonOneClicked() {

        checkAnswer(answerOneButton.getText().toString());
    }

    @OnClick(R.id.answer_button_2)
    protected void buttonTwoClicked(){

        checkAnswer(answerTwoButton.getText().toString());
    }

    @OnClick(R.id.answer_button_3)
    protected void buttonThreeClicked(){

        checkAnswer(answerThreeButton.getText().toString());
    }

    @OnClick(R.id.answer_button_4)
    protected void buttonFourClicked(){

        checkAnswer(answerFourButton.getText().toString());
    }

    @OnClick(R.id.submit_button)
    protected void buttonSubmitClicked(){

        if(questionListPosition <= questionList.size() -1) {


        } else {
//            Handling no more questions, taking user back to MainActivity
            quizCallback.quizFinished(correctAnswers);
        }
    }

    private void disableAnswerButtons() {
        answerOneButton.setEnabled(false);
        answerTwoButton.setEnabled(false);
        answerThreeButton.setEnabled(false);
        answerFourButton.setEnabled(false);
    }

    private void enableAnswerButtons() {
        answerOneButton.setEnabled(true);
        answerTwoButton.setEnabled(true);
        answerThreeButton.setEnabled(true);
        answerFourButton.setEnabled(true);
    }

    public void attachParent (QuizCallback quizCallback) {

        this.quizCallback = quizCallback;
    }

    public interface QuizCallback {

        void quizFinished(int correctAnswers);
    }
}
