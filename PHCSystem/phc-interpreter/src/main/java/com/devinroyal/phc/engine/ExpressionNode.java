/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package engine;

import util.Logger;

/**
 * ExpressionNode is the fundamental building block of a symbolic expression tree.
 * Each node can represent a value (leaf node) or an operator with child nodes.
 * This structure enables recursive evaluation of complex symbolic expressions.
 */
public final class ExpressionNode {

    private final Character operator;
    private final Double value;
    private final ExpressionNode left;
    private final ExpressionNode right;

    // Leaf node constructor
    public ExpressionNode(double value) {
        this.value = value;
        this.operator = null;
        this.left = null;
        this.right = null;
    }

    // Operator node constructor
    public ExpressionNode(char operator, ExpressionNode left, ExpressionNode right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
        this.value = null;
    }

    public double evaluate() {
        if (operator == null) {
            return value;
        }

        double leftVal = left.evaluate();
        double rightVal = right.evaluate();

        try {
            switch (operator) {
                case '+': return leftVal + rightVal;
                case '-': return leftVal - rightVal;
                case '*': return leftVal * rightVal;
                case '/':
                    if (rightVal == 0) throw new ArithmeticException("Division by zero.");
                    return leftVal / rightVal;
                default:
                    throw new UnsupportedOperationException("Unsupported operator: " + operator);
            }
        } catch (Exception e) {
            Logger.error("Evaluation error at node [" + this + "]: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public String toString() {
        if (operator == null) {
            return Double.toString(value);
        }
        return "(" + left + " " + operator + " " + right + ")";
    }

    public boolean isLeaf() {
        return operator == null;
    }

    public Character getOperator() {
        return operator;
    }

    public Double getValue() {
        return value;
    }

    public ExpressionNode getLeft() {
        return left;
    }

    public ExpressionNode getRight() {
        return right;
    }
}
