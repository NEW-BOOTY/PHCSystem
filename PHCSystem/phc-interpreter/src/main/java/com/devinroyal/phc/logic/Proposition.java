/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package logic;

/**
 * Proposition represents logical statements used in PHCLogicEvaluator.
 * Supports atomic (boolean), unary (NOT), and binary (AND, OR, IMPLIES) logical constructs.
 */
public final class Proposition {

    public enum Type {
        ATOMIC,
        NOT,
        AND,
        OR,
        IMPLIES
    }

    private final Type type;
    private final Boolean value;               // Only for ATOMIC propositions
    private final Proposition operand;        // For unary operators (NOT)
    private final Proposition leftOperand;    // For binary operators
    private final Proposition rightOperand;   // For binary operators

    // Atomic proposition constructor
    public Proposition(boolean value) {
        this.type = Type.ATOMIC;
        this.value = value;
        this.operand = null;
        this.leftOperand = null;
        this.rightOperand = null;
    }

    // Unary proposition constructor (NOT)
    public Proposition(Type type, Proposition operand) {
        if (type != Type.NOT) {
            throw new IllegalArgumentException("Unary constructor only supports NOT");
        }
        this.type = type;
        this.operand = operand;
        this.value = null;
        this.leftOperand = null;
        this.rightOperand = null;
    }

    // Binary proposition constructor (AND, OR, IMPLIES)
    public Proposition(Type type, Proposition leftOperand, Proposition rightOperand) {
        if (type != Type.AND && type != Type.OR && type != Type.IMPLIES) {
            throw new IllegalArgumentException("Binary constructor only supports AND, OR, IMPLIES");
        }
        this.type = type;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.value = null;
        this.operand = null;
    }

    public Type getType() {
        return type;
    }

    public Boolean getValue() {
        return value;
    }

    public Proposition getOperand() {
        return operand;
    }

    public Proposition getLeftOperand() {
        return leftOperand;
    }

    public Proposition getRightOperand() {
        return rightOperand;
    }

    @Override
    public String toString() {
        switch (type) {
            case ATOMIC:
                return Boolean.toString(value);
            case NOT:
                return "¬(" + operand + ")";
            case AND:
                return "(" + leftOperand + " ∧ " + rightOperand + ")";
            case OR:
                return "(" + leftOperand + " ∨ " + rightOperand + ")";
            case IMPLIES:
                return "(" + leftOperand + " → " + rightOperand + ")";
            default:
                return "Unknown Proposition";
        }
    }
}
