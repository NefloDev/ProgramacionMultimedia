package com.example.juegoadivinapalabra;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ListView;
import android.widget.TextView;

public class GameActivity extends Activity {
    private TextView countDownText;
    private ListView wordList;

    private CountDownTimer countDownTimer;
    private static final long MINUTEINMILLISECONDS = 60000L;

    private static final int EASYMINUTES = 5;

    private static final int MEDIUMMINUTES = 4;

    private static final int DIFFICULTMINUTES = 3;

    private long timeLeftInMilliseconds;

    private final long COUNTDOWN = 1000L;
    private boolean timeRunning;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        countDownText = (TextView) findViewById(R.id.countDownText);

        switch(PlayActivity.difficulty){
            case 1:
                timeLeftInMilliseconds = EASYMINUTES * MINUTEINMILLISECONDS;
                break;
            case 2:
                timeLeftInMilliseconds = MEDIUMMINUTES * MINUTEINMILLISECONDS;
                break;
            default:
                timeLeftInMilliseconds = DIFFICULTMINUTES * MINUTEINMILLISECONDS;
        }

        startTimer();
    }

    public void startTimer(){
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, COUNTDOWN) {
            @Override
            public void onTick(long l) {
                timeLeftInMilliseconds = l;
                updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    public void updateTimer(){
        int minutes = (int) (timeLeftInMilliseconds / MINUTEINMILLISECONDS);
        int seconds = (int) (timeLeftInMilliseconds % MINUTEINMILLISECONDS / COUNTDOWN);

        String timeLeftText;
        timeLeftText = "" + minutes;
        timeLeftText += ":";
        if(seconds < 10){
            timeLeftText += "0";
        }
        timeLeftText += seconds;
        countDownText.setText(timeLeftText);
    }
}
