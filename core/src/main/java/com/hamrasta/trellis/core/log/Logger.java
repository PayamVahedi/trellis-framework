package com.hamrasta.trellis.core.log;


public class Logger {
    private static org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(Logger.class);
    private static String getFileName() {
        StackTraceElement[] stackTraceElement = Thread.currentThread().getStackTrace();
        if (stackTraceElement.length > 3) {
            return stackTraceElement[3].getFileName();
        }
        return "";
    }
    private static String getMethodName() {
        StackTraceElement[] stackTraceElement = Thread.currentThread().getStackTrace();
        if (stackTraceElement.length > 3) {
            return stackTraceElement[3].getMethodName();
        }
        return "";
    }
    public static void debug(String identifier, String message) {
        log(0, String.format("(%s)-[%s]-{%s}->%s", getFileName(), getMethodName(), identifier, message), null);
    }
    public static void debug(String identifier, String message, Throwable t) {
        log(0, String.format("(%s)-[%s]-{%s}->%s", getFileName(), getMethodName(), identifier, message), t);
    }
    public static void info(String identifier, String message) {
        log(1, String.format("(%s)-[%s]-{%s}->%s", getFileName(), getMethodName(), identifier, message), null);
    }
    public static void info(String identifier, String message, Throwable t) {
        log(1, String.format("(%s)-[%s]-{%s}->%s", getFileName(), getMethodName(), identifier, message), t);
    }
    public static void error(String identifier, String message) {
        log(2, String.format("(%s)-[%s]-{%s}->%s", getFileName(), getMethodName(), identifier, message), null);
    }
    public static void error(String identifier, String message, Throwable t) {
        log(2, String.format("(%s)-[%s]-{%s}->%s", getFileName(), getMethodName(), identifier, message), t);
    }
    public static void debug(String className, String methodName, String identifier, String message) {
        log(0, String.format("(%s)-[%s]-{%s}->%s", className, methodName, identifier, message), null);
    }
    public static void debug(String className, String methodName, String identifier, String message, Throwable t) {
        log(0, String.format("(%s)-[%s]-{%s}->%s", className, methodName, identifier, message), t);
    }
    public static void info(String className, String methodName, String identifier, String message) {
        log(1, String.format("(%s)-[%s]-{%s}->%s", className, methodName, identifier, message), null);
    }
    public static void info(String className, String methodName, String identifier, String message, Throwable t) {
        log(1, String.format("(%s)-[%s]-{%s}->%s", className, methodName, identifier, message), t);
    }
    public static void error(String className, String methodName, String identifier, String message) {
        log(2, String.format("(%s)-[%s]-{%s}->%s", className, methodName, identifier, message), null);
    }
    public static void error(String className, String methodName, String identifier, String message, Throwable t) {
        log(2, String.format("(%s)-[%s]-{%s}->%s", className, methodName, identifier, message), t);
    }
    private static void log(int type, String message, Throwable t) {
        if (t != null) {
            if (type == 0)
                logger.debug(message, t);
            else if (type == 1)
                logger.info(message, t);
            else if (type == 2)
                logger.error(message, t);
        }
        else {
            if (type == 0)
                logger.debug(message);
            else if (type == 1)
                logger.info(message);
            else if (type == 2)
                logger.error(message);
        }
    }
}
