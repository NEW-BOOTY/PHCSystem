/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package logic;

import util.Logger;

/**
 * PHCLogicEvaluator evaluates logical expressions within the Prime Harmonics Calculus framework.
 * Supports conjunction, disjunction, negation, and implication operations on Propositions.
 * Ensures robust error handling and recursive evaluation.
 */
public final class PHCLogicEvaluator {

    /**
     * Evaluates the truth value of a Proposition.
     * Supports recursive evaluation of compound propositions.
     *
     * @param proposition the logical proposition to evaluate
     * @return true if the proposition evaluates to true, false otherwise
     * @throws IllegalArgumentException if the proposition is null or malformed
     */
    public boolean evaluate(Proposition proposition) {
        if (proposition == null) {
            Logger.error("Attempted to evaluate a null Proposition.");
            throw new IllegalArgumentException("Proposition cannot be null");
        }

        try {
            switch (proposition.getType()) {
                case ATOMIC:
                    return proposition.getValue();
                case NOT:
                    return !evaluate(proposition.getOperand());
                case AND:
                    return evaluate(proposition.getLeftOperand()) && evaluate(proposition.getRightOperand());
                case OR:
                    return evaluate(proposition.getLeftOperand()) || evaluate(proposition.getRightOperand());
                case IMPLIES:
                    return !evaluate(proposition.getLeftOperand()) || evaluate(proposition.getRightOperand());
                default:
                    throw new UnsupportedOperationException("Unsupported Proposition type: " + proposition.getType());
            }
        } catch (Exception e) {
            Logger.error("Error evaluating proposition: " + proposition + " - " + e.getMessage(), e);
            throw e;
        }
    }
}
