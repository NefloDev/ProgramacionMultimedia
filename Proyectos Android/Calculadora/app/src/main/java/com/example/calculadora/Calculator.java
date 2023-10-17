package com.example.calculadora;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <h2>Calculator Class that manages the evaluation of the formula as a String</h2>
 * @author Alejandro Nebot
 * @version 1.1
 * @since 1.0
 */

public class Calculator {

    /**
     * Method that calculates a formula given as a String, separating the values from the
     * operators and resolving first the multiplications before doing sums or subtraction
     * @param calc String that contains the formula that has to be calculated
     * @return A String containing the result value of the operation
     */
    public static String calculate(String calc){
        long result = 0L;
        boolean found = false;
        int i = 0;

        String[] splitted = calc.split("[+*]|(?=[-+*])|(?<=[^-+*][+*])");
        String[] opers = calc.split("[0-9]|-[0-9]");
        List<Integer> nums;
        List<String> ops;

        nums = Arrays.stream(splitted).filter(s -> !s.isEmpty()).map(Integer::parseInt).collect(Collectors.toList());
        ops = Arrays.stream(opers).filter(s -> !s.isEmpty()).collect(Collectors.toList());

        if (nums.size() > 1 && (calc.contains("+") || calc.contains("-") || calc.contains("*"))) {
            while(ops.size()<nums.size()-1){
                int index = -1;
                if(!found && i<nums.size()){
                    found = nums.get(i) < 0;
                    index = found ? i : index;
                    i++;
                }
                if(index != -1){
                    found = false;
                    ops.add(index-1, "+");
                    i = 0;
                }
            }

            int temp1;
            int temp2;
            while(ops.contains("*")){
                int index = ops.indexOf("*");
                ops.remove(index);
                temp1 = nums.get(index);
                temp2 = nums.get(index+1);
                nums.remove(index+1);
                nums.remove(index);
                nums.add(index, (temp1*temp2));
            }
            for (int num:nums) {
                result += num;
            }

            return String.valueOf(result);
        }
        return calc;
    }

    /**
     * Method that checks if a given character is a valid operator for the calculator
     * @param character Character containing the operator to evaluate
     * @return A boolean that will be true if the character is a valid operator
     */
    public static boolean isSign(char character){
        return character == '-' || character == '+' || character == '*';
    }

    /**
     * Method that checks if a String starts with a zero or a sign
     * @param txt String containing part of a formula
     * @return A boolean that will be true if the String starts with a 0 or a sign (different from -
     * because a number can be negative)
     */
    public static boolean isFirstZeroOrFirstSign(String txt){
        int length = txt.length();
        return length > 1 ?
                (txt.charAt(length-2) == '+' || txt.charAt(length-2) == '*') && txt.charAt(length-1) == '0' :
                (txt.charAt(length - 1) == '0' || txt.charAt(length-1) == '+' || txt.charAt(length-1) == '*');

    }

}
