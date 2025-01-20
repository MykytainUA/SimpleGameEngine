package org.mykytainua.simplegameengine.rendering.shaders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mykytainua.simplegameengine.objects.components.Component;
import org.mykytainua.simplegameengine.objects.components.mesh.Mesh;
import org.mykytainua.simplegameengine.objects.components.texture.SolidColorComponent;
import org.mykytainua.simplegameengine.objects.components.texture.TextureComponent;
import org.mykytainua.simplegameengine.objects.components.transform.PositionComponent;
import org.mykytainua.simplegameengine.objects.components.transform.RotationComponent;
import org.mykytainua.simplegameengine.objects.components.transform.SizeComponent;

/**
 * The ShaderAssembler class dynamically constructs shaders based on the
 * components that need to be included in the rendering pipeline for a game
 * object. It prepares vertex and fragment shader source code by conditionally
 * adding preprocessor definitions based on the components that are attached to
 * the object.
 * 
 * <p>The components, such as Position, Rotation, Size, SolidColor, and Texture,
 * each correspond to specific shader functionality. The ShaderAssembler class
 * ensures that the shader code is customized to include the necessary defines
 * for the components that are active. This allows the shaders to only include
 * the functionality required for rendering a specific object, based on its
 * components.</p>
 * 
 * <p>It takes a list of components for the object and constructs the
 * corresponding shader by adding the proper preprocessor defines into the
 * shader source code. The defines are inserted into the source code where a
 * placeholder "%ADD_DEFINES%" is found, enabling the necessary shader features
 * dynamically.</p>
 * 
 * <p>This class abstracts the process of shader creation, making it easy to
 * reuse shaders across different game objects by simply passing in the
 * appropriate list of components.</p>
 *
 * @see StaticShader
 * @see Component
 */
public class ShaderAssembler {

    // StaticShader source code variables
    private static String[] vertexShaderSource;
    private static String[] fragmentShaderSource;

    // List of components to be used in the shader
    private static List<Component> componentsForShader = new ArrayList<Component>();

    // Mapping of component types to their corresponding preprocessor defines
    private static Map<Class<? extends Component>, String> definesMap = 
           new HashMap<Class<? extends Component>, String>();

    // Static block to initialize the defines map with component-class to define
    // string mappings
    static {
        definesMap.put(PositionComponent.class, "#define POSITION_COMPONENT\n");
        definesMap.put(RotationComponent.class, "#define ROTATION_COMPONENT\n");
        definesMap.put(SizeComponent.class, "#define SIZE_COMPONENT\n");
        definesMap.put(SolidColorComponent.class, "#define SOLID_COLOR_COMPONENT\n");
        definesMap.put(TextureComponent.class, "#define TEXTURE_COMPONENT\n");
    }

    /**
     * This method generates a shader by reading the shader source files, adding
     * component-specific defines, and returning the corresponding StaticShader object.
     *
     * @param vertexShaderPath   the path to the vertex shader source file
     * @param fragmentShaderPath the path to the fragment shader source file
     * @param components         the list of components associated with the object
     * @return the generated StaticShader object with the appropriate defines
     */
    public static StaticShader getShaderByPath(String vertexShaderPath, String fragmentShaderPath,
            List<Component> components) {

        // Assign the components for the shader
        componentsForShader = components;

        // Load shader source code from file
        vertexShaderSource = ShaderUtilities.getShaderSourceAsString(vertexShaderPath);
        fragmentShaderSource = ShaderUtilities.getShaderSourceAsString(fragmentShaderPath);

        // Add component-specific defines to the shader source
        vertexShaderSource = addDefines(vertexShaderSource);
        fragmentShaderSource = addDefines(fragmentShaderSource);

        // Create and return the shader
        StaticShader staticShader = new StaticShader(vertexShaderSource, fragmentShaderSource, components);
        return staticShader;
    }

    /**
     * This method processes the shader source code and adds necessary preprocessor
     * defines for the components being used in the shader.
     *
     * @param source the original shader source code
     * @return the shader source code with added preprocessor defines
     */
    private static String[] addDefines(String[] source) {

        List<String> sourceCodeAsList = new ArrayList<String>();

        // Convert the source array to a list for easier manipulation
        for (int i = 0; i < source.length; i++) {
            sourceCodeAsList.add(source[i]);
        }

        // Locate the macro placeholder in the source code
        int macroPlaceholderIndex = -1;
        for (String lineOfCode : sourceCodeAsList) {
            if (lineOfCode.contains("%ADD_DEFINES%")) {
                macroPlaceholderIndex = sourceCodeAsList.indexOf(lineOfCode);
            }
        }

        // Add the appropriate defines for the components
        for (Component component : componentsForShader) {
            if (definesMap.containsKey(component.getClass())) {
                sourceCodeAsList.add(macroPlaceholderIndex + 1, 
                                     definesMap.get(component.getClass()));
            } else {
                // If the component is a Mesh, ignore it as it doesn't require a define
                if (!Mesh.class.isAssignableFrom(component.getClass())) {
                    System.out.println("Unknown component:" + component);
                }
            }
        }

        // Return the modified source code as an array
        return sourceCodeAsList.toArray(new String[0]);
    }
}
