package org.MykytaInUA.SimpleGameEngine.rendering.shaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ShaderUtilities {
	public static String[] getShaderSourceAsString(String pathToShader) {

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
}
