package org.mykytainua.simplegameengine.objects.components.texture;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.joml.Vector3f;
import org.joml.Vector4f;
import org.mykytainua.simplegameengine.global.DataType;
import org.mykytainua.simplegameengine.global.AttributeDefinition;
import org.mykytainua.simplegameengine.objects.DataProvider;
import org.mykytainua.simplegameengine.objects.LocalDataProvider;
import org.mykytainua.simplegameengine.objects.components.Component;
import org.mykytainua.simplegameengine.objects.components.ComponentLayout;
import org.mykytainua.simplegameengine.objects.components.ComponentMetadata;
import org.mykytainua.simplegameengine.objects.components.ShaderComponent;
import org.mykytainua.simplegameengine.rendering.OpenGLBufferType;

/**
 * The {@code SolidColorComponent} class represents a material component that
 * defines a solid color for rendering 3D objects.
 *
 * <p>This component is used to assign a specific color to an object in a
 * rendering pipeline. The color is defined using a {@link Vector4f}, which
 * includes red, green, blue, and alpha (transparency) channels.</p>
 *
 * <p>Implements the {@link RenderMaterialComponent} interface to provide
 * functionality for rendering materials in the context of a 3D engine.</p>
 */
public class SolidColorComponent implements RenderMaterialComponent, 
                                            ShaderComponent {

    private static final ComponentMetadata METADATA;
    private final DataProvider rawData;
    
    static {
        METADATA = new ComponentMetadata(new ComponentLayout(
                new AttributeDefinition(DataType.FLOAT_VEC4, 
                                        "instanceColor",
                                        OpenGLBufferType.VBO,
                                        true)));
    }

    /**
     * The size of data per vertex, in number of floats (4 floats for RGBA).
     */
    public static final int DATA_PER_VERTEX_SIZE = 4;

    /**
     * The name of the attribute pointer associated with the color data in the
     * shader program.
     */
    public static final String ATTRIBUTE_POINTER_NAME = "instanceColor";
    
    public static final String PREPROCESSOR_DEFINE = "SOLID_COLOR_COMPONENT";

    /**
     * Constructs a {@code SolidColorComponent} with the specified color.
     *
     * @param color the RGBA color as a {@link Vector4f}
     */
    public SolidColorComponent(Vector4f color) {
        this.rawData = new LocalDataProvider(METADATA);
        this.setColor(color);
    }

    /**
     * Returns the RGBA color of this component.
     *
     * @return the color as a {@link Vector4f}
     */
    public Vector4f getColor() { 
        ByteBuffer colorData = this.getComponentData("instanceColor");
        
        Vector4f col = new Vector4f();

        for(int i = 0; i < 4; i++) {
            col.setComponent(i, colorData.getFloat());
        }
        
        return col;
    }

    /**
     * Sets the RGBA color for this component.
     *
     * @param color the new color as a {@link Vector4f}
     */
    public void setColor(Vector4f color) { 
        ByteBuffer buff = ByteBuffer.allocate(DataType.FLOAT_VEC4.getByteSize()).order(ByteOrder.nativeOrder());
        
        for(int i = 0; i < 4; i++) {
            buff.putFloat(color.get(i));
        }
        
        buff.flip();
        
        this.rawData.setRawData(buff, "instanceColor");
    }

    /**
     * Creates a deep copy of this component, ensuring its state is fully cloned.
     *
     * @return a new {@code SolidColorComponent} with the same color
     */
    @Override
    public Component deepCopy() {
        return new SolidColorComponent(new Vector4f(this.getColor()));
    }


    @Override
    public String getPreprocessorDefine() {
        return PREPROCESSOR_DEFINE;
    }

    @Override
    public ComponentMetadata getComponentMetadata() {
        // TODO Auto-generated method stub
        return METADATA;
    }
    
    @Override
    public ByteBuffer getComponentData(String name) {       
        return this.rawData.getRawData(name).order(ByteOrder.nativeOrder());
    }
}
