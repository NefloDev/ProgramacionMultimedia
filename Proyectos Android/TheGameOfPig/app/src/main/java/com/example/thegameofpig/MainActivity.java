package com.example.thegameofpig;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int playerTurn;
    private int sum;
    private TextView firstPlayerPoints;
    private TextView secondPlayerPoints;
    private TextView firstPlayerDiceResultSum;
    private TextView secondPlayerDiceResultSum;
    private TextView firstPlayerLabel;
    private TextView secondPlayerLabel;
    private Button rollButton;
    private Button holdButton;
    private LinearLayout firstPlayerLayout;
    private LinearLayout secondPlayerLayout;
    private ImageView diceImage;
    private int diceResult;
    private int imageResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        playerTurn = 1;
        sum = 0;

        firstPlayerLayout = findViewById(R.id.firstPlayerLayout);
        secondPlayerLayout = findViewById(R.id.secondPlayerLayout);

        firstPlayerLabel = findViewById(R.id.firstPlayerLabel);
        secondPlayerLabel = findViewById(R.id.secondPlayerLabel);

        firstPlayerPoints = findViewById(R.id.firstPLayerPoints);
        secondPlayerPoints = findViewById(R.id.secondPlayerPoints);

        diceImage = findViewById(R.id.diceImage);

        firstPlayerDiceResultSum = findViewById(R.id.firstPlayerDicePointsSum);
        secondPlayerDiceResultSum = findViewById(R.id.secondPlayerDicePointsSum);

        rollButton = findViewById(R.id.rollButton);
        holdButton = findViewById(R.id.holdButton);

        setPlayerColor();

        imageResource = 0;

        super.onCreate(savedInstanceState);
    }

    public void roll(View view){
        diceResult = randomNumberSixDice();
        setDiceImage();

        if(diceResult != 1){
            sum += diceResult;
        }else{
            changeTurn();
        }

        if(playerTurn == 1){
            firstPlayerDiceResultSum.setText(String.valueOf(sum));
        }else{
            secondPlayerDiceResultSum.setText(String.valueOf(sum));
        }
    }

    private void setPlayerColor(){
        Integer drawableFirst;
        Integer drawableSecond;

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            drawableFirst = playerTurn == 1 ? R.drawable.first_player_landscape_bg : null;
            drawableSecond = playerTurn == 2 ? R.drawable.second_player_landscape_bg : null;
        }else{
            drawableFirst = playerTurn == 1 ? R.drawable.first_player_portrait_bg : null;
            drawableSecond = playerTurn == 2 ? R.drawable.second_player_portrait_bg : null;
        }

        firstPlayerLayout.setBackground(drawableFirst != null ? ResourcesCompat.getDrawable(firstPlayerLayout.getResources(), drawableFirst, getTheme()) : null);
        secondPlayerLayout.setBackground(drawableSecond != null ? ResourcesCompat.getDrawable(secondPlayerLayout.getResources(), drawableSecond, getTheme()) : null);
    }

    private void changeTurn(){
        playerTurn = playerTurn == 1 ? 2 : 1;
        setPlayerColor();
        sum = 0;

        firstPlayerDiceResultSum.setText(String.valueOf(sum));
        secondPlayerDiceResultSum.setText(String.valueOf(sum));
    }

    private void setDiceImage(){
        switch (diceResult){
            case 1:
                imageResource = R.drawable.d6_1;
                break;
            case 2:
                imageResource = R.drawable.d6_2;
                break;
            case 3:
                imageResource = R.drawable.d6_3;
                break;
            case 4:
                imageResource = R.drawable.d6_4;
                break;
            case 5:
                imageResource = R.drawable.d6_5;
                break;
            case 6:
                imageResource = R.drawable.d6_6;
                break;
            default:
                imageResource = R.drawable.empty_dice;

        }
        diceImage.setImageResource(imageResource);
    }

    private int randomNumberSixDice(){
        int maxdicevalue = 6;
        int mindicevalue = 1;
        return ((int)(Math.random()*(maxdicevalue - mindicevalue + 1) + mindicevalue));
    }

    public void hold(View view){
        int points;
        String winner = "WINNER!";

        if(sum > 0){
            if(playerTurn == 1){
                points = Integer.parseInt(firstPlayerPoints.getText().toString());
                firstPlayerPoints.setText(String.valueOf(points+sum));
                if((points+sum) >= 100){
                    firstPlayerLabel.setText(winner);
                    finishGame();
                }else {
                    changeTurn();
                }
            }else{
                points = Integer.parseInt(secondPlayerPoints.getText().toString());
                secondPlayerPoints.setText(String.valueOf(points+sum));
                if((points+sum) >= 100){
                    secondPlayerLabel.setText(winner);
                    finishGame();
                }else{
                    changeTurn();
                }
            }
        }
    }

    private void finishGame(){
        rollButton.setClickable(false);
        holdButton.setClickable(false);
    }

    public void restartGame(View view){
        sum = 0;
        playerTurn = 1;
        diceResult = 0;

        setPlayerColor();

        firstPlayerLabel.setText(R.string.first_player_label);
        secondPlayerLabel.setText(R.string.second_player_label);

        firstPlayerPoints.setText(R.string.start_game_value);
        secondPlayerPoints.setText(R.string.start_game_value);

        firstPlayerDiceResultSum.setText(R.string.start_game_value);
        secondPlayerDiceResultSum.setText(R.string.start_game_value);

        rollButton.setClickable(true);
        holdButton.setClickable(true);

        diceImage.setImageResource(R.drawable.empty_dice);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("sum", sum);
        outState.putInt("playerTurn", playerTurn);
        outState.putInt("diceResult", diceResult);
        outState.putInt("imageResource", imageResource);

        outState.putBoolean("button", rollButton.isClickable());

        outState.putString("firstPlayerPoints", firstPlayerPoints.getText().toString());
        outState.putString("secondPlayerPoints", secondPlayerPoints.getText().toString());

        outState.putString("firstPlayerLabel", firstPlayerLabel.getText().toString());
        outState.putString("secondPlayerLabel", secondPlayerLabel.getText().toString());

        outState.putString("firstPlayerDiceResultSum", firstPlayerDiceResultSum.getText().toString());
        outState.putString("secondPlayerDiceResultSum", secondPlayerDiceResultSum.getText().toString());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        sum = savedInstanceState.getInt("sum");
        playerTurn = savedInstanceState.getInt("playerTurn");
        diceResult = savedInstanceState.getInt("diceResult");
        imageResource = savedInstanceState.getInt("imageResource");

        diceImage.setImageResource(imageResource);

        rollButton.setClickable(savedInstanceState.getBoolean("button"));
        holdButton.setClickable(savedInstanceState.getBoolean("button"));

        firstPlayerPoints.setText(savedInstanceState.getString("firstPlayerPoints"));
        secondPlayerPoints.setText(savedInstanceState.getString("secondPlayerPoints"));

        firstPlayerLabel.setText(savedInstanceState.getString("firstPlayerLabel"));
        secondPlayerLabel.setText(savedInstanceState.getString("secondPlayerLabel"));

        firstPlayerDiceResultSum.setText(savedInstanceState.getString("firstPlayerDiceResultSum"));
        secondPlayerDiceResultSum.setText(savedInstanceState.getString("secondPlayerDiceResultSum"));

        setPlayerColor();

        super.onRestoreInstanceState(savedInstanceState);
    }
}