/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 *
 * SymbolicParser.java
 * Parses symbolic Prime Harmonics Calculus expressions into abstract syntax trees.
 */

package symbolic;

import symbolic.ast.ExpressionNode;
import symbolic.ast.OperationType;

import java.util.*;

public final class SymbolicParser {

    private static final Set<String> OPERATORS = Set.of("+", "-", "*", "/", "^");

    public ExpressionNode parse(String input) throws IllegalArgumentException {
        try {
            Queue<String> tokens = tokenize(input);
            return parseExpression(tokens);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse symbolic expression: " + e.getMessage());
        }
    }

    private Queue<String> tokenize(String input) {
        Queue<String> tokens = new LinkedList<>();
        StringBuilder buffer = new StringBuilder();

        for (char ch : input.toCharArray()) {
            if (Character.isWhitespace(ch)) continue;

            if (Character.isLetterOrDigit(ch) || ch == '.' || ch == '_' || ch == ',') {
                buffer.append(ch);
            } else {
                if (buffer.length() > 0) {
                    tokens.add(buffer.toString());
                    buffer.setLength(0);
                }
                tokens.add(String.valueOf(ch));
            }
        }

        if (buffer.length() > 0) {
            tokens.add(buffer.toString());
        }

        return tokens;
    }

    private ExpressionNode parseExpression(Queue<String> tokens) {
        return parseAddition(tokens);
    }

    private ExpressionNode parseAddition(Queue<String> tokens) {
        ExpressionNode node = parseMultiplication(tokens);
        while (!tokens.isEmpty()) {
            String op = tokens.peek();
            if ("+".equals(op) || "-".equals(op)) {
                tokens.poll();
                ExpressionNode right = parseMultiplication(tokens);
                node = new ExpressionNode(OperationType.fromSymbol(op), node, right);
            } else {
                break;
            }
        }
        return node;
    }

    private ExpressionNode parseMultiplication(Queue<String> tokens) {
        ExpressionNode node = parseFactor(tokens);
        while (!tokens.isEmpty()) {
            String op = tokens.peek();
            if ("*".equals(op) || "/".equals(op)) {
                tokens.poll();
                ExpressionNode right = parseFactor(tokens);
                node = new ExpressionNode(OperationType.fromSymbol(op), node, right);
            } else {
                break;
            }
        }
        return node;
    }

    private ExpressionNode parseFactor(Queue<String> tokens) {
        String token = tokens.poll();
        if (token == null) throw new IllegalArgumentException("Unexpected end of expression.");

        if ("(".equals(token)) {
            ExpressionNode inner = parseExpression(tokens);
            if (!")".equals(tokens.poll())) {
                throw new IllegalArgumentException("Missing closing parenthesis.");
            }
            return inner;
        }

        if (isNumber(token)) {
            return new ExpressionNode(Double.parseDouble(token));
        }

        if (isVariable(token)) {
            return new ExpressionNode(token);
        }

        if (isFunction(token)) {
            if (!"(".equals(tokens.poll())) {
                throw new IllegalArgumentException("Expected '(' after function name.");
            }
            List<ExpressionNode> args = new ArrayList<>();
            while (!tokens.isEmpty()) {
                args.add(parseExpression(tokens));
                if (")".equals(tokens.peek())) {
                    tokens.poll();
                    break;
                } else if (",".equals(tokens.peek())) {
                    tokens.poll(); // skip comma
                } else {
                    throw new IllegalArgumentException("Malformed function argument list.");
                }
            }
            return new ExpressionNode(token, args);
        }

        throw new IllegalArgumentException("Unrecognized token: " + token);
    }

    private boolean isNumber(String s) {
        return s.matches("[-+]?[0-9]*\\.?[0-9]+");
    }

    private boolean isVariable(String s) {
        return s.matches("[a-zA-Z_][a-zA-Z0-9_]*") && !isFunction(s);
    }

    private boolean isFunction(String s) {
        return s.equalsIgnoreCase("Omega") ||
               s.equalsIgnoreCase("Delta") ||
               s.equalsIgnoreCase("dOmega/dt");
    }
}
