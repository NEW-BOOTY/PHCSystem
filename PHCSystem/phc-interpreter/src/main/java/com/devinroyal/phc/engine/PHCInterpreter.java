/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package engine;

import core.PrimeField;
import core.PrimeOperator;
import core.SpectralPrime;
import core.PhiLattice;
import logic.PHCLogicEvaluator;
import logic.Proposition;
import util.Logger;

import java.util.Scanner;
import java.util.List;

/**
 * PHCInterpreter is the symbolic interpreter and command processor
 * for evaluating Prime Harmonics Calculus expressions and logical
 * propositions involving prime fields, lattice constructs, and
 * symbolic transformations over spectral primes.
 */
public final class PHCInterpreter {

    private final PrimeField field;
    private final PhiLattice lattice;
    private final PHCLogicEvaluator logicEvaluator;

    public PHCInterpreter() {
        this.field = new PrimeField();
        this.lattice = new PhiLattice(field);
        this.logicEvaluator = new PHCLogicEvaluator();
    }

    public void startInteractiveSession() {
        Logger.info("PHC Interpreter initialized. Awaiting symbolic input...");
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("\nPHC> ");
                final String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit")) {
                    Logger.info("Exiting PHC Interpreter.");
                    break;
                }

                evaluateInput(input);
            }
        } catch (Exception e) {
            Logger.error("Unexpected error in interpreter session: " + e.getMessage(), e);
        }
    }

    private void evaluateInput(String input) {
        try {
            if (input.startsWith("prop ")) {
                Proposition prop = Proposition.parse(input.substring(5));
                boolean result = logicEvaluator.evaluate(prop);
                Logger.success("Proposition evaluated: " + result);
            } else if (input.startsWith("spectral ")) {
                int index = Integer.parseInt(input.substring(9).trim());
                SpectralPrime sp = field.getSpectralPrime(index);
                Logger.success("Spectral Prime [" + index + "] = " + sp);
            } else if (input.startsWith("lattice ")) {
                int n = Integer.parseInt(input.substring(8).trim());
                List<Double> projection = lattice.projectPhiAxis(n);
                Logger.success("PhiLattice projection @ index " + n + ": " + projection);
            } else if (input.startsWith("op ")) {
                String expr = input.substring(3);
                PrimeOperator op = PrimeOperator.compile(expr);
                double result = op.evaluate(field);
                Logger.success("PrimeOperator '" + expr + "' evaluated: " + result);
            } else {
                Logger.warn("Unknown command. Try: prop, spectral, lattice, op, or exit.");
            }
        } catch (Exception e) {
            Logger.error("Evaluation error: " + e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        new PHCInterpreter().startInteractiveSession();
    }
}
