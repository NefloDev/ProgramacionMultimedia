package com.example.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class MainActivity extends AppCompatActivity {
    private TextView calcText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calcText = findViewById(R.id.calcText);
    }

    public void addChar(View view){
        Button bt = (Button) view;
        String equation = calcText.getText().toString();
        char character = bt.getText().charAt(0);
        calcText.setText(
                (isSign(equation.charAt(equation.length()-1)) && isSign(character)) || isFirstZeroOrFirstSum(equation) ?
                        equation.substring(0, equation.length()-1) + character :
                        equation + character
        );
    }

    public void calc(View view){
        String equation = calcText.getText().toString().replaceAll("", "");
        double result;
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("rhino");
        if(equation.contains("+") || equation.contains("-")){
            try {
                result = (double)engine.eval(equation);
                calcText.setText(String.format("%.0f", result));
            } catch (ScriptException e) {
                System.err.println("Error getting result");
            }
        }
    }

    public void clear(View view){
        calcText.setText("0");
    }

    private boolean isSign(char character){
        return character == '-' || character == '+';
    }

    private boolean isFirstZeroOrFirstSum(String txt){
        int length = txt.length();
        return length > 1 ?
                txt.charAt(length-2) == '+' && txt.charAt(length-1) == '0' :
                (txt.charAt(length - 1) == '0' || txt.charAt(length-1) == '+');

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("equation", calcText.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        calcText.setText(savedInstanceState.getString("equation"));
        super.onRestoreInstanceState(savedInstanceState);
    }
}