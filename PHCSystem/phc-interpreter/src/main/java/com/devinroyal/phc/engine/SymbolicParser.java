/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package engine;

import util.Logger;
import java.util.Stack;

/**
 * SymbolicParser parses mathematical expressions represented as strings
 * and constructs an expression tree composed of ExpressionNode objects.
 * This parser is used in the PHC system to interpret symbolic forms that
 * can be evaluated over PrimeField and related constructs.
 */
public final class SymbolicParser {

    public ExpressionNode parse(String expression) throws IllegalArgumentException {
        try {
            return buildExpressionTree(expression.replaceAll("\\s+", ""));
        } catch (Exception e) {
            Logger.error("Failed to parse expression: " + expression, e);
            throw new IllegalArgumentException("Parsing error: " + e.getMessage());
        }
    }

    private ExpressionNode buildExpressionTree(String expr) {
        Stack<ExpressionNode> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        int i = 0;
        while (i < expr.length()) {
            char ch = expr.charAt(i);

            if (Character.isDigit(ch)) {
                int start = i;
                while (i < expr.length() && (Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.')) {
                    i++;
                }
                double value = Double.parseDouble(expr.substring(start, i));
                values.push(new ExpressionNode(value));
                continue;
            }

            if (ch == '(') {
                operators.push(ch);
            } else if (ch == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    applyOperator(values, operators.pop());
                }
                if (!operators.isEmpty() && operators.peek() == '(') {
                    operators.pop();
                }
            } else if (isOperator(ch)) {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(ch)) {
                    applyOperator(values, operators.pop());
                }
                operators.push(ch);
            }

            i++;
        }

        while (!operators.isEmpty()) {
            applyOperator(values, operators.pop());
        }

        return values.isEmpty() ? null : values.pop();
    }

    private boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    private int precedence(char op) {
        switch (op) {
            case '+':
            case '-': return 1;
            case '*':
            case '/': return 2;
        }
        return 0;
    }

    private void applyOperator(Stack<ExpressionNode> values, char operator) {
        ExpressionNode right = values.pop();
        ExpressionNode left = values.pop();
        values.push(new ExpressionNode(operator, left, right));
    }
}
