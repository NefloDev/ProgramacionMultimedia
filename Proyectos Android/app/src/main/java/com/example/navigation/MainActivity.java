package com.example.navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));
    }

    public void goToDrawer(View view){
        Intent i = new Intent(this, DrawerActivity.class);
        startActivity(i);
    }

    public void goToBottom(View view){
        Intent i = new Intent(this, BottomActivity.class);
        startActivity(i);
    }

    public void goToOptions(View view){
        Intent i = new Intent(this, OptionsActivity.class);
        startActivity(i);
    }

    public void goToTabbed(View view){
        Intent i = new Intent(this, TabbedActivity.class);
        startActivity(i);
    }

}