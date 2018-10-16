package com.luke;

// -------------------------------------------------------------------------

import java.util.Stack;

/**
 * Utility class containing validation/evaluation/conversion operations
 * for prefix and postfix arithmetic expressions.
 *
 * @author
 * @version 1/12/15 13:03:48
 */

public class Arith {
    //~ Validation methods ..........................................................

    /**
     * Validation method for prefix notation.
     *
     * @param prefixLiterals : an array containing the string literals hopefully in prefix order.
     *                       The method assumes that each of these literals can be one of:
     *                       - "+", "-", "*", or "/"
     *                       - or a valid string representation of an integer.
     * @return true if the parameter is indeed in prefix notation, and false otherwise.
     **/
    public static boolean validatePrefixOrder(String prefixLiterals[]) {
        Stack<Integer> stack = new Stack<>();
        try {
            for (int i = prefixLiterals.length - 1; i >= 0; i--) {
                String element = prefixLiterals[i];
                if (isOperator(element)) {
                    stack.push(performOperation(element, stack.pop(), stack.pop()));
                } else {
                    stack.push(Integer.valueOf(element));
                }
            }
            return stack.size() == 1;
        } catch (Exception ignore) {
            return false;
        }
    }

    /**
     * Validation method for postfix notation.
     *
     * @param postfixLiterals : an array containing the string literals hopefully in postfix order.
     *                        The method assumes that each of these literals can be one of:
     *                        - "+", "-", "*", or "/"
     *                        - or a valid string representation of an integer.
     * @return true if the parameter is indeed in postfix notation, and false otherwise.
     **/
    public static boolean validatePostfixOrder(String postfixLiterals[]) {
        Integer count = 0;
        for (String string : postfixLiterals) {
            if (string.matches("-?\\d+")) {
                count++;
            } else {
                if (isOperator(string)) {
                    count--;
                } else {
                    return false;
                }
            }
        }

        return (count == 1);
    }

    private static boolean isOperator(String string) {
        return string.equals("/") ||
                string.equals("*") ||
                string.equals("^") ||
                string.equals("+") ||
                string.equals("-");

    }

    //~ Evaluation  methods ..........................................................

    /**
     * Evaluation method for prefix notation.
     *
     * @param prefixLiterals : an array containing the string literals in prefix order.
     *                       The method assumes that each of these literals can be one of:
     *                       - "+", "-", "*", or "/"
     *                       - or a valid string representation of an integer.
     * @return the integer result of evaluating the expression
     **/
    public static int evaluatePrefixOrder(String prefixLiterals[]) {
        if (validatePrefixOrder(prefixLiterals)) {
            Stack<Integer> stack = new Stack<>();
            for (int i = prefixLiterals.length - 1; i >= 0; i--) {
                String element = prefixLiterals[i];
                if (isOperator(element)) {
                    stack.push(performOperation(element, stack.pop(), stack.pop()));
                } else {
                    stack.push(Integer.valueOf(element));
                }
            }
            return stack.pop();
        } else {
            return 0;
        }
    }

    /**
     * Evaluation method for postfix notation.
     *
     * @param postfixLiterals : an array containing the string literals in postfix order.
     *                        The method assumes that each of these literals can be one of:
     *                        - "+", "-", "*", or "/"
     *                        - or a valid string representation of an integer.
     * @return the integer result of evaluating the expression
     **/
    public static int evaluatePostfixOrder(String postfixLiterals[]) {
        if (validatePostfixOrder(postfixLiterals)) {
            Stack<String> stack = new Stack<>();
            for (String string : postfixLiterals) {
                if (string.matches("-?\\d+")) {
                    stack.push(string);
                } else {
                    int one = Integer.parseInt(stack.pop());
                    int two = Integer.parseInt(stack.pop());

                    int result = performOperation(string, two, one);
                    stack.push(String.valueOf(result));
                }
            }

            return Integer.parseInt(stack.pop());
        }
        return -1;
    }

    private static int performOperation(String string, int one, int two) {
        switch (string) {
            case "*":
                return (one * two);
            case "/":
                return (one / two);
            case "+":
                return (one + two);
            case "-":
                return (one - two);
            default:
                return 0;
        }
    }

