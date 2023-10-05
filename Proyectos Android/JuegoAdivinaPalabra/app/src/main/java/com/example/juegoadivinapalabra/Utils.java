package com.example.juegoadivinapalabra;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Utils extends Activity{
    public void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
