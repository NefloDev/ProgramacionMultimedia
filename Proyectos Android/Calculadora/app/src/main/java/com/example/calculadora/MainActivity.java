package com.example.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
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

//    public void calc(View view){
//        //Calculo realizado a partir de importar el motor "Rhino", éste permite evaluar código en forma de cadena de texto, que,en este caso, es una
//        //formula asi que lo que devuelve es un resultado numérico
//        String equation = calcText.getText().toString();
//        double result;
//        ScriptEngineManager mgr = new ScriptEngineManager();
//        ScriptEngine engine = mgr.getEngineByName("rhino");
//        if(equation.contains("+") || equation.contains("-")){
//            try {
//                result = (double)engine.eval(equation);
//                calcText.setText(String.format("%.0f", result));
//            } catch (ScriptException e) {
//                System.err.println("Error getting result");
//            }
//        }
//    }

    public void calculate(View view){
        String calc = calcText.getText().toString();
        int result = 0;

        StringBuilder number;
        ArrayList<Integer> nums = new ArrayList<>();

        for (int i = 0; i < calc.length(); i++) {
            char token = calc.charAt(i);
            if (Character.isDigit(token)){
                number = new StringBuilder();
                while(i<calc.length() && Character.isDigit(calc.charAt(i))){
                    if(Character.isDigit(calc.charAt(i))){
                        if(i > 0){
                            if(calc.charAt(i-1) == '-'){
                                number.append("-");
                            }
                        }
                        token = calc.charAt(i);
                        number.append(token);
                        i++;
                    }
                }
                nums.add(Integer.parseInt(number.toString()));
            }
        }
        if (nums.size() > 1 && (calc.contains("+") || calc.contains("-"))) {
            for (int num: nums) {
                result += num;
            }

            calcText.setText(String.valueOf(result));
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
