package com.example.calculadora;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * <h2>Test class for Calculator class methods</h2>
 * @author Alejandro Nebot
 * @version 1.0
 * @since 1.0
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CalculatorUnitTest {
    /**
     * Test method that asserts if a basic sum operation gets the correct result
     */
    @Test
    public void testAdd2Operands() {
        int total = Integer.parseInt(Calculator.calculate("5+3"));
        assertEquals("X + Y operations not working correctly",8, total);
    }
    /**
     * Test method that asserts if a basic sum operation of more than 2 numbers gets
     * the correct result
     */
    @Test
    public void testAdd1Operand() {
        int total = Integer.parseInt(Calculator.calculate("4+3+1"));
        assertEquals("+ X operations not working correctly",8, total);
    }
    /**
     * Test method that asserts if a basic multiplication operation gets
     * the correct result
     */
    @Test
    public void testMult2Operands() {
        int total = Integer.parseInt(Calculator.calculate("4*2"));
        assertEquals("X * Y operations not working correctly",8, total);
    }
    /**
     * Test method that asserts if a basic multiplication operation of more than 2 numbers gets
     * the correct result
     */
    @Test
    public void testMult1Operand() {
        int total = Integer.parseInt(Calculator.calculate("2*2*2"));
        assertEquals("* X operations not working correctly",8, total);
    }
    /**
     * Test method that asserts if a basic subtraction operation gets
     * the correct result
     */
    @Test
    public void testSubtract2Operands() {
        int total = Integer.parseInt(Calculator.calculate("10-2"));
        assertEquals("X - Y operations not working correctly",8, total);
    }
    /**
     * Test method that asserts if a basic subtraction operation of more than 2 numbers gets
     * the correct result
     */
    @Test
    public void testSubtract1Operand() {
        int total = Integer.parseInt(Calculator.calculate("14-1-5"));
        assertEquals("- X operations not working correctly",8, total);
    }
    /**
     * Test method that asserts if a basic multiplication operation by zero gets
     * the correct result
     */
    @Test
    public void testMultiplyBy0() {
        int total = Integer.parseInt(Calculator.calculate("3*0"));
        assertEquals("X * 0 operations not working correctly",0, total);
    }
    /**
     * Test method that asserts if a basic multiplication operation by a negative number gets
     * the correct result
     */
    @Test
    public void testMultiplyByNegative() {
        int total = Integer.parseInt(Calculator.calculate("8*-1"));
        assertEquals("X * -Y operations not working correctly",-8, total);
    }
}