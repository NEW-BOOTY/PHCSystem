/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package logic;

import util.ComplexUtils;
import util.Logger;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

/**
 * OmegaZeroLocator implements numerical methods to locate zero crossings of
 * Prime Harmonics Calculus functions within the Omega complex lattice,
 * simulating critical behavior akin to the Riemann Zeta function's non-trivial zeros.
 */
public class OmegaZeroLocator {

    private static final BigDecimal STEP = new BigDecimal("0.0005");
    private static final int MAX_ITER = 100000;
    private final Logger logger;
    private final MathContext mc;

    public OmegaZeroLocator(MathContext mc) {
        this.mc = mc;
        this.logger = new Logger();
        logger.info("OmegaZeroLocator initialized.");
    }

    public ComplexUtils.SurfaceResponse locateOmegaProjection(BigDecimal input) {
        try {
            BigDecimal x = input;
            BigDecimal y = new BigDecimal("0.5");
            for (int i = 0; i < MAX_ITER; i++) {
                BigDecimal real = ComplexUtils.evaluateReZetaLike(x, y, mc);
                BigDecimal imag = ComplexUtils.evaluateImZetaLike(x, y, mc);

                if (real.abs().compareTo(STEP) < 0 && imag.abs().compareTo(STEP) < 0) {
                    logger.debug("Zero found at: Re=" + x + ", Im=" + y);
                    return ComplexUtils.SurfaceResponse.success(x, y);
                }

                y = y.add(STEP, mc);
                if (y.compareTo(new BigDecimal("100")) > 0) break; // escape on domain exhaustion
            }
        } catch (Exception e) {
            logger.error("Omega projection failed.", e);
        }

        return ComplexUtils.SurfaceResponse.error("No zero found within iteration bounds.");
    }

    public List<ComplexUtils.SurfaceResponse> scanCriticalLine(BigDecimal start, BigDecimal end) {
        List<ComplexUtils.SurfaceResponse> results = new ArrayList<>();
        BigDecimal y = start;

        while (y.compareTo(end) <= 0) {
            BigDecimal real = ComplexUtils.evaluateReZetaLike(new BigDecimal("0.5"), y, mc);
            BigDecimal imag = ComplexUtils.evaluateImZetaLike(new BigDecimal("0.5"), y, mc);

            if (real.abs().compareTo(STEP) < 0 && imag.abs().compareTo(STEP) < 0) {
                results.add(ComplexUtils.SurfaceResponse.success(new BigDecimal("0.5"), y));
                logger.debug("Critical line zero: Im=" + y);
            }

            y = y.add(STEP, mc);
        }

        return results;
    }
}
