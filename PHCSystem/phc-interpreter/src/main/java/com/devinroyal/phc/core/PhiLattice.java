/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package core;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import util.Logger;

/**
 * Models a φ-harmonic lattice using prime harmonics as structural vectors.
 * This lattice is used to simulate harmonic fields and quantum arithmetic topology.
 */
public class PhiLattice {

    private final List<BigDecimal[]> layers;
    private final BigInteger latticeModulus;
    private final int dimension;

    /**
     * Constructs a φ-harmonic lattice given a dimensionality and modulus.
     *
     * @param dimension the dimension of the lattice
     * @param modulus   the modulus under which harmonics are computed
     */
    public PhiLattice(int dimension, BigInteger modulus) {
        if (dimension <= 0 || modulus == null || !modulus.isProbablePrime(100)) {
            throw new IllegalArgumentException("Dimension must be > 0 and modulus must be a prime.");
        }
        this.dimension = dimension;
        this.latticeModulus = modulus;
        this.layers = new ArrayList<>();
        generateLattice();
    }

    /**
     * Generates the harmonic φ-lattice structure.
     */
    private void generateLattice() {
        BigDecimal phi = BigDecimal.valueOf((1 + Math.sqrt(5)) / 2);
        BigDecimal modDec = new BigDecimal(latticeModulus);

        for (int i = 1; i <= dimension; i++) {
            BigDecimal[] vector = new BigDecimal[dimension];
            for (int j = 0; j < dimension; j++) {
                BigDecimal angle = BigDecimal.valueOf(Math.PI * i * (j + 1));
                BigDecimal value = phi.pow(j + 1, MathContextHolder.MC)
                        .multiply(BigDecimal.valueOf(Math.sin(angle.doubleValue())), MathContextHolder.MC)
                        .remainder(modDec);
                vector[j] = value.setScale(10, RoundingMode.HALF_UP);
            }
            layers.add(vector);
        }

        Logger.info("Generated φ-lattice with " + dimension + " dimensions under modulus " + latticeModulus);
    }

    /**
     * Retrieves the i-th layer vector in the φ-lattice.
     *
     * @param index layer index
     * @return BigDecimal[]
     */
    public BigDecimal[] getLayer(int index) {
        if (index < 0 || index >= layers.size()) {
            Logger.warn("Invalid layer index: " + index);
            return new BigDecimal[dimension];
        }
        return layers.get(index);
    }

    /**
     * Returns the full lattice structure.
     *
     * @return List of vectors
     */
    public List<BigDecimal[]> getLattice() {
        return new ArrayList<>(layers);
    }

    public BigInteger getModulus() {
        return latticeModulus;
    }

    public int getDimension() {
        return dimension;
    }

    /**
     * Internal MathContext holder for precision control.
     */
    private static class MathContextHolder {
        static final java.math.MathContext MC = new java.math.MathContext(64, RoundingMode.HALF_UP);
    }
}
