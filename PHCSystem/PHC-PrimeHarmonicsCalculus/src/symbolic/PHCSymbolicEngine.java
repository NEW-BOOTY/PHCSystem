/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 *
 * PHCSymbolicEngine.java
 * Core symbolic processor for Prime Harmonics Calculus.
 * Parses, evaluates, and reduces symbolic expressions over Ω(t), harmonic sequences, and prime dynamics.
 */

package symbolic;

import core.PrimeField;
import java.util.*;
import java.util.regex.*;

public final class PHCSymbolicEngine {

    private final PrimeField field;

    public PHCSymbolicEngine(PrimeField field) {
        this.field = field;
    }

    /**
     * Evaluates a symbolic PHC expression.
     * Supports operators: Omega(t), Omega'(t), Delta[Ω](t+h), etc.
     * @param expression symbolic string
     * @return result string after evaluation
     */
    public String evaluate(String expression) {
        try {
            expression = expression.replaceAll("\\s+", "");
            if (expression.matches("(?i)Omega\\(([-+]?[0-9]*\\.?[0-9]+)\\)")) {
                return evalOmega(expression);
            }

            if (expression.matches("(?i)dOmega\\/(dt)\\(([-+]?[0-9]*\\.?[0-9]+)\\)")) {
                return evalOmegaDerivative(expression);
            }

            if (expression.matches("(?i)Delta\\[Omega\\]\\(([-+]?[0-9]*\\.?[0-9]+),([-+]?[0-9]*\\.?[0-9]+)\\)")) {
                return evalFiniteDifference(expression);
            }

            return "Unsupported symbolic construct: " + expression;

        } catch (Exception ex) {
            return "[PHC SYMBOLIC ERROR] " + ex.getMessage();
        }
    }

    /**
     * Ω(t)
     */
    private String evalOmega(String expr) {
        Matcher matcher = Pattern.compile("Omega\\(([-+]?[0-9]*\\.?[0-9]+)\\)", Pattern.CASE_INSENSITIVE).matcher(expr);
        if (matcher.find()) {
            double t = Double.parseDouble(matcher.group(1));
            double[] complex = field.computeFieldAt(t);
            return String.format("Ω(%.5f) ≈ %.10f + %.10fi", t, complex[0], complex[1]);
        }
        return "Malformed Omega(t)";
    }

    /**
     * dΩ/dt ≈ finite difference
     */
    private String evalOmegaDerivative(String expr) {
        Matcher matcher = Pattern.compile("dOmega\\/dt\\(([-+]?[0-9]*\\.?[0-9]+)\\)", Pattern.CASE_INSENSITIVE).matcher(expr);
        if (matcher.find()) {
            double t = Double.parseDouble(matcher.group(1));
            double h = 1e-5;
            double[] f1 = field.computeFieldAt(t + h);
            double[] f0 = field.computeFieldAt(t - h);
            double dx = (f1[0] - f0[0]) / (2 * h);
            double dy = (f1[1] - f0[1]) / (2 * h);
            return String.format("dΩ/dt(%.5f) ≈ %.10f + %.10fi", t, dx, dy);
        }
        return "Malformed dOmega/dt";
    }

    /**
     * Delta[Ω](t, h) = Ω(t + h) - Ω(t)
     */
    private String evalFiniteDifference(String expr) {
        Matcher matcher = Pattern.compile("Delta\\[Omega\\]\\(([-+]?[0-9]*\\.?[0-9]+),([-+]?[0-9]*\\.?[0-9]+)\\)", Pattern.CASE_INSENSITIVE).matcher(expr);
        if (matcher.find()) {
            double t = Double.parseDouble(matcher.group(1));
            double h = Double.parseDouble(matcher.group(2));
            double[] f0 = field.computeFieldAt(t);
            double[] f1 = field.computeFieldAt(t + h);
            double dx = f1[0] - f0[0];
            double dy = f1[1] - f0[1];
            return String.format("ΔΩ(%.5f, %.5f) ≈ %.10f + %.10fi", t, h, dx, dy);
        }
        return "Malformed Delta[Omega](t,h)";
    }
}
