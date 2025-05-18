/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package visual;

import util.ComplexUtils;
import util.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 * OmegaSurface generates and visualizes the modulus of a PHC ζ-like function
 * across the complex plane, revealing omega-structured zero fields.
 */
public class OmegaSurface extends JPanel {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final BigDecimal RE_START = new BigDecimal("0.0");
    private static final BigDecimal RE_END = new BigDecimal("1.0");
    private static final BigDecimal IM_START = new BigDecimal("0.0");
    private static final BigDecimal IM_END = new BigDecimal("50.0");
    private static final int RESOLUTION = 800;
    private static final MathContext MC = new MathContext(40);

    private final Logger logger;
    private BufferedImage image;

    public OmegaSurface() {
        this.logger = new Logger();
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        generateSurface();
    }

    private void generateSurface() {
        logger.info("Generating Omega Surface...");
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < RESOLUTION; x++) {
            for (int y = 0; y < RESOLUTION; y++) {
                BigDecimal re = RE_START.add(
                        RE_END.subtract(RE_START, MC)
                                .multiply(BigDecimal.valueOf(x).divide(BigDecimal.valueOf(RESOLUTION), MC), MC), MC);

                BigDecimal im = IM_START.add(
                        IM_END.subtract(IM_START, MC)
                                .multiply(BigDecimal.valueOf(y).divide(BigDecimal.valueOf(RESOLUTION), MC), MC), MC);

                BigDecimal modulus = ComplexUtils.evaluateZetaModulus(re, im, MC);
                int colorVal = mapModulusToColor(modulus);
                image.setRGB(x, y, colorVal);
            }
        }

        logger.info("Omega Surface generated.");
    }

    private int mapModulusToColor(BigDecimal modulus) {
        int value = modulus.min(new BigDecimal("20")).multiply(BigDecimal.valueOf(12)).intValue();
        return new Color(value % 256, (value * 2) % 256, (value * 4) % 256).getRGB();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }

    public static void renderFrame() {
        JFrame frame = new JFrame("PHC: Omega Surface Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new OmegaSurface());
        frame.pack();
        frame.setVisible(true);
    }
}
