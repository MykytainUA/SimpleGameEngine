package org.mykytainua.simplegameengine.utilities;

import java.util.logging.*;

public class ColoredConsoleHandler extends ConsoleHandler {
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";

    @Override
    public void publish(LogRecord record) {
        String color;
        Level level = record.getLevel();

        if (level == Level.SEVERE) {
            color = RED; // Red for severe errors
        } else if (level == Level.WARNING) {
            color = YELLOW; // Yellow for warnings
        } else if (level == Level.INFO) {
            color = BLUE; // Blue for info messages
        } else if (level == Level.CONFIG) {
            color = CYAN; // Cyan for config messages
        } else {
            color = GREEN; // Green for fine, finer, finest (debugging)
        }

        System.out.println(color + getFormatter().format(record) + RESET);
    }
}

