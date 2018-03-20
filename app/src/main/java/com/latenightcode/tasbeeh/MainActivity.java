package com.latenightcode.tasbeeh;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

public class MainActivity extends AppCompatActivity {

    private LinearLayout mButtonCountLayout;
    private TextView mTasbeehName;
    private TextSwitcher mTextSwitcher;
    private TextView mCountShowTextView;
    private String[] tasbeehName = {"Allahu Akbar", "Subhanallah", "La Ilaha illallah", "Bismillahir Rahmanir Rahim", "Alhamdulilloh", "Astagfirullah", "La hawla wa la \nquwwata illa billah", "Subhanallah Owa-bihamdi\nSubhanallahil Azim"};

    int counterValue;
    int stringIndex = 0;
    private String setTasbeehName;

    //showcase view
    ViewTarget target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextSwitcher = findViewById(R.id.textswitcher_tasbeeh_name);
        mButtonCountLayout = findViewById(R.id.button_linearlayout);
        mCountShowTextView = findViewById(R.id.textview_count);
        target = new ViewTarget(findViewById(R.id.textswitcher_tasbeeh_name));

        final Integer colorAccent = getResources().getColor(R.color.colorAccent);
        String getCounterNumberFromTextView = mCountShowTextView.getText().toString();
        counterValue = 0;

        mTextSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(stringIndex == tasbeehName.length - 1){

                    stringIndex = 0;
                    mTextSwitcher.setText(tasbeehName[stringIndex]);
                    getTasbeehNameAndChangeTextViewValue();

                }else{

                    mTextSwitcher.setText(tasbeehName[++stringIndex]);
                    getTasbeehNameAndChangeTextViewValue();

                }

            }
        });

        mTextSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @SuppressLint("ResourceAsColor")
            @Override
            public View makeView() {

                mTasbeehName = new TextView(MainActivity.this);
                mTasbeehName.setTextColor(colorAccent);
                mTasbeehName.setTextSize(24);
                mTasbeehName.setGravity(Gravity.CENTER_HORIZONTAL);
                return mTasbeehName;

            }
        });

        mTextSwitcher.setText(tasbeehName[stringIndex]);

        mButtonCountLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                counterValue++;
                mCountShowTextView.setText(String.valueOf(counterValue));

            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("PREFERENCES", MODE_PRIVATE);
        boolean firstStart = sharedPreferences.getBoolean("FIRST_START", true);

        if(firstStart){
            setTourGuide(target);
        }

    }

    private void setTourGuide(ViewTarget target) {

        new ShowcaseView.Builder(this)
                .setContentTitle("Cilck and explore new tasbeeh")
                .setContentText("Click on Tasbeeh Name \"Allahu Akbar\" to set another Tasbeeh")
                .useDecorViewAsParent()
                .setTarget(target)
                .setStyle(R.style.CustomShowcaseTheme3)
                .build();

        SharedPreferences preferences = getSharedPreferences("PREFERENCES", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("FIRST_START", false);
        editor.apply();

    }

    private void getTasbeehNameAndChangeTextViewValue() {

        //getting selected tasbeeh name from user
        setTasbeehName = tasbeehName[stringIndex];

        //change the counter value to zero for shifting new tasbeeh name
        mCountShowTextView.setText(R.string.zero);
        counterValue = 0;

    }
}
