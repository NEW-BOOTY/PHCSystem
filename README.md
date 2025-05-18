Theoretical Goal

PHCSystem aims to model the latent prime-harmonic field as a symbolic language with computational parity to complex numbers and Hilbert spaces. Its ultimate purpose is to either unlock the structure of the Riemann Zeta zeros, or go beyond with a fully original prime harmonic logic calculus.

# Prime Harmonics Calculus (PHCSystem)

**PHCSystem** is a groundbreaking symbolic and geometric computational framework designed to explore the **hidden architecture of prime numbers**. This modular Java-based system combines symbolic logic, spectral prime operators, and dynamic zero-locator surfaces to investigate patterns potentially related to or transcending the Riemann Hypothesis.

---

## 📐 Project Structure

/PHCSystem/
├── phc-interpreter/
│ └── src/
│ ├── core/
│ │ ├── SpectralPrime.java
│ │ ├── PrimeOperator.java
│ │ ├── PrimeField.java
│ │ └── PhiLattice.java
│ ├── engine/
│ │ ├── PHCInterpreter.java
│ │ ├── SymbolicParser.java
│ │ └── ExpressionNode.java
│ ├── logic/
│ │ ├── PHCLogicEvaluator.java
│ │ └── Proposition.java
│ └── util/
│ ├── ComplexUtils.java
│ └── Logger.java
│ └── Main.java
├── PHC-PrimeHarmonicsCalculus/
│ └── src/
│ ├── core/
│ │ └── PrimeField.java
│ │ └── PHCInterpreter.java
│ ├── logic/
│ │ └── OmegaZeroLocator.java
│ ├── visual/
│ │ └── OmegaSurface.java
│ └── PHCLauncher.java
└── pom.xml


---

## 🚀 Features

- **Symbolic Interpreter**: Parse, transform, and evaluate abstract prime harmonic expressions.
- **Zero Locator**: Dynamic geometric surface plotting of zeta-analogic structures.
- **Spectral Prime Operators**: Advanced operators for prime field exploration.
- **Modular Engine**: Designed to scale into mathematical logic and visualization layers.
- **High-Precision Complex Utilities**: Custom utilities for arithmetic, roots, and convergence.
- **Custom Logging Engine**: Thread-safe, color-coded, verbosity-controlled logs.
- **Maven Integration**: Fully Mavenized project structure with multi-module compatibility.

---

## 🛠️ Build & Run

### ✅ Prerequisites

- Java 11+
- Maven 3.6+

### ⚙️ Compile and Run

From the root of your repository:

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="Main"
