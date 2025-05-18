/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package util;

import java.util.Objects;

/**
 * ComplexUtils provides robust utility methods for complex number operations.
 * Designed for precision, correctness, and symbolic compatibility with PHC calculations.
 */
public final class ComplexUtils {

    public static class Complex {
        public final double re;
        public final double im;

        public Complex(double re, double im) {
            this.re = re;
            this.im = im;
        }

        public Complex add(Complex other) {
            return new Complex(this.re + other.re, this.im + other.im);
        }

        public Complex subtract(Complex other) {
            return new Complex(this.re - other.re, this.im - other.im);
        }

        public Complex multiply(Complex other) {
            return new Complex(
                this.re * other.re - this.im * other.im,
                this.re * other.im + this.im * other.re
            );
        }

        public Complex divide(Complex other) {
            double denominator = other.re * other.re + other.im * other.im;
            if (denominator == 0) {
                Logger.error("Attempted division by zero complex number: " + other);
                throw new ArithmeticException("Division by zero complex number");
            }
            return new Complex(
                (this.re * other.re + this.im * other.im) / denominator,
                (this.im * other.re - this.re * other.im) / denominator
            );
        }

        public double magnitude() {
            return Math.hypot(re, im);
        }

        public double phase() {
            return Math.atan2(im, re);
        }

        public Complex conjugate() {
            return new Complex(re, -im);
        }

        public Complex exp() {
            return new Complex(
                Math.exp(re) * Math.cos(im),
                Math.exp(re) * Math.sin(im)
            );
        }

        public Complex log() {
            return new Complex(Math.log(this.magnitude()), this.phase());
        }

        public Complex pow(Complex exponent) {
            return this.log().multiply(exponent).exp();
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Complex)) return false;
            Complex other = (Complex) obj;
            return Double.compare(re, other.re) == 0 &&
                   Double.compare(im, other.im) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(re, im);
        }

        @Override
        public String toString() {
            return String.format("(%f + %fi)", re, im);
        }
    }

    private ComplexUtils() {
        // Prevent instantiation
    }
}