    //~ Conversion  methods ..........................................................

    /**
     * Converts prefix to postfix.
     *
     * @param prefixLiterals : an array containing the string literals in prefix order.
     *                       The method assumes that each of these literals can be one of:
     *                       - "+", "-", "*", or "/"
     *                       - or a valid string representation of an integer.
     * @return the expression in postfix order.
     **/
    public static String[] convertPrefixToPostfix(String prefixLiterals[]) {
        if (validatePrefixOrder(prefixLiterals)) {
            Stack<String> stack = new Stack<>();
            for (int i = prefixLiterals.length - 1; i >= 0; i--) {
                if (!isOperator(prefixLiterals[i])) {
                    stack.push(prefixLiterals[i]);
                } else {
                    String[] elements = new String[2];
                    elements[0] = stack.pop();
                    elements[1] = stack.pop();

                    String newItem = elements[0] + " " + elements[1] + " " + prefixLiterals[i];
                    stack.push(newItem);
                }
            }
            return stack.pop().split(" ");
        } else {
            return null;
        }
    }

    /**
     * Converts postfix to prefix.
     *
     * @param postfixLiterals : an array containing the string literals in postfix order.
     *                        The method assumes that each of these literals can be one of:
     *                        - "+", "-", "*", or "/"
     *                        - or a valid string representation of an integer.
     * @return the expression in prefix order.
     **/
    public static String[] convertPostfixToPrefix(String postfixLiterals[]) {
        if (validatePostfixOrder(postfixLiterals)) {
            Stack<String> stack = new Stack<>();
            for (int i = 0; i < postfixLiterals.length; i++) {
                if (!isOperator(postfixLiterals[i])) {
                    stack.push(postfixLiterals[i]);
                } else {
                    String[] elements = new String[2];
                    elements[0] = stack.pop();
                    elements[1] = stack.pop();

                    String newItem = postfixLiterals[i] + " " + elements[1] + " " + elements[0];
                    stack.push(newItem);
                }
            }
            return stack.pop().split(" ");
        } else {
            return null;
        }
    }

    /**
     * Converts prefix to infix.
     *
     * @param prefixLiterals : an array containing the string literals in prefix order.
     *                       The method assumes that each of these literals can be one of:
     *                       - "+", "-", "*", or "/"
     *                       - or a valid string representation of an integer.
     * @return the expression in infix order.
     **/
    public static String[] convertPrefixToInfix(String prefixLiterals[]) {
        if (validatePrefixOrder(prefixLiterals)) {
            Stack<String> stack = new Stack<>();
            for (int i = prefixLiterals.length - 1; i >= 0; i--) {
                if (!isOperator(prefixLiterals[i])) {
                    stack.push(prefixLiterals[i]);
                } else {
                    String[] elements = new String[2];
                    elements[0] = stack.pop();
                    elements[1] = stack.pop();

                    String newItem = "( " + elements[0] + " " + prefixLiterals[i] + " " + elements[1] + " )";
                    stack.push(newItem);
                }
            }
            return stack.pop().split(" ");
        } else {
            return null;
        }
    }

    /**
     * Converts postfix to infix.
     *
     * @param postfixLiterals : an array containing the string literals in postfix order.
     *                        The method assumes that each of these literals can be one of:
     *                        - "+", "-", "*", or "/"
     *                        - or a valid string representation of an integer.
     * @return the expression in infix order.
     **/
    public static String[] convertPostfixToInfix(String postfixLiterals[]) {
        if (validatePostfixOrder(postfixLiterals)) {
            Stack<String> stack = new Stack<>();
            for (int i = 0; i < postfixLiterals.length; i++) {
                if (!isOperator(postfixLiterals[i])) {
                    stack.push(postfixLiterals[i]);
                } else {
                    String[] elements = new String[2];
                    elements[0] = (stack.size() != 0) ? stack.pop() : "";
                    elements[1] = (stack.size() != 0) ? stack.pop() : "";

                    String newItem = "( " + elements[1] + " " + postfixLiterals[i] + " " + elements[0] + " )";
                    stack.push(newItem);
                }
            }
            return stack.pop().split(" ");
        } else {
            return null;
        }
    }
}
