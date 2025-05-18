/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 *
 * Main.java
 * Entry point for PHC interpreter CLI testing and interactive evaluation.
 */

package symbolic;

import symbolic.ast.ExpressionNode;
import symbolic.ast.OperationType;
import symbolic.logic.PHCLogicEvaluator;
import symbolic.logic.Proposition;
import symbolic.log.Logger;

public final class Main {

    public static void main(String[] args) {
        Logger.info("PHC Interpreter Booting Up");

        PHCLogicEvaluator evaluator = new PHCLogicEvaluator();

        try {
            // Set symbolic scope
            evaluator.assignVariable("x", 2.0);
            evaluator.assignVariable("y", 3.0);

            // Build proposition: (x^2 + y^2) > 10
            ExpressionNode x = ExpressionNode.variable("x");
            ExpressionNode y = ExpressionNode.variable("y");
            ExpressionNode sumSq = ExpressionNode.operation(
                    OperationType.ADD,
                    ExpressionNode.operation(OperationType.POW, x, ExpressionNode.literal(2)),
                    ExpressionNode.operation(OperationType.POW, y, ExpressionNode.literal(2))
            );

            Proposition prop = new Proposition("Pythagorean Magnitude", sumSq, true);

            Logger.info("Evaluating Proposition: " + prop);
            boolean result = evaluator.isTrue(prop.getExpression());

            if (result == prop.getExpectedTruth()) {
                Logger.info("Proposition PASSED truth check.");
            } else {
                Logger.warn("Proposition FAILED truth check.");
            }

        } catch (Exception e) {
            Logger.error("Fatal error during symbolic evaluation: " + e.getMessage());
        }

        Logger.info("PHC Interpreter Shutdown");
    }
}
