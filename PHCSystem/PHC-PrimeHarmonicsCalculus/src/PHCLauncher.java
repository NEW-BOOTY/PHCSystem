/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

import visual.OmegaSurface;
import logic.OmegaZeroLocator;
import core.PHCInterpreter;
import util.Logger;

/**
 * PHCLauncher is the unified entry point to Prime Harmonics Calculus.
 * It initiates PHC visualizations, logic zero-finding analysis, and interpreter interactions.
 */
public class PHCLauncher {

    private static final Logger logger = new Logger();

    public static void main(String[] args) {
        logger.info("=== Prime Harmonics Calculus (PHC) Framework Booting ===");

        try {
            if (args.length == 0) {
                logger.info("Launching OmegaSurface visualizer...");
                OmegaSurface.renderFrame();
            } else if ("locate-zeros".equalsIgnoreCase(args[0])) {
                logger.info("Running OmegaZeroLocator...");
                OmegaZeroLocator.locateZeros();
            } else if ("interpreter".equalsIgnoreCase(args[0])) {
                logger.info("Initializing PHCInterpreter...");
                PHCInterpreter interpreter = new PHCInterpreter();
                interpreter.run();
            } else {
                logger.error("Unknown command: " + args[0]);
            }

        } catch (Exception e) {
            logger.error("Fatal error encountered in PHCLauncher", e);
        }

        logger.info("=== PHC Framework Execution Complete ===");
    }
}
