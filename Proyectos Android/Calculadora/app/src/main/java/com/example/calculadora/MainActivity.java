package com.example.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * <h2>MainActivity class that manages app interface changes and Calculator Class results</h2>
 * @author Alejandro Nebot
 * @version 1.1
 * @since 1.0
 */
public class MainActivity extends AppCompatActivity {
    /**
     * TextView attribute that gets the Text show in the app as formula/result
     */
    private TextView calcText;

    /**
     * Method that initializes the class as a manager for the layout "activity_main.xml"
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calcText = findViewById(R.id.calcText);
    }

    /**
     * Method that changes "calcText" inserting a number or operator depending on which button is being pressed
     * @param view A reference to the view that is executing the method
     */
    public void addChar(View view){
        Button bt = (Button) view;
        String equation = calcText.getText().toString();
        char character = bt.getText().charAt(0);

        calcText.setText(
                (!(equation.charAt(equation.length()-1) == '*' && character == '-') && Calculator.isSign(equation.charAt(equation.length()-1))
                        && Calculator.isSign(character)) || Calculator.isFirstZeroOrFirstSign(equation) ?
                        equation.substring(0, equation.length()-1) + character :
                        equation + character
        );

        equation = calcText.getText().toString();

        if(equation.contains("*+") || equation.contains("**")){
            int index = equation.contains("*+") ? equation.indexOf("*+") : equation.indexOf("**");
            String last = equation.substring(index, equation.length()-1);
            if(index != 0){
                equation = equation.substring(0, index);
            }
            equation = equation+last;
        }

        calcText.setText(equation);
    }

    /**
     * Method that sets the text of "calcText" to "0" to reset the calculation
     * @param view A reference to the view that is executing the method (in this case, the only one
     *             that has access to it is "C" button
     */
    public void clear(View view){
        calcText.setText("0");
    }

    /**
     * Method that sets the text of calcText to the result given by the "calculate()" method
     * of "Calculator" class by giving it the text of the formula
     * @param view A reference to the view that is executing the method (in this case, the only one
     *             that has access to it is "=" button)
     */
    public void calculate(View view){
        calcText.setText(Calculator.calculate(calcText.getText().toString()));
    }

    /**
     * Method that saves the state of the variables that are written in to recover them after
     * the app re-starts
     * @param outState Bundle in which to place your saved state.
     *
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("equation", calcText.getText().toString());
        super.onSaveInstanceState(outState);
    }

    /**
     * Method that recovers the state of the variables that were saved before stopping the app after
     * re-starting it
     * @param savedInstanceState the data most recently supplied in {@link #onSaveInstanceState}.
     *
     */
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        calcText.setText(savedInstanceState.getString("equation"));
        super.onRestoreInstanceState(savedInstanceState);
    }
}
