package com.example.juegoadivinapalabra;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PlayActivity extends Activity {
    private Button volverButton;

    private Button easyButton;

    private Button mediumButton;

    private Button difficultButton;

    public static int difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);

        volverButton = (Button) findViewById(R.id.volverButtonPlay);
        volverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchMainActivity();
            }
        });

        easyButton = (Button) findViewById(R.id.easyButton);
        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                difficulty = 1;
                Intent intent = new Intent(PlayActivity.this, GameActivity.class);//Crashea con GameActivity, pero no con otras clases
                startActivity(intent);
            }
        });

        mediumButton = (Button) findViewById(R.id.mediumButton);
        mediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                difficulty = 2;
                Intent intent = new Intent(PlayActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        difficultButton = (Button) findViewById(R.id.difficultButton);
        difficultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                difficulty = 3;
                Intent intent = new Intent(PlayActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });
    }

    private void switchMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void startGame(){

    }

}