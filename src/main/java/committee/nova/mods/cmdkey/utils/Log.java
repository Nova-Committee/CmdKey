package committee.nova.mods.cmdkey.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import static committee.nova.mods.cmdkey.CmdKey.LOGGER;

/**
 * @Project: CmdKey
 * @Author: cnlimiter
 * @CreateTime: 2025/3/15 22:17
 * @Description:
 */
public class Log {

    public static void info(String message, String... params) {
        LOGGER.log(Level.INFO, message, params);
    }
    public static void warn(String message, String... params) {
        LOGGER.log(Level.WARNING, message, params);
    }
    public static void error(String message, String... params) {
        LOGGER.log(Level.SEVERE, message, params);
    }

    public static void info(String message, Object... params) {
        LOGGER.log(Level.INFO, message, params);
    }
    public static void warn(String message, Object... params) {
        LOGGER.log(Level.WARNING, message, params);
    }
    public static void error(String message, Object... params) {
        LOGGER.log(Level.SEVERE, message, params);
    }

    public static void info(String message, Throwable... params) {
        LOGGER.log(Level.INFO, message, params);
    }
    public static void warn(String message, Throwable... params) {
        LOGGER.log(Level.WARNING, message, params);
    }
    public static void error(String message, Throwable... params) {
        LOGGER.log(Level.SEVERE, message, params);
    }

    public static void info(String message) {
        LOGGER.log(Level.INFO, message);
    }
    public static void warn(String message) {
        LOGGER.log(Level.WARNING, message);
    }
    public static void error(String message) {
        LOGGER.log(Level.SEVERE, message);
    }
}
