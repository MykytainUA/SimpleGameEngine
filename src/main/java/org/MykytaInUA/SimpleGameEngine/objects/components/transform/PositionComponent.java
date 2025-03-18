package org.mykytainua.simplegameengine.objects.components.transform;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.joml.Vector3f;
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
 * Represents the position of a 3D object in the game engine. The position is
 * stored as a {@link Vector3f}, and this component provides methods for
 * managing and retrieving position-related data.
 */
public class PositionComponent implements Transform,                                      
                                          ShaderComponent {
    
    private static final ComponentMetadata METADATA;
    private final DataProvider rawData;
    
    static {
        METADATA = new ComponentMetadata(new ComponentLayout(
                new AttributeDefinition(DataType.FLOAT_VEC3, 
                                        "instancePosition",  
                                        OpenGLBufferType.VBO,
                                        true)));
    }

    /** The name of the attribute pointer for this component in the shader. */
    public static final String ATTRIBUTE_POINTER_NAME = "instancePosition";
    
    public static final String PREPROCESSOR_DEFINE = "POSITION_COMPONENT";

    /**
     * Constructs a {@code PositionComponent} with the given position.
     *
     * @param position the position vector to initialize this component with.
     */
    public PositionComponent(Vector3f position) {
        this.rawData = new LocalDataProvider(METADATA);
        this.setPosition(position);
    }

    /**
     * Retrieves the position vector of this component.
     *
     * @return the current position vector.
     */
    public Vector3f getPosition() {
        ByteBuffer positionData = this.getComponentData("instancePosition");
        Vector3f pos = new Vector3f();
        
        for(int i = 0; i < 3; i++) {
            pos.setComponent(i, positionData.getFloat());
        }
        
        return pos;
    }

    /**
     * Updates the position vector of this component.
     *
     * @param position the new position vector.
     */
    public void setPosition(Vector3f position) {
        ByteBuffer buff = ByteBuffer.allocate(DataType.FLOAT_VEC3.getByteSize()).order(ByteOrder.nativeOrder());
        
        for(int i = 0; i < 3; i++) {
            buff.putFloat(position.get(i));
        }
        
        buff.flip();
        
        this.rawData.setRawData(buff, "instancePosition");
    }

    /**
     * Creates a deep copy of this position component.
     *
     * @return a new {@code PositionComponent} instance with identical position
     *         data.
     */
    @Override
    public Component deepCopy() {
        return new PositionComponent(new Vector3f(this.getPosition()));
    }

    @Override
    public String getPreprocessorDefine() {
        return PREPROCESSOR_DEFINE;
    }

    @Override
    public ComponentMetadata getComponentMetadata() {
        return METADATA;
    }

    @Override
    public ByteBuffer getComponentData(String name) {
        return this.rawData.getRawData(name).order(ByteOrder.nativeOrder());
    }
}

