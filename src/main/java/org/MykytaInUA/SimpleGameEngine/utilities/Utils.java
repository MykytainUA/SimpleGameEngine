package org.MykytaInUA.SimpleGameEngine.utilities;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.glu.GLU;

import static com.jogamp.opengl.GL2ES2.GL_FRAGMENT_SHADER;
import static com.jogamp.opengl.GL2ES2.GL_VERTEX_SHADER;
import static com.jogamp.opengl.GL4.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Utils {
	
	private static int counter = 0;
	
	public static int createShaderProgram(String vertexShaderPath, String fragmentShaderPath) {
		int vertexShader, fragmentShader, program;
		
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		String[] vertexShaderSource = readShaderSource(vertexShaderPath);
		String[] fragmentShaderSource = readShaderSource(fragmentShaderPath);
		
		vertexShader = gl.glCreateShader(GL_VERTEX_SHADER);
		fragmentShader = gl.glCreateShader(GL_FRAGMENT_SHADER);
		program = gl.glCreateProgram();
		
		gl.glShaderSource(vertexShader, vertexShaderSource.length, vertexShaderSource, null);
		gl.glCompileShader(vertexShader);
		Utils.printShaderLog(vertexShader);
		
		gl.glShaderSource(fragmentShader, fragmentShaderSource.length, fragmentShaderSource, null);
		gl.glCompileShader(fragmentShader);
		Utils.printShaderLog(fragmentShader);
		
		gl.glAttachShader(program, vertexShader);
		gl.glAttachShader(program, fragmentShader);
		
		gl.glLinkProgram(program);
		Utils.printProgramLog(program);
		
		gl.glDetachShader(program, vertexShader);
		gl.glDetachShader(program, fragmentShader);
		
		gl.glDeleteShader(vertexShader);
		gl.glDeleteShader(fragmentShader);
		
		return program;
	}
	
	public static String[] readShaderSource(String pathToShader) {

		List <String>sourceCodeAsList = new LinkedList<String>();

		try(Scanner scanner = new Scanner(new File(pathToShader)))
		{
			while(scanner.hasNext()) {
				sourceCodeAsList.add(scanner.nextLine() + '\n');
			}
		} catch (FileNotFoundException e1) {
			System.err.println("Could not find shader source file:");
			System.err.println("Path" + pathToShader);
			e1.printStackTrace();
		}

		return sourceCodeAsList.toArray(String[]::new);
	} 
	
	public static void printShaderLog(int shader) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		int[] len = new int[1];
		int [] charWritten = new int[1];
		byte[] log = null;
		
		// Determine the length of the shader compilation log
		gl.glGetShaderiv(shader, GL_INFO_LOG_LENGTH, len, 0);
		
		if(len[0] > 0) {
			log = new byte[len[0]];
			gl.glGetShaderInfoLog(shader, len[0], charWritten, 0, log, 0);
			System.out.println("Shader info Log:");
			for (int i = 0; i < log.length; i++) {
				System.out.print((char) log[i]);
			}
		}
	}
	
	public static void printProgramLog(int program) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		int[] len = new int[1];
		int [] charWritten = new int[1];
		byte[] log = null;
		
		// Determine the length of the shader compilation log
		gl.glGetProgramiv(program, GL_INFO_LOG_LENGTH, len, 0);
		
		if(len[0] > 0) {
			log = new byte[len[0]];
			gl.glGetProgramInfoLog(program, len[0], charWritten, 0, log, 0);
			System.out.println("Program info Log:");
			for (int i = 0; i < log.length; i++) {
				System.out.print((char) log[i]);
			}
		}
	}
	
	public static boolean checkOpenGLErrors() {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		boolean foundError = false;
		
		GLU glu = new GLU();
		int glError = gl.glGetError();
		
		while (glError != GL_NO_ERROR) {
			System.err.println("glError:" + glu.gluErrorString(glError));
			foundError = true;
			glError = gl.glGetError();
		}
		
		return foundError;
	}
	
	public static void saveFrontBuffer(GLAutoDrawable drawable, String filePath) {
        GL4 gl = drawable.getGL().getGL4();

        // Get the viewport dimensions
        int width = drawable.getSurfaceWidth();
        int height = drawable.getSurfaceHeight();

        // Create a ByteBuffer to hold the pixel data
        ByteBuffer buffer = ByteBuffer.allocateDirect(width * height * 4); // RGBA format

        // Read the front buffer
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
        counter++;
        filePath += counter + ".png";
        // Save the BufferedImage to a file
        try {
            ImageIO.write(image, "png", new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
