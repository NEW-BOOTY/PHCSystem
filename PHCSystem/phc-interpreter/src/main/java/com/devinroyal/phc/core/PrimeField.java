/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package core;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import util.Logger;

/**
 * Represents a finite harmonic field constructed from SpectralPrimes.
 * Enables algebraic and symbolic PHC field-level operations.
 */
public class PrimeField {

    private final Set<SpectralPrime> elements;
    private final BigInteger modulus;

    /**
     * Initializes a PrimeField with an upper harmonic limit.
     *
     * @param modulus BigInteger modulus defining the boundary of the field
     */
    public PrimeField(BigInteger modulus) {
        if (modulus == null || !modulus.isProbablePrime(100)) {
            throw new IllegalArgumentException("Modulus must be a valid probable prime.");
        }

        this.modulus = modulus;
        this.elements = new HashSet<>();
        populateField();
    }

    /**
     * Populates the harmonic field with SpectralPrime elements modulo the modulus.
     */
    private void populateField() {
        BigInteger current = BigInteger.TWO;
        while (current.compareTo(modulus) < 0) {
            if (current.isProbablePrime(100)) {
                try {
                    elements.add(new SpectralPrime(current));
                } catch (Exception e) {
                    Logger.error("Error creating SpectralPrime: " + e.getMessage());
                }
            }
            current = current.add(BigInteger.ONE);
        }
        Logger.info("PrimeField populated with " + elements.size() + " SpectralPrime elements.");
    }

    /**
     * Checks if the given SpectralPrime exists in this field.
     *
     * @param sp SpectralPrime
     * @return boolean
     */
    public boolean contains(SpectralPrime sp) {
        return elements.contains(sp);
    }

    /**
     * Returns the modular inverse of a SpectralPrime in the field.
     *
     * @param sp SpectralPrime
     * @return SpectralPrime or null
     */
    public SpectralPrime modularInverse(SpectralPrime sp) {
        try {
            BigInteger inv = sp.getPrimeValue().modInverse(modulus);
            return new SpectralPrime(inv);
        } catch (ArithmeticException ex) {
            Logger.warn("No modular inverse for: " + sp.getPrimeValue());
            return null;
        }
    }

    /**
     * Performs field addition modulo the harmonic modulus.
     *
     * @param a SpectralPrime
     * @param b SpectralPrime
     * @return SpectralPrime representing (a + b) mod modulus
     */
    public SpectralPrime add(SpectralPrime a, SpectralPrime b) {
        BigInteger result = a.getPrimeValue().add(b.getPrimeValue()).mod(modulus);
        return new SpectralPrime(result);
    }

    /**
     * Performs field multiplication modulo the harmonic modulus.
     *
     * @param a SpectralPrime
     * @param b SpectralPrime
     * @return SpectralPrime representing (a * b) mod modulus
     */
    public SpectralPrime multiply(SpectralPrime a, SpectralPrime b) {
        BigInteger result = a.getPrimeValue().multiply(b.getPrimeValue()).mod(modulus);
        return new SpectralPrime(result);
    }

    /**
     * Returns the number of elements in the field.
     *
     * @return int
     */
    public int size() {
        return elements.size();
    }

    public BigInteger getModulus() {
        return modulus;
    }

    public Set<SpectralPrime> getElements() {
        return new HashSet<>(elements);
    }
}
