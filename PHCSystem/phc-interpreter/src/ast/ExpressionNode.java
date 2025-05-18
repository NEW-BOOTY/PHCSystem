/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 *
 * ExpressionNode.java
 * AST Node for symbolic Prime Harmonics Calculus expressions.
 */

package symbolic.ast;

import java.util.*;

public final class ExpressionNode {
    private final OperationType operation;
    private final ExpressionNode left;
    private final ExpressionNode right;
    private final List<ExpressionNode> arguments;
    private final String variableName;
    private final Double numericValue;
    private final boolean isFunctionCall;

    // Numeric literal
    public ExpressionNode(double value) {
        this.operation = null;
        this.left = null;
        this.right = null;
        this.arguments = null;
        this.variableName = null;
        this.numericValue = value;
        this.isFunctionCall = false;
    }

    // Variable
    public ExpressionNode(String variableName) {
        this.operation = null;
        this.left = null;
        this.right = null;
        this.arguments = null;
        this.variableName = variableName;
        this.numericValue = null;
        this.isFunctionCall = false;
    }

    // Binary Operation
    public ExpressionNode(OperationType operation, ExpressionNode left, ExpressionNode right) {
        this.operation = operation;
        this.left = left;
        this.right = right;
        this.arguments = null;
        this.variableName = null;
        this.numericValue = null;
        this.isFunctionCall = false;
    }

    // Function Call
    public ExpressionNode(String functionName, List<ExpressionNode> args) {
        this.operation = null;
        this.left = null;
        this.right = null;
        this.arguments = args;
        this.variableName = functionName;
        this.numericValue = null;
        this.isFunctionCall = true;
    }

    public boolean isLiteral() {
        return numericValue != null;
    }

    public boolean isVariable() {
        return variableName != null && !isFunctionCall;
    }

    public boolean isOperation() {
        return operation != null;
    }

    public boolean isFunctionCall() {
        return isFunctionCall;
    }

    public Double getLiteralValue() {
        return numericValue;
    }

    public String getVariableName() {
        return variableName;
    }

    public OperationType getOperation() {
        return operation;
    }

    public ExpressionNode getLeft() {
        return left;
    }

    public ExpressionNode getRight() {
        return right;
    }

    public List<ExpressionNode> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        if (isLiteral()) return String.valueOf(numericValue);
        if (isVariable()) return variableName;
        if (isOperation()) return "(" + left + " " + operation.getSymbol() + " " + right + ")";
        if (isFunctionCall()) {
            return variableName + "(" + String.join(", ",
                arguments.stream().map(Object::toString).toList()) + ")";
        }
        return "UnknownNode";
    }
}
