/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package core;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import util.Logger;

/**
 * PrimeField represents a hyper-structured numerical field built on harmonic prime theory,
 * which extends classical fields by embedding spectral resonances and non-Euclidean transformations.
 */
public class PrimeField {

    private final List<BigDecimal> primeBasis;
    private final MathContext mc;
    private final Logger logger;

    public PrimeField(int maxPrimeCount, MathContext mc) {
        this.mc = mc;
        this.logger = new Logger();
        this.primeBasis = generatePrimeBasis(maxPrimeCount);
    }

    private List<BigDecimal> generatePrimeBasis(int limit) {
        List<BigDecimal> basis = new ArrayList<>();
        int count = 0, num = 2;

        while (count < limit) {
            if (isPrime(num)) {
                basis.add(new BigDecimal(num, mc));
                count++;
            }
            num++;
        }

        logger.info("Generated prime basis with " + count + " elements.");
        return basis;
    }

    private boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;

        for (int i = 5; i * i <= n; i += 6)
            if (n % i == 0 || n % (i + 2) == 0)
                return false;

        return true;
    }

    public BigDecimal spectralNorm(BigDecimal value) {
        try {
            BigDecimal sum = BigDecimal.ZERO;
            for (BigDecimal p : primeBasis) {
                BigDecimal harmonic = BigDecimal.ONE.divide(p.multiply(value.add(BigDecimal.ONE)), mc);
                sum = sum.add(harmonic, mc);
            }
            return sum;
        } catch (Exception e) {
            logger.error("Spectral norm calculation failed.", e);
            return BigDecimal.ZERO;
        }
    }

    public boolean isWithinField(BigDecimal x) {
        for (BigDecimal p : primeBasis) {
            if (x.remainder(p, mc).compareTo(BigDecimal.ZERO) == 0) {
                return true;
            }
        }
        return false;
    }

    public List<BigDecimal> getPrimeBasis() {
        return new ArrayList<>(primeBasis);
    }

    public MathContext getMathContext() {
        return mc;
    }
}
