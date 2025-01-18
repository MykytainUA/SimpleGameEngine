package org.mykytainua.simplegameengine.rendering.shaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * The ShaderUtilities class provides utility methods for working with shader
 * files. It contains methods to read shader source code from a file and return
 * it as an array of strings.
 */
public class ShaderUtilities {

    /**
     * Reads the shader source code from a file and returns it as an array of
     * strings. Each line from the shader source is stored as a separate element in
     * the array.
     * 
     * <p>This method opens the file specified by the given path, reads its contents
     * line by line, and adds each line to a list. Once the file is read, the list
     * is converted into an array and returned.</p>
     *
     * @param pathToShader the path to the shader source file
     * @return an array of strings containing the shader source code, each line as a
     *         separate element
     */
    public static String[] getShaderSourceAsString(String pathToShader) {

        // List to store shader source code line by line
        List<String> sourceCodeAsList = new LinkedList<String>();

        try (Scanner scanner = new Scanner(new File(pathToShader))) {
            // Read each line of the file and add it to the list
            while (scanner.hasNext()) {
                sourceCodeAsList.add(scanner.nextLine() + '\n');
            }
        } catch (FileNotFoundException e1) {
            // If the file is not found, print an error message
            System.err.println("Could not find shader source file:");
            System.err.println("Path: " + pathToShader);
            e1.printStackTrace();
        }

        // Return the shader source code as an array of strings
        return sourceCodeAsList.toArray(String[]::new);
    }
}
