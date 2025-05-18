/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package core;

import java.math.BigInteger;
import java.util.List;
import java.util.ArrayList;
import util.Logger;

/**
 * Provides mathematical operations and transformations over SpectralPrime structures.
 * Encodes the symbolic operator algebra central to Prime Harmonics Calculus (PHC).
 */
public final class PrimeOperator {

    /**
     * Adds two SpectralPrimes by approximating spectral superposition.
     *
     * @param a SpectralPrime
     * @param b SpectralPrime
     * @return SpectralPrime representing harmonic fusion (approximate)
     */
    public static SpectralPrime add(SpectralPrime a, SpectralPrime b) {
        try {
            BigInteger synthetic = a.getPrimeValue().add(b.getPrimeValue());
            return new SpectralPrime(nextLikelyPrime(synthetic));
        } catch (Exception e) {
            Logger.error("Error in PrimeOperator.add(): " + e.getMessage());
            throw new RuntimeException("Failed to add SpectralPrimes.");
        }
    }

    /**
     * Subtracts two SpectralPrimes.
     *
     * @param a SpectralPrime
     * @param b SpectralPrime
     * @return SpectralPrime or null if result is not prime
     */
    public static SpectralPrime subtract(SpectralPrime a, SpectralPrime b) {
        BigInteger diff = a.getPrimeValue().subtract(b.getPrimeValue()).abs();
        if (diff.compareTo(BigInteger.TWO) < 0 || !diff.isProbablePrime(80)) {
            Logger.warn("Subtraction result is not a prime: " + diff);
            return null;
        }
        return new SpectralPrime(diff);
    }

    /**
     * Computes the harmonic tensor product of two SpectralPrimes.
     *
     * @param a SpectralPrime
     * @param b SpectralPrime
     * @return SpectralPrime representing tensor-like fusion
     */
    public static SpectralPrime tensor(SpectralPrime a, SpectralPrime b) {
        BigInteger combined = a.getPrimeValue().multiply(b.getPrimeValue());
        return new SpectralPrime(nextLikelyPrime(combined));
    }

    /**
     * Applies a spectral scaling factor.
     *
     * @param sp SpectralPrime
     * @param scalar int scalar multiplier
     * @return List of SpectralPrimes scaled across harmonic tiers
     */
    public static List<SpectralPrime> scale(SpectralPrime sp, int scalar) {
        List<SpectralPrime> results = new ArrayList<>();
        try {
            BigInteger base = sp.getPrimeValue();
            for (int i = 1; i <= scalar; i++) {
                BigInteger p = base.multiply(BigInteger.valueOf(i));
                results.add(new SpectralPrime(nextLikelyPrime(p)));
            }
        } catch (Exception e) {
            Logger.error("Error scaling SpectralPrime: " + e.getMessage());
        }
        return results;
    }

    /**
     * Locates the next probable prime greater than or equal to the input.
     *
     * @param candidate BigInteger
     * @return Next probable prime
     */
    public static BigInteger nextLikelyPrime(BigInteger candidate) {
        if (candidate == null || candidate.compareTo(BigInteger.TWO) < 0) {
            return BigInteger.TWO;
        }

        BigInteger current = candidate;
        while (!current.isProbablePrime(100)) {
            current = current.add(BigInteger.ONE);
        }
        return current;
    }

    private PrimeOperator() {
        throw new UnsupportedOperationException("PrimeOperator is a static utility class.");
    }
}
