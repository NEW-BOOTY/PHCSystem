/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 *
 * PHCLogicEvaluator.java
 * Evaluates ExpressionNode symbolic trees for logical truth in PHC semantics.
 */

package symbolic.logic;

import symbolic.ast.ExpressionNode;
import symbolic.ast.OperationType;
import symbolic.util.ComplexUtils;
import symbolic.log.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PHCLogicEvaluator {

    private final Map<String, Double> variableScope;

    public PHCLogicEvaluator() {
        this.variableScope = new HashMap<>();
    }

    public void assignVariable(String variable, double value) {
        variableScope.put(variable, value);
        Logger.info("Assigned variable " + variable + " = " + value);
    }

    public double evaluate(ExpressionNode node) throws ArithmeticException {
        if (node.isLiteral()) return node.getLiteralValue();

        if (node.isVariable()) {
            String name = node.getVariableName();
            if (!variableScope.containsKey(name)) {
                Logger.error("Undefined variable: " + name);
                throw new ArithmeticException("Undefined variable: " + name);
            }
            return variableScope.get(name);
        }

        if (node.isOperation()) {
            double leftVal = evaluate(node.getLeft());
            double rightVal = evaluate(node.getRight());
            return applyOperation(node.getOperation(), leftVal, rightVal);
        }

        if (node.isFunctionCall()) {
            return evaluateFunction(node.getVariableName(), node.getArguments());
        }

        throw new ArithmeticException("Unsupported node structure: " + node);
    }

    private double applyOperation(OperationType op, double left, double right) {
        return switch (op) {
            case ADD -> left + right;
            case SUB -> left - right;
            case MUL -> left * right;
            case DIV -> {
                if (right == 0) throw new ArithmeticException("Division by zero");
                yield left / right;
            }
            case POW -> Math.pow(left, right);
        };
    }

    private double evaluateFunction(String name, List<ExpressionNode> args) {
        try {
            return switch (name.toLowerCase()) {
                case "sin" -> Math.sin(evaluate(args.get(0)));
                case "cos" -> Math.cos(evaluate(args.get(0)));
                case "tan" -> Math.tan(evaluate(args.get(0)));
                case "exp" -> Math.exp(evaluate(args.get(0)));
                case "log" -> {
                    double val = evaluate(args.get(0));
                    if (val <= 0) throw new ArithmeticException("log domain error");
                    yield Math.log(val);
                }
                case "sqrt" -> {
                    double val = evaluate(args.get(0));
                    if (val < 0) throw new ArithmeticException("sqrt domain error");
                    yield Math.sqrt(val);
                }
                case "abs" -> Math.abs(evaluate(args.get(0)));
                case "primeharm" -> ComplexUtils.phcKernel(evaluate(args.get(0)));
                default -> throw new UnsupportedOperationException("Unknown function: " + name);
            };
        } catch (IndexOutOfBoundsException e) {
            throw new ArithmeticException("Insufficient arguments for function: " + name);
        }
    }

    public boolean isTrue(ExpressionNode proposition) {
        try {
            double val = evaluate(proposition);
            return Math.abs(val) > 1e-9;
        } catch (Exception e) {
            Logger.error("Evaluation failed: " + e.getMessage());
            return false;
        }
    }
}
