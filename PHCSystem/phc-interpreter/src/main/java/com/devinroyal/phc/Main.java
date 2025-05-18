/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

import core.PrimeField;
import engine.PHCInterpreter;
import engine.SymbolicParser;
import engine.ExpressionNode;
import logic.PHCLogicEvaluator;
import logic.Proposition;
import util.Logger;

import java.util.Scanner;

/**
 * Main entry point for PHC Interpreter system.
 * Handles REPL loop and interactive symbolic evaluation using Prime Harmonics Calculus.
 */
package com.devinroyal.phc;

public class Main {

    public static void main(String[] args) {
        Logger.info("Initializing PHC Interpreter...");
        
        try (Scanner scanner = new Scanner(System.in)) {
            PHCInterpreter interpreter = new PHCInterpreter();
            SymbolicParser parser = new SymbolicParser();
            PHCLogicEvaluator logicEvaluator = new PHCLogicEvaluator();

            Logger.info("PHC Interpreter Ready. Type ':exit' to quit.");
            while (true) {
                System.out.print("\nPHC> ");
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase(":exit")) {
                    Logger.info("Terminating PHC Interpreter.");
                    break;
                }

                try {
                    if (input.startsWith(":eval ")) {
                        String expr = input.substring(6).trim();
                        ExpressionNode ast = parser.parse(expr);
                        double result = interpreter.evaluate(ast);
                        System.out.println("⇒ " + result);
                    } else if (input.startsWith(":prop ")) {
                        String propStr = input.substring(6).trim();
                        Proposition prop = new Proposition(propStr);
                        boolean result = logicEvaluator.evaluate(prop);
                        System.out.println("⇒ " + (result ? "TRUE" : "FALSE"));
                    } else if (input.equalsIgnoreCase(":field")) {
                        PrimeField field = new PrimeField();
                        field.printFieldSummary();
                    } else {
                        Logger.warn("Unknown command. Use :eval, :prop, :field, or :exit.");
                    }
                } catch (Exception e) {
                    Logger.error("Failed to evaluate input: " + input, e);
                }
            }

        } catch (Exception ex) {
            Logger.error("Fatal error during PHC startup.", ex);
        }
    }
}
