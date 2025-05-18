/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 *
 * OperationType.java
 * Enum of symbolic operations.
 */

package symbolic.ast;

public enum OperationType {
    ADD("+"),
    SUB("-"),
    MUL("*"),
    DIV("/"),
    POW("^");

    private final String symbol;

    OperationType(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public static OperationType fromSymbol(String symbol) {
        return switch (symbol) {
            case "+" -> ADD;
            case "-" -> SUB;
            case "*" -> MUL;
            case "/" -> DIV;
            case "^" -> POW;
            default -> throw new IllegalArgumentException("Unknown operator: " + symbol);
        };
    }
}
