/*
 * Copyright © 2024 Devin B. Royal.
 * All Rights Reserved.
 *
 * Logger.java
 * Central PHC logging class for standardized terminal output and traceability.
 */

package symbolic.log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Logger {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Logger() {
        // Prevent instantiation
    }

    public static void info(String message) {
        log("INFO", message);
    }

    public static void warn(String message) {
        log("WARN", message);
    }

    public static void error(String message) {
        log("ERROR", message);
    }

    private static void log(String level, String message) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        System.out.printf("[%s] [%s]: %s%n", timestamp, level, message);
    }
}
