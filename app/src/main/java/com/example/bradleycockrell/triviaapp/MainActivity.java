package com.example.bradleycockrell.triviaapp;

import android.content.DialogInterface;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements QuestionCreatorFragment.Callback, QuizFragment.QuizCallback {

    private QuestionCreatorFragment questionCreatorFragment;
    private QuizFragment quizFragment;
    private List<Question> questionList;

    public static final String QUESTIONS_LIST = "questions_list";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        questionList = new ArrayList<>();

    }


    @OnClick(R.id.add_question_button)
    protected void addQuestionClicked() {

        questionCreatorFragment = QuestionCreatorFragment.newInstance();
        questionCreatorFragment.attachParent(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, questionCreatorFragment).commit();

    }

    @Override
    public void questionSaved(Question question) {

        questionList.add(question);

        Toast.makeText(this, "Question Saved.", Toast.LENGTH_SHORT).show();

        getSupportFragmentManager().beginTransaction().remove(questionCreatorFragment).commit();

    }

    @OnClick(R.id.take_quiz_button)
    protected void takeQuizClicked() {


        if (questionList.isEmpty()) {
            Toast.makeText(this, "You must create some questions first.", Toast.LENGTH_SHORT).show();

        } else {
//            Launch fragment, pass in ParcelableArray
            quizFragment = QuizFragment.newInstance();
            quizFragment.attachParent(this);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, quizFragment).commit();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(QUESTIONS_LIST, (ArrayList<? extends Parcelable>) questionList);
            quizFragment.setArguments(bundle);
        }
    }


    @Override
    public void quizFinished(int correctAnswers) {

        getSupportFragmentManager().beginTransaction().remove(quizFragment).commit();

        showQuizResultsAlertDialog(correctAnswers);
    }

    private void showQuizResultsAlertDialog(int correctAnswers) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quiz Finished.")
                .setMessage(getString(R.string.number_of_correct_answers, correctAnswers))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }


    @OnClick(R.id.delete_quiz_button)
    protected void deleteQuizClicked() {

        if(questionList.isEmpty()) {
            Toast.makeText(this, "There's no quiz to be deleted.", Toast.LENGTH_SHORT).show();

        } else {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Quiz")
                .setMessage("Are you sure you want to delete this Quiz?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Handle deleting quiz
                        questionList.clear();
                        dialogInterface.dismiss();
                        Toast.makeText(MainActivity.this, "Quiz Deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Handle closing dialog
                        dialogInterface.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    } }


}
