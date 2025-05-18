/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 *
 * Proposition.java
 * Encapsulates logic statements and metadata for PHC processing.
 */

package symbolic.logic;

import symbolic.ast.ExpressionNode;

public final class Proposition {

    private final String label;
    private final ExpressionNode expression;
    private final boolean expectedTruth;

    public Proposition(String label, ExpressionNode expression, boolean expectedTruth) {
        this.label = label;
        this.expression = expression;
        this.expectedTruth = expectedTruth;
    }

    public String getLabel() {
        return label;
    }

    public ExpressionNode getExpression() {
        return expression;
    }

    public boolean getExpectedTruth() {
        return expectedTruth;
    }

    @Override
    public String toString() {
        return label + ": " + expression.toString() + " ⟹ expected = " + expectedTruth;
    }
}
