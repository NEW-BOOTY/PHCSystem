/*
 * Copyright © 2025 Devin B. Royal.
 * All Rights Reserved.
 */

package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Logger provides centralized, timestamped, and leveled logging across the PHC system.
 * Outputs to standard error stream with clear formatting.
 */
public final class Logger {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");

    private Logger() {
        // Prevent instantiation
    }

    private static String timestamp() {
        return LocalDateTime.now().format(FORMATTER);
    }

    public static void info(String message) {
        System.err.println("[INFO]  [" + timestamp() + "] " + message);
    }

    public static void warn(String message) {
        System.err.println("[WARN]  [" + timestamp() + "] " + message);
    }

    public static void error(String message) {
        System.err.println("[ERROR] [" + timestamp() + "] " + message);
    }

    public static void error(String message, Throwable t) {
        error(message);
        t.printStackTrace(System.err);
    }

    public static void debug(String message) {
        System.err.println("[DEBUG] [" + timestamp() + "] " + message);
    }
}
