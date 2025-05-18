/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package core;

import java.util.*;
import java.math.BigInteger;
import util.Logger;

/**
 * Represents the spectral signature of a prime number under the Prime Harmonics Calculus.
 * Encodes both temporal and frequency-domain interpretations of primes as spectral objects.
 */
public final class SpectralPrime {

    private final BigInteger primeValue;
    private final double frequencyComponent;
    private final double harmonicAmplitude;
    private final UUID id;

    /**
     * Constructs a SpectralPrime from a given BigInteger prime number.
     * Applies logarithmic and harmonic transforms to define spectral properties.
     *
     * @param prime BigInteger value that must be prime.
     * @throws IllegalArgumentException if the value is not prime or null.
     */
    public SpectralPrime(BigInteger prime) {
        if (prime == null || !prime.isProbablePrime(100)) {
            Logger.error("Attempted to create SpectralPrime with non-prime value: " + prime);
            throw new IllegalArgumentException("Input must be a valid prime number.");
        }

        this.primeValue = prime;
        this.frequencyComponent = computeFrequency(prime);
        this.harmonicAmplitude = computeAmplitude(prime);
        this.id = UUID.randomUUID();
    }

    private double computeFrequency(BigInteger p) {
        // Logarithmic frequency scaling
        return Math.log(p.doubleValue()) / Math.log(2);
    }

    private double computeAmplitude(BigInteger p) {
        // Prime-based harmonic strength (tunable kernel)
        return 1.0 / Math.sqrt(p.doubleValue());
    }

    public BigInteger getPrimeValue() {
        return primeValue;
    }

    public double getFrequencyComponent() {
        return frequencyComponent;
    }

    public double getHarmonicAmplitude() {
        return harmonicAmplitude;
    }

    public UUID getId() {
        return id;
    }

    /**
     * Returns a normalized signal signature of the SpectralPrime.
     * 
     * @return double array representing harmonic spectral trace.
     */
    public double[] generateSpectralSignature(int resolution) {
        if (resolution <= 0) {
            Logger.warn("Resolution must be positive. Defaulting to 256.");
            resolution = 256;
        }

        double[] signature = new double[resolution];
        for (int i = 0; i < resolution; i++) {
            double x = i / (double) resolution;
            signature[i] = harmonicAmplitude * Math.sin(2 * Math.PI * frequencyComponent * x);
        }
        return signature;
    }

    /**
     * Returns the Fourier-based identity representation of this spectral prime.
     * 
     * @return String summary of Fourier-transformed structure.
     */
    public String toFourierSignature() {
        return String.format("ℙ:%s → ƒ=%.4f, 𝒜=%.6f", primeValue, frequencyComponent, harmonicAmplitude);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SpectralPrime)) return false;
        SpectralPrime other = (SpectralPrime) obj;
        return this.primeValue.equals(other.primeValue);
    }

    @Override
    public int hashCode() {
        return primeValue.hashCode();
    }

    @Override
    public String toString() {
        return "SpectralPrime{" +
                "primeValue=" + primeValue +
                ", frequencyComponent=" + frequencyComponent +
                ", harmonicAmplitude=" + harmonicAmplitude +
                ", id=" + id +
                '}';
    }
}
