package org.MykytaInUA.SimpleGameEngine.rendering.shaders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.Position;

import org.joml.Vector3f;
import org.joml.Vector4f;
import org.MykytaInUA.SimpleGameEngine.objects.components.Component;
import org.MykytaInUA.SimpleGameEngine.objects.components.texture.SolidColorComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.texture.TextureComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.transform.PositionComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.transform.RotationComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.transform.SizeComponent;

// Assemble shader depending on list of dependencies
public class ShaderAssembler {
	
	private static String[] vertexShaderSource;
	private static String[] fragmentShaderSource;
	
	private static Component[] componentsForShader;
	
	private static Map<Class<? extends Component>, String> definesMap = new HashMap<Class<? extends Component>, String>();
	
	static {
		definesMap.put(PositionComponent.class, "#define POSITION_COMPONENT\n");
		definesMap.put(RotationComponent.class, "#define ROTATION_COMPONENT\n");
		definesMap.put(SizeComponent.class, "#define SIZE_COMPONENT\n");
		definesMap.put(SolidColorComponent.class, "#define SOLID_COLOR_COMPONENT\n");
		definesMap.put(TextureComponent.class, "#define TEXTURE_COMPONENT\n");
	}
	
	public static Shader getShaderByPath(String vertexShaderPath, String fragmentShaderPath, Component[] components) {
		
		vertexShaderSource = ShaderUtilities.getShaderSourceAsString(vertexShaderPath);
		fragmentShaderSource = ShaderUtilities.getShaderSourceAsString(fragmentShaderPath);
		
		// Temporary add components
		componentsForShader = new Component[4];
		
		componentsForShader[0] = new PositionComponent(new Vector3f());
		componentsForShader[1] = new RotationComponent(new Vector3f());
		componentsForShader[2] = new SizeComponent(new Vector3f());
		componentsForShader[3] = new SolidColorComponent(new Vector4f());
		// 
		
		vertexShaderSource = addDefines(vertexShaderSource);
		fragmentShaderSource = addDefines(fragmentShaderSource);
		
		Shader shader= new Shader(vertexShaderSource, fragmentShaderSource);
		
		return shader;
	}
	
	private static String[] addDefines(String[] source) {
		
		List<String> sourceCodeAsList = new ArrayList<String>();
		
		for (int i = 0; i < source.length; i++) {
			sourceCodeAsList.add(source[i]);
		}
		
		// Find macros placeholder
		int macroPlaceholderIndex = -1;
		for(String lineOfCode : sourceCodeAsList) {
			if(lineOfCode.contains("%ADD_DEFINES%")) {
				macroPlaceholderIndex = sourceCodeAsList.indexOf(lineOfCode);
			}
		}
		
		// Form insertion data (defines)
		for (int i = 0; i < componentsForShader.length; i++) {
			if(definesMap.containsKey(componentsForShader[i].getClass())) {
				sourceCodeAsList.add(macroPlaceholderIndex + 1, definesMap.get(componentsForShader[i].getClass()));
			} else {
				System.out.println("Unknown component");
			}
		}
		
		return sourceCodeAsList.toArray(new String[0]);
	}
}
