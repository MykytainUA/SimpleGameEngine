package org.mykytainua.simplegameengine.utilities;

import java.nio.FloatBuffer;
import org.mykytainua.simplegameengine.settings.Settings;

/**
 * Logger class provides logging functionality for printing buffer data to the console.
 * It checks whether logging is active and prints the data contained in a FloatBuffer.
 */
public class Logger {

    /**
     * Prints the content of a FloatBuffer to the console with a provided info label.
     * This method only executes if logging is enabled as defined in the Settings class.
     *
     * @param info A string that provides information to be printed before the buffer data.
     * @param dataBuffer A FloatBuffer containing the data to be printed.
     */
    public static void printBufferData(String info, FloatBuffer dataBuffer) {

        // Check if logging is enabled.
        if (Settings.IS_LOGGING_ACTIVE) {
            // Print the provided info message.
            System.out.println(info);

            // Print the contents of the buffer.
            while (dataBuffer.hasRemaining()) {
                System.out.print(dataBuffer.get() + " ");
            }

            // Print a newline after the data.
            System.out.println("");

            // Rewind the buffer so that it can be read again if necessary.
            dataBuffer.rewind();
        }
    }
}
