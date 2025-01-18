package org.mykytainua.simplegameengine.utilities;

import static com.jogamp.opengl.GL2ES2.GL_FRAGMENT_SHADER;
import static com.jogamp.opengl.GL2ES2.GL_VERTEX_SHADER;
import static com.jogamp.opengl.GL4.GL_INFO_LOG_LENGTH;
import static com.jogamp.opengl.GL4.GL_NO_ERROR;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.glu.GLU;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 * Utility class that provides various helper methods for OpenGL shader
 * handling, error checking, and saving images.
 */
public class Utils {

    // Counter for the number of images saved (used to name output files uniquely)
    private static int counter = 0;

    /**
     * Creates a shader program using the specified vertex and fragment shader
     * paths.
     *
     * @param vertexShaderPath   Path to the vertex shader file
     * @param fragmentShaderPath Path to the fragment shader file
     * @return The compiled shader program ID
     */
    public static int createShaderProgram(String vertexShaderPath, String fragmentShaderPath) {
        int vertexShader;
        int fragmentShader;
        int program;

        GL4 gl = (GL4) GLContext.getCurrentGL();

        // Read the shader source files
        final String[] vertexShaderSource = readShaderSource(vertexShaderPath);
        final String[] fragmentShaderSource = readShaderSource(fragmentShaderPath);

        // Create and compile vertex and fragment shaders
        vertexShader = gl.glCreateShader(GL_VERTEX_SHADER);
        fragmentShader = gl.glCreateShader(GL_FRAGMENT_SHADER);
        program = gl.glCreateProgram();

        gl.glShaderSource(vertexShader, vertexShaderSource.length, vertexShaderSource, null);
        gl.glCompileShader(vertexShader);
        Utils.printShaderLog(vertexShader);

        gl.glShaderSource(fragmentShader, fragmentShaderSource.length, fragmentShaderSource, null);
        gl.glCompileShader(fragmentShader);
        Utils.printShaderLog(fragmentShader);

        // Attach shaders to the program and link it
        gl.glAttachShader(program, vertexShader);
        gl.glAttachShader(program, fragmentShader);

        gl.glLinkProgram(program);
        Utils.printProgramLog(program);

        // Detach and delete shaders after linking
        gl.glDetachShader(program, vertexShader);
        gl.glDetachShader(program, fragmentShader);

        gl.glDeleteShader(vertexShader);
        gl.glDeleteShader(fragmentShader);

        return program;
    }

    /**
     * Reads the source code from a shader file.
     *
     * @param pathToShader Path to the shader file
     * @return An array of strings containing the shader source code
     */
    public static String[] readShaderSource(String pathToShader) {
        List<String> sourceCodeAsList = new LinkedList<String>();

        try (Scanner scanner = new Scanner(new File(pathToShader))) {
            while (scanner.hasNext()) {
                sourceCodeAsList.add(scanner.nextLine() + '\n');
            }
        } catch (FileNotFoundException e1) {
            System.err.println("Could not find shader source file:");
            System.err.println("Path: " + pathToShader);
            e1.printStackTrace();
        }

        return sourceCodeAsList.toArray(String[]::new);
    }

    /**
     * Prints the log of a shader compilation to the console.
     *
     * @param shader The shader ID
     */
    public static void printShaderLog(int shader) {
        GL4 gl = (GL4) GLContext.getCurrentGL();

        int[] len = new int[1];
        int[] charWritten = new int[1];
        byte[] log = null;

        // Get the length of the shader's log
        gl.glGetShaderiv(shader, GL_INFO_LOG_LENGTH, len, 0);

        if (len[0] > 0) {
            log = new byte[len[0]];
            gl.glGetShaderInfoLog(shader, len[0], charWritten, 0, log, 0);
            System.out.println("Shader info Log:");
            for (int i = 0; i < log.length; i++) {
                System.out.print((char) log[i]);
            }
        }
    }

    /**
     * Prints the log of a shader program linking to the console.
     *
     * @param program The shader program ID
     */
    public static void printProgramLog(int program) {
        GL4 gl = (GL4) GLContext.getCurrentGL();

        int[] len = new int[1];
        int[] charWritten = new int[1];
        byte[] log = null;

        // Get the length of the program's log
        gl.glGetProgramiv(program, GL_INFO_LOG_LENGTH, len, 0);

        if (len[0] > 0) {
            log = new byte[len[0]];
            gl.glGetProgramInfoLog(program, len[0], charWritten, 0, log, 0);
            System.out.println("Program info Log:");
            for (int i = 0; i < log.length; i++) {
                System.out.print((char) log[i]);
            }
        }
    }

    /**
     * Checks for OpenGL errors and prints them to the console.
     *
     * @return True if there were any OpenGL errors, false otherwise
     */
    public static boolean checkOpenGLErrors() {
        GL4 gl = (GL4) GLContext.getCurrentGL();

        boolean foundError = false;

        GLU glu = new GLU();
        int glError = gl.glGetError();

        // Loop through all OpenGL errors
        while (glError != GL_NO_ERROR) {
            System.err.println("glError: " + glu.gluErrorString(glError));
            foundError = true;
            glError = gl.glGetError();
        }

        return foundError;
    }

    /**
     * Saves the content of the front buffer (current rendered image) to a PNG file.
     *
     * @param drawable The GLAutoDrawable that contains the OpenGL context
     * @param filePath The file path where the image should be saved
     */
    public static void saveFrontBuffer(GLAutoDrawable drawable, String filePath) {
        GL4 gl = drawable.getGL().getGL4();

        // Get the viewport dimensions
        int width = drawable.getSurfaceWidth();
        int height = drawable.getSurfaceHeight();

        // Create a ByteBuffer to hold the pixel data
        ByteBuffer buffer = ByteBuffer.allocateDirect(width * height * 4); // RGBA format

        // Read the front buffer into the ByteBuffer
        gl.glReadPixels(0, 0, width, height, GL4.GL_RGBA, GL4.GL_UNSIGNED_BYTE, buffer);

        // Flip the buffer for correct image orientation
        buffer.rewind();

        // Create a BufferedImage from the ByteBuffer
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int r = buffer.get() & 0xFF;
                int g = buffer.get() & 0xFF;
                int b = buffer.get() & 0xFF;
                int a = buffer.get() & 0xFF;
                int argb = (a << 24) | (r << 16) | (g << 8) | b;
                image.setRGB(x, height - 1 - y, argb); // Flip the image vertically
            }
        }

        // Increment the counter to avoid overwriting files
        counter++;
        filePath += counter + ".png";

        // Save the BufferedImage to the specified file
        try {
            ImageIO.write(image, "png", new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

