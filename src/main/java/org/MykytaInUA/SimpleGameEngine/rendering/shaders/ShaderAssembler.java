package org.MykytaInUA.SimpleGameEngine.rendering.shaders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.MykytaInUA.SimpleGameEngine.objects.components.Component;
import org.MykytaInUA.SimpleGameEngine.objects.components.mesh.MeshComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.texture.SolidColorComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.texture.TextureComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.transform.PositionComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.transform.RotationComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.transform.SizeComponent;

// Assemble shader depending on list of dependencies
public class ShaderAssembler {
	
	private static String[] vertexShaderSource;
	private static String[] fragmentShaderSource;
	
	private static List<Component> componentsForShader = new ArrayList<Component>();
	
	private static Map<Class<? extends Component>, String> definesMap = new HashMap<Class<? extends Component>, String>();
	
	static {
		definesMap.put(PositionComponent.class, "#define POSITION_COMPONENT\n");
		definesMap.put(RotationComponent.class, "#define ROTATION_COMPONENT\n");
		definesMap.put(SizeComponent.class, "#define SIZE_COMPONENT\n");
		definesMap.put(SolidColorComponent.class, "#define SOLID_COLOR_COMPONENT\n");
		definesMap.put(TextureComponent.class, "#define TEXTURE_COMPONENT\n");
	}
	
	public static Shader getShaderByPath(String vertexShaderPath, String fragmentShaderPath, List<Component> components) {
		
		componentsForShader = components;
		
		vertexShaderSource = ShaderUtilities.getShaderSourceAsString(vertexShaderPath);
		fragmentShaderSource = ShaderUtilities.getShaderSourceAsString(fragmentShaderPath);
			
		vertexShaderSource = addDefines(vertexShaderSource);
		fragmentShaderSource = addDefines(fragmentShaderSource);
		
		Shader shader = new Shader(vertexShaderSource, fragmentShaderSource, components);
		
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
		for (Component component : componentsForShader) {
			if(definesMap.containsKey(component.getClass())) {
				sourceCodeAsList.add(macroPlaceholderIndex + 1, definesMap.get(component.getClass()));
			} else {
				
				// Ignore mesh components
				if(!MeshComponent.class.isAssignableFrom(component.getClass())) {
					System.out.println("Unknown component:" + component);
				} 
			}
		}
		
		return sourceCodeAsList.toArray(new String[0]);
	}
}
